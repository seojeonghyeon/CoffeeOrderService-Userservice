package com.zayden.coffeeorderserviceuserservice.messagequeue.producer;

import com.zayden.coffeeorderserviceuserservice.dto.LogUserPointDto;
import com.zayden.coffeeorderserviceuserservice.dto.kafka.*;
import com.zayden.coffeeorderserviceuserservice.messagequeue.config.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string", true, "user_id"),
            new Field("int32", true, "amount"),
            new Field("String", true, "log_message")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("log")
            .build();

    public LogUserPointDto send(String topic, LogUserPointDto logUserPointDto){
        PayloadLogUserPoint payload = PayloadLogUserPoint.builder()
                .user_id(logUserPointDto.getUserId())
                .amount(logUserPointDto.getAmount())
                .log_message(logUserPointDto.getLogMessage())
                .build();
        KafkaLogUserPointDto kafkaLogUserPointDto = new KafkaLogUserPointDto(schema, payload);
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        kafkaProducer.send(topic, kafkaLogUserPointDto);
        log.info("User Producer send data for log from the User microservice"+kafkaLogUserPointDto);
        return logUserPointDto;
    }
}
