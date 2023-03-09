package com.thangpt.researching.api.internal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thangpt.researching.client.kafka.producer.KafkaProducer;
import com.thangpt.researching.common.ExcelConverter;
import com.thangpt.researching.common.FileWatcher;
import com.thangpt.researching.feature.biz.entity.AfmastEntity;
import com.thangpt.researching.feature.biz.repository.AfmastRepository;
import com.thangpt.researching.feature.tech.entity.AfmastEntity2nd;
import com.thangpt.researching.feature.tech.repository.Afmast2ndRepository;
import com.thangpt.researching.model.ExcelModel;
import com.thangpt.researching.services.FileConverter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "/i")
@RestController
@RequestMapping("/i")
public class ResearchingInternalController {

    Logger logger = LoggerFactory.getLogger(ResearchingInternalController.class);

    @Autowired
    private ExcelConverter excelConverter;

    @Autowired
    private AfmastRepository afmastRepository;

    @Autowired
    private Afmast2ndRepository afmast2ndRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private ExecutorService executorService;

    @Value("${app.kafka.producer.first-produce-topic}")
    private String sysvarTopic;

    @Value("${app.file.watcher.stp}")
    private String pathToFolder;

    @Autowired
    private FileConverter fileConverter;
    
    //@GetMapping("/getApi")
    @RequestMapping(value = "/getApi", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getApi(){
        logger.info("first get API");
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String getFoosBySimplePath() {
        return "Just test!";
    }

    @ApiOperation(tags = "convertExcelToObject", value = "convertExcelToObject")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully", response = List.class),
            @ApiResponse(code = 400, message = "Client error", response = Object.class),
            @ApiResponse(code = 500, message = "Server error", response = Object.class) })
    @RequestMapping(value = "/convertExcelToObject", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<Object> convertExcelToObject() {
        ExcelModel excelModel = (ExcelModel) excelConverter.convertExcelToObject(null, null, ExcelModel.class);
        return List.of(excelModel);
    }

    @ApiOperation(tags = "account", value = "get account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully", response = List.class),
            @ApiResponse(code = 400, message = "Client error", response = Object.class),
            @ApiResponse(code = 500, message = "Server error", response = Object.class) })
    @RequestMapping(value = "/account/{acctno}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> getAccounts(@PathVariable String acctno) {
        Optional<AfmastEntity> afmastEntity = afmastRepository.findByAcctno(acctno);
        Optional<AfmastEntity2nd> afmastEntity2nd = afmast2ndRepository.findByAcctno(acctno);
        return List.of(afmastEntity2nd.isPresent() ? afmastEntity2nd.get() : afmastEntity.get());
    }

    @ApiOperation(tags = "send kafka", value = "send kafka")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully", response = Object.class),
            @ApiResponse(code = 400, message = "Client error", response = Object.class),
            @ApiResponse(code = 500, message = "Server error", response = Object.class) })
    @RequestMapping(value = "/kafka", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> sendKafka() {
        String a = "/INVESTOR \n /MAIL/abc(at)gmail.com \n /BIRD/19850202 \n /ADRF/204 Ho?af?ng Qu?oos?c Vi?eej?t, H?af? N?ooj?i";
        String [] list = a.split("?");
        kafkaProducer.sendMessage(sysvarTopic, null, "{\"key\":\"BATCHRUNNING\",\"value\":\"N\",\"group\":\"SYSTEM\",\"description\":\"\"}", List.of());
        return ResponseEntity.ok("OK");
    }

    @ApiOperation(tags = "genSTP", value = "genSTP")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully", response = Object.class),
            @ApiResponse(code = 400, message = "Client error", response = Object.class),
            @ApiResponse(code = 500, message = "Server error", response = Object.class) })
    @RequestMapping(value = "/genSTP", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> genSTP() throws IOException {
        // Long a = 598136L;
        // fileConverter.genSTP(a);
        HashMap<String,String> b = fileConverter.templates;
        fileConverter.templates.put("a", "pathToFolder");

        HashMap<String,String> c = fileConverter.templates;
        String a= fileConverter.readFileToString("template/540.NEWM.REAL.UNIT_NONE_AVAI.txt");
        return ResponseEntity.ok("OK");
    }
}
