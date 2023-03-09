package com.thangpt.researching.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.thangpt.researching.common.FileWatcher;
import com.thangpt.researching.feature.customize.entity.VsdtxreqEntity;
import com.thangpt.researching.feature.customize.repository.VsdtxreqRepository;

@Component
public class FileConverter {
    Logger logger = LoggerFactory.getLogger(FileConverter.class);

    @Autowired
    private VsdtxreqRepository vsdtxreqRepository;

    @Value("${app.file.template.stp}")
    private String sendFolder;

    private final String TEMPLATE_FILE_EXTENSION = ".txt";
    private final String FIN_FILE_EXTENSION = ".fin";
    private final String TIME_FORMAT_NOW = "HH:mm:ss";
    private final String PATH_TO_TEMPLATE_FILES = "template/";

    public static final HashMap<String,String> templates = new HashMap<>();

    public void genSTP(Long reqID) throws IOException{
        Optional<VsdtxreqEntity> vsdtxreqEntity = vsdtxreqRepository.findByReqId(reqID);
        String templateContent;
        if (vsdtxreqEntity.isPresent()){
            //String templateFileName = vsdtxreqEntity.get().getTrfCode().replace("/", "_");
            //templateContent = readFileToString(PATH_TO_TEMPLATE_FILES+templateFileName+TEMPLATE_FILE_EXTENSION);//getTemplateString(templateFileName);
            templateContent = templates.get(vsdtxreqEntity.get().getTrfCode());
            HashMap<String,String> transactionFields = new HashMap<String,String>();
            vsdtxreqEntity.get().getVsdtxreqdtlEntities()
                .forEach(x->transactionFields.put(x.getFldName(), x.getCval() == null ? Strings.EMPTY : convertVNeseToMessage(x.getCval())));
            transactionFields.put("REQID", vsdtxreqEntity.get().getReqId().toString());
            transactionFields.put("REQTIME", getCurrentTime());
            templateContent = fillDataToTemplate(transactionFields, templateContent);
            saveFile(vsdtxreqEntity.get().getReqId().toString()+FIN_FILE_EXTENSION, templateContent);
        }

    }

    // private String getTemplateString(String fileName) throws IOException{
    //     RandomAccessFile objRandomAccessFile = null;
    //     try {
    //         File file = new File(sendFolder+fileName+TEMPLATE_FILE_EXTENSION);
    //         objRandomAccessFile = new RandomAccessFile(file,"r");
    //         objRandomAccessFile.seek(0);
    //         int iLength = (int)file.length();
    //         byte[] result = new byte[iLength];
    //         java.util.Arrays.fill(result, (byte)0);
    //         objRandomAccessFile.read(result, 0,iLength);
    //         return new String(result);
    //     } finally{
    //         objRandomAccessFile.close();
    //     }
    // }

    public static String readFileToString(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        return asString(resource);
    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void initTemplates(Resource[] resources){
        templates.clear();
        Arrays.asList(resources).forEach(
            resource->templates.put(resource.getFilename().replace(".txt","").replace("_", "/"), 
                                    asString(resource)));
    }

    private void saveFile(String fileName, String textToSave) throws IOException{
        File file = new File(sendFolder+fileName);
        BufferedWriter out = new BufferedWriter(new FileWriter(file), 32768);
        out.write(textToSave);
        out.close();
    }

    private String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public String convertVNeseToMessage(String inputString){
        StringBuilder result = new StringBuilder();
        Arrays.asList(inputString.split("")).stream().forEach(x->{
            result.append(FileWatcher.enCodeDictionary.get(x) == null ? x : FileWatcher.enCodeDictionary.get(x));
        });
        return result.toString();
    }

    public String convertMessageToVNese(String inputString){
        String patternString = "%(" + StringUtils.join(FileWatcher.deCodeDictionary.keySet(), "|") + ")%";
        Pattern pattern = Pattern.compile(patternString);
        String a = inputString.replace("?", "%");
        Matcher matcher = pattern.matcher(a);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            String idx = matcher.group(1);
            String val = FileWatcher.deCodeDictionary.get(idx);
            matcher.appendReplacement(sb, val);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public String fillDataToTemplate(HashMap<String,String> fields, String template){
        String patternString = "%(" + StringUtils.join(fields.keySet(), "|") + ")%";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(template);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb, fields.get(matcher.group(1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
