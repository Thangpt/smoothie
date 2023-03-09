package com.thangpt.researching.client.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(String topic, String key, String payload, List<Header> headers) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, key, payload, headers);
        kafkaTemplate.send(record).whenComplete((result, ex) -> {
            if (ex != null) {
                LOGGER.error(">> sendMessage exception: {}", ex.getMessage());
            } else {
                LOGGER.info(">> sendMessage success, message: [{}]", payload);
            }
        });
    }

    public void sendMessage(String topic, String key, Object payload) {
        try {
            String value = objectMapper.writeValueAsString(payload);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, key, value);
            kafkaTemplate.send(record).whenComplete((result, ex) -> {
                if (ex != null) {
                    LOGGER.error(">> sendMessage exception: {}", ex.getMessage());
                } else {
                    LOGGER.info(">> sendMessage success, message: [{}]", payload);
                }
            });
        } catch (Exception e) {
            LOGGER.error(">> sendMessage payload: {}, exception: {}", payload, e.getMessage());
        }
    }
}