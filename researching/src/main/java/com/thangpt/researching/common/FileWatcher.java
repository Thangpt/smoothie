package com.thangpt.researching.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;




public class FileWatcher implements Runnable{

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FileWatcher.class);

    public static final Hashtable<String,String> enCodeDictionary = new Hashtable<String, String>();
    public static final Hashtable<String,String> deCodeDictionary = new Hashtable<String, String>();

    static {
        deCodeDictionary.put("AW","Ă"); 
        deCodeDictionary.put("aw","ă"); 
        deCodeDictionary.put("OW","Ơ"); 
        deCodeDictionary.put("ow","ơ"); 
        deCodeDictionary.put("UW","Ư"); 
        deCodeDictionary.put("uw","ư"); 
        deCodeDictionary.put("AA","Â"); 
        deCodeDictionary.put("aa","â"); 
        deCodeDictionary.put("OO","Ô"); 
        deCodeDictionary.put("oo","ô"); 
        deCodeDictionary.put("EE","Ê"); 
        deCodeDictionary.put("ee","ê"); 
        deCodeDictionary.put("AF","À"); 
        deCodeDictionary.put("AS","Á"); 
        deCodeDictionary.put("AR","Ả"); 
        deCodeDictionary.put("AX","Ã"); 
        deCodeDictionary.put("AJ","Ạ"); 
        deCodeDictionary.put("af","à"); 
        deCodeDictionary.put("as","á"); 
        deCodeDictionary.put("ar","ả"); 
        deCodeDictionary.put("ax","ã"); 
        deCodeDictionary.put("aj","ạ"); 
        deCodeDictionary.put("AAF","Ầ"); 
        deCodeDictionary.put("AAS","Ấ"); 
        deCodeDictionary.put("AAR","Ẩ"); 
        deCodeDictionary.put("AAX","Ẫ"); 
        deCodeDictionary.put("AAJ","Ậ"); 
        deCodeDictionary.put("aaf","ầ"); 
        deCodeDictionary.put("aas","ấ"); 
        deCodeDictionary.put("aar","ẩ"); 
        deCodeDictionary.put("aax","ẫ"); 
        deCodeDictionary.put("aaj","ậ"); 
        deCodeDictionary.put("AWF","Ằ"); 
        deCodeDictionary.put("AWS","Ắ"); 
        deCodeDictionary.put("AWR","Ẳ"); 
        deCodeDictionary.put("AWX","Ẵ"); 
        deCodeDictionary.put("AWJ","Ặ"); 
        deCodeDictionary.put("awf","ằ"); 
        deCodeDictionary.put("aws","ắ"); 
        deCodeDictionary.put("awr","ẳ"); 
        deCodeDictionary.put("awx","ẵ"); 
        deCodeDictionary.put("awj","ặ"); 
        deCodeDictionary.put("DD","Đ"); 
        deCodeDictionary.put("dd","đ"); 
        deCodeDictionary.put("EF","È"); 
        deCodeDictionary.put("ES","É"); 
        deCodeDictionary.put("ER","Ẻ"); 
        deCodeDictionary.put("EX","Ẽ"); 
        deCodeDictionary.put("EJ","Ẹ"); 
        deCodeDictionary.put("ef","è"); 
        deCodeDictionary.put("es","é"); 
        deCodeDictionary.put("er","ẻ"); 
        deCodeDictionary.put("ex","ẽ"); 
        deCodeDictionary.put("ej","ẹ"); 
        deCodeDictionary.put("EEF","Ề"); 
        deCodeDictionary.put("EES","Ế"); 
        deCodeDictionary.put("EER","Ể"); 
        deCodeDictionary.put("EEX","Ễ"); 
        deCodeDictionary.put("EEJ","Ệ"); 
        deCodeDictionary.put("eef","ề"); 
        deCodeDictionary.put("ees","ế"); 
        deCodeDictionary.put("eer","ể"); 
        deCodeDictionary.put("eex","ễ"); 
        deCodeDictionary.put("eej","ệ"); 
        deCodeDictionary.put("IF","Ì"); 
        deCodeDictionary.put("IS","Í"); 
        deCodeDictionary.put("IR","Ỉ"); 
        deCodeDictionary.put("IX","Ĩ"); 
        deCodeDictionary.put("IJ","Ị"); 
        deCodeDictionary.put("if","ì"); 
        deCodeDictionary.put("is","í"); 
        deCodeDictionary.put("ir","ỉ"); 
        deCodeDictionary.put("ix","ĩ"); 
        deCodeDictionary.put("ij","ị"); 
        deCodeDictionary.put("OF","Ò"); 
        deCodeDictionary.put("OS","Ó"); 
        deCodeDictionary.put("OR","Ỏ"); 
        deCodeDictionary.put("OX","Õ"); 
        deCodeDictionary.put("OJ","Ọ"); 
        deCodeDictionary.put("of","ò"); 
        deCodeDictionary.put("os","ó"); 
        deCodeDictionary.put("or","ỏ"); 
        deCodeDictionary.put("ox","õ"); 
        deCodeDictionary.put("oj","ọ"); 
        deCodeDictionary.put("OOF","Ồ"); 
        deCodeDictionary.put("OOS","Ố"); 
        deCodeDictionary.put("OOR","Ổ"); 
        deCodeDictionary.put("OOX","Ỗ"); 
        deCodeDictionary.put("OOJ","Ộ"); 
        deCodeDictionary.put("oof","ồ"); 
        deCodeDictionary.put("oos","ố"); 
        deCodeDictionary.put("oor","ổ"); 
        deCodeDictionary.put("oox","ỗ"); 
        deCodeDictionary.put("ooj","ộ"); 
        deCodeDictionary.put("OWF","Ờ"); 
        deCodeDictionary.put("OWS","Ớ"); 
        deCodeDictionary.put("OWR","Ở"); 
        deCodeDictionary.put("OWX","Ỡ"); 
        deCodeDictionary.put("OWJ","Ợ"); 
        deCodeDictionary.put("owf","ờ"); 
        deCodeDictionary.put("ows","ớ"); 
        deCodeDictionary.put("owr","ở"); 
        deCodeDictionary.put("owx","ỡ"); 
        deCodeDictionary.put("owj","ợ"); 
        deCodeDictionary.put("UF","Ù"); 
        deCodeDictionary.put("US","Ú"); 
        deCodeDictionary.put("UR","Ủ"); 
        deCodeDictionary.put("UX","Ũ"); 
        deCodeDictionary.put("UJ","Ụ"); 
        deCodeDictionary.put("uf","ù"); 
        deCodeDictionary.put("us","ú"); 
        deCodeDictionary.put("ur","ủ"); 
        deCodeDictionary.put("ux","ũ"); 
        deCodeDictionary.put("uj","ụ"); 
        deCodeDictionary.put("UWF","Ừ"); 
        deCodeDictionary.put("UWS","Ứ"); 
        deCodeDictionary.put("UWR","Ử"); 
        deCodeDictionary.put("UWX","Ữ"); 
        deCodeDictionary.put("UWJ","Ự"); 
        deCodeDictionary.put("uwf","ừ"); 
        deCodeDictionary.put("uws","ứ"); 
        deCodeDictionary.put("uwr","ử"); 
        deCodeDictionary.put("uwx","ữ"); 
        deCodeDictionary.put("uwj","ự"); 
        deCodeDictionary.put("yf","ỳ"); 
        deCodeDictionary.put("ys","ý"); 
        deCodeDictionary.put("yx","ỹ"); 
        deCodeDictionary.put("yj","ỵ"); 
        deCodeDictionary.put("yr","ỷ"); 
        deCodeDictionary.put("YF","Ỳ"); 
        deCodeDictionary.put("YS","Ý"); 
        deCodeDictionary.put("YX","Ỹ"); 
        deCodeDictionary.put("YJ","Ỵ"); 
        deCodeDictionary.put("YR","Ỷ"); 
        deCodeDictionary.put("_","/"); 
        deCodeDictionary.put("_38","&"); 
        deCodeDictionary.put("_35","#"); 
        deCodeDictionary.put("_37","%"); 
        deCodeDictionary.put("_92","\\\\");

        enCodeDictionary.put("Ă","?AW?");
        enCodeDictionary.put("ă","?aw?");
        enCodeDictionary.put("Ơ","?OW?");
        enCodeDictionary.put("ơ","?ow?");
        enCodeDictionary.put("Ư","?UW?");
        enCodeDictionary.put("ư","?uw?");
        enCodeDictionary.put("Â","?AA?");
        enCodeDictionary.put("â","?aa?");
        enCodeDictionary.put("Ô","?OO?");
        enCodeDictionary.put("ô","?oo?");
        enCodeDictionary.put("Ê","?EE?");
        enCodeDictionary.put("ê","?ee?");
        enCodeDictionary.put("À","?AF?");
        enCodeDictionary.put("Á","?AS?");
        enCodeDictionary.put("Ả","?AR?");
        enCodeDictionary.put("Ã","?AX?");
        enCodeDictionary.put("Ạ","?AJ?");
        enCodeDictionary.put("à","?af?");
        enCodeDictionary.put("á","?as?");
        enCodeDictionary.put("ả","?ar?");
        enCodeDictionary.put("ã","?ax?");
        enCodeDictionary.put("ạ","?aj?");
        enCodeDictionary.put("Ầ","?AAF?");
        enCodeDictionary.put("Ấ","?AAS?");
        enCodeDictionary.put("Ẩ","?AAR?");
        enCodeDictionary.put("Ẫ","?AAX?");
        enCodeDictionary.put("Ậ","?AAJ?");
        enCodeDictionary.put("ầ","?aaf?");
        enCodeDictionary.put("ấ","?aas?");
        enCodeDictionary.put("ẩ","?aar?");
        enCodeDictionary.put("ẫ","?aax?");
        enCodeDictionary.put("ậ","?aaj?");
        enCodeDictionary.put("Ằ","?AWF?");
        enCodeDictionary.put("Ắ","?AWS?");
        enCodeDictionary.put("Ẳ","?AWR?");
        enCodeDictionary.put("Ẵ","?AWX?");
        enCodeDictionary.put("Ặ","?AWJ?");
        enCodeDictionary.put("ằ","?awf?");
        enCodeDictionary.put("ắ","?aws?");
        enCodeDictionary.put("ẳ","?awr?");
        enCodeDictionary.put("ẵ","?awx?");
        enCodeDictionary.put("ặ","?awj?");
        enCodeDictionary.put("Đ","?DD?");
        enCodeDictionary.put("đ","?dd?");
        enCodeDictionary.put("È","?EF?");
        enCodeDictionary.put("É","?ES?");
        enCodeDictionary.put("Ẻ","?ER?");
        enCodeDictionary.put("Ẽ","?EX?");
        enCodeDictionary.put("Ẹ","?EJ?");
        enCodeDictionary.put("è","?ef?");
        enCodeDictionary.put("é","?es?");
        enCodeDictionary.put("ẻ","?er?");
        enCodeDictionary.put("ẽ","?ex?");
        enCodeDictionary.put("ẹ","?ej?");
        enCodeDictionary.put("Ề","?EEF?");
        enCodeDictionary.put("Ế","?EES?");
        enCodeDictionary.put("Ể","?EER?");
        enCodeDictionary.put("Ễ","?EEX?");
        enCodeDictionary.put("Ệ","?EEJ?");
        enCodeDictionary.put("ề","?eef?");
        enCodeDictionary.put("ế","?ees?");
        enCodeDictionary.put("ể","?eer?");
        enCodeDictionary.put("ễ","?eex?");
        enCodeDictionary.put("ệ","?eej?");
        enCodeDictionary.put("Ì","?IF?");
        enCodeDictionary.put("Í","?IS?");
        enCodeDictionary.put("Ỉ","?IR?");
        enCodeDictionary.put("Ĩ","?IX?");
        enCodeDictionary.put("Ị","?IJ?");
        enCodeDictionary.put("ì","?if?");
        enCodeDictionary.put("í","?is?");
        enCodeDictionary.put("ỉ","?ir?");
        enCodeDictionary.put("ĩ","?ix?");
        enCodeDictionary.put("ị","?ij?");
        enCodeDictionary.put("Ò","?OF?");
        enCodeDictionary.put("Ó","?OS?");
        enCodeDictionary.put("Ỏ","?OR?");
        enCodeDictionary.put("Õ","?OX?");
        enCodeDictionary.put("Ọ","?OJ?");
        enCodeDictionary.put("ò","?of?");
        enCodeDictionary.put("ó","?os?");
        enCodeDictionary.put("ỏ","?or?");
        enCodeDictionary.put("õ","?ox?");
        enCodeDictionary.put("ọ","?oj?");
        enCodeDictionary.put("Ồ","?OOF?");
        enCodeDictionary.put("Ố","?OOS?");
        enCodeDictionary.put("Ổ","?OOR?");
        enCodeDictionary.put("Ỗ","?OOX?");
        enCodeDictionary.put("Ộ","?OOJ?");
        enCodeDictionary.put("ồ","?oof?");
        enCodeDictionary.put("ố","?oos?");
        enCodeDictionary.put("ổ","?oor?");
        enCodeDictionary.put("ỗ","?oox?");
        enCodeDictionary.put("ộ","?ooj?");
        enCodeDictionary.put("Ờ","?OWF?");
        enCodeDictionary.put("Ớ","?OWS?");
        enCodeDictionary.put("Ở","?OWR?");
        enCodeDictionary.put("Ỡ","?OWX?");
        enCodeDictionary.put("Ợ","?OWJ?");
        enCodeDictionary.put("ờ","?owf?");
        enCodeDictionary.put("ớ","?ows?");
        enCodeDictionary.put("ở","?owr?");
        enCodeDictionary.put("ỡ","?owx?");
        enCodeDictionary.put("ợ","?owj?");
        enCodeDictionary.put("Ù","?UF?");
        enCodeDictionary.put("Ú","?US?");
        enCodeDictionary.put("Ủ","?UR?");
        enCodeDictionary.put("Ũ","?UX?");
        enCodeDictionary.put("Ụ","?UJ?");
        enCodeDictionary.put("ù","?uf?");
        enCodeDictionary.put("ú","?us?");
        enCodeDictionary.put("ủ","?ur?");
        enCodeDictionary.put("ũ","?ux?");
        enCodeDictionary.put("ụ","?uj?");
        enCodeDictionary.put("Ừ","?UWF?");
        enCodeDictionary.put("Ứ","?UWS?");
        enCodeDictionary.put("Ử","?UWR?");
        enCodeDictionary.put("Ữ","?UWX?");
        enCodeDictionary.put("Ự","?UWJ?");
        enCodeDictionary.put("ừ","?uwf?");
        enCodeDictionary.put("ứ","?uws?");
        enCodeDictionary.put("ử","?uwr?");
        enCodeDictionary.put("ữ","?uwx?");
        enCodeDictionary.put("ự","?uwj?");
        enCodeDictionary.put("ỳ","?yf?");
        enCodeDictionary.put("ý","?ys?");
        enCodeDictionary.put("ỹ","?yx?");
        enCodeDictionary.put("ỵ","?yj?");
        enCodeDictionary.put("ỷ","?yr?");
        enCodeDictionary.put("Ỳ","?YF?");
        enCodeDictionary.put("Ý","?YS?");
        enCodeDictionary.put("Ỹ","?YX?");
        enCodeDictionary.put("Ỵ","?YJ?");
        enCodeDictionary.put("Ỷ","?YR?");
        enCodeDictionary.put("/","?_?");
        enCodeDictionary.put("&","?_38?");
        enCodeDictionary.put("#","?_35?");
        enCodeDictionary.put("%","?_37?");
        enCodeDictionary.put("\\","?_92?");
    }

    private String pathToFolder;

    public FileWatcher (String pathToFolder){
        this.pathToFolder = pathToFolder;
    }

    @Override
    public void run() {
        try(WatchService watchService = FileSystems.getDefault().newWatchService()){
            Path receiveFolder = Paths.get(pathToFolder);
            receiveFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key;
            while((key = watchService.take()) != null){ 
                try {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        LOGGER.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                        Path fileReceive = receiveFolder.resolve(((WatchEvent<Path>) event).context());
                        LOGGER.info("File path: "+fileReceive.toString());
                        processReceiveSwiftMessage(fileReceive.toFile());
                    }
                    key.reset();
                } catch (Exception e) {
                    LOGGER.error("process receive file exception : ", e);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("watchFile exception : ", ex);
        }
    }

    private void processReceiveSwiftMessage(File fileReceive) throws IOException{
        SwiftParser swiftParser = new SwiftParser(fileReceive);
        SwiftMessage swiftMessage =  swiftParser.message();
        String bicCode, sessionNumber, messageId, msgType, errorMsg, receiveTime, msgCode;
        if(swiftMessage.getBlock4().containsTag("451")){
            msgType = swiftMessage.getBlock4().getTagValue("451");
            receiveTime = swiftMessage.getBlock4().getTagValue("177");
            LOGGER.info("Tag 451: "+ msgType + " at time : "+ receiveTime);
            if("1".equals(msgType) &&
            swiftMessage.getBlock4().containsTag("405")){
                errorMsg = swiftMessage.getBlock4().getTagValue("405");
                LOGGER.info("Error Message Tag 405: "+ errorMsg);
            }
            swiftMessage = swiftMessage.unparsedTextGetAsMessage(0);
        }

        bicCode = swiftMessage.getBlock1().getLogicalTerminal();
        sessionNumber = swiftMessage.getBlock1().getSessionNumber();
        messageId = swiftMessage.getBlock1().getSequenceNumber();
        msgCode = swiftMessage.getBlock2().getMessageType();
        Arrays.asList(swiftMessage.getBlock4().asTagArray()).stream().forEach(x->convertMessageFieldToVNese(x));;
        LOGGER.info("bicCode: {} - sessionNumber: {} - messageId: {}",bicCode,sessionNumber,messageId);

    }

    private void convertMessageFieldToVNese(Tag tag){
        StringBuilder tagContent = new StringBuilder(tag.getValue().replace("(at)", "@"));
        if(tagContent.toString().contains("?")){
            String[] letters = tagContent.toString().split("?");
            tagContent.setLength(0);
            Arrays.asList(letters).stream().forEach(x->{
                if(enCodeDictionary.containsKey(x)){
                    x = enCodeDictionary.get(x);
                }
                tagContent.append(x);
            });
        }
        tag.setValue(tagContent.toString());
    }
}
