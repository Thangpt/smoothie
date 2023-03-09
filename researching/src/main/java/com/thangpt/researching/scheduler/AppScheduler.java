package com.thangpt.researching.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppScheduler {

    Logger logger = LoggerFactory.getLogger(AppScheduler.class);

    @Value("${server.port}")
    private int serverPort;

    @Scheduled(fixedRate = 3600000)
    public void schedulerByFixedRate(){
        logger.info("the first fixed rate scheduler that I have created!");
    }

    @Scheduled(fixedDelay = 3600000)
    public void schedulerByFixedDelay(){
        logger.info("the first fixed delay scheduler that I have created!");
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void schedulerByCronExpression(){
        logger.info("the first cron scheduler that I have created!");
    }

    @Scheduled(fixedDelayString = "${app.config.scheduler.delay-string}")
    public void schedulerByConfig(){
        logger.info("the first config scheduler that I have created!");
    }
}
