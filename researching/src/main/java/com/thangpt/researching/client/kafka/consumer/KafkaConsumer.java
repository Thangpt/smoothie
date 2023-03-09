package com.thangpt.researching.client.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${app.kafka.consumer.first-consume-topic}")
    public void processBiz(String message) {
        LOGGER.info(">> processBiz onMessage: message [{}]", message);
        try {
            // MrxStateDto mrxStateDto = objectMapper.readValue(message, MrxStateDto.class);
            // if(Constants.MrxStepState.SOD.equals(mrxStateDto.getStep())){
            //     accountUseCase.getMarginXAccounts();
            // }
        } catch (Exception e) {
            LOGGER.error("processBiz onMessage: exception: {}", e.getMessage());
        }
    }
}
