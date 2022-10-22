package com.zayden.coffeeorderserviceuserservice.messagequeue.producer;

import com.zayden.userservice.dto.*;
import com.zayden.userservice.dto.kafka.Field;
import com.zayden.userservice.dto.kafka.KafkaUserpointDto;
import com.zayden.userservice.dto.kafka.PayloadUserpoint;
import com.zayden.userservice.dto.kafka.Schema;
import com.zayden.userservice.messagequeue.config.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserPointProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string", true, "user_id"),
            new Field("int32", true, "amount")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("pay")
            .build();

    public UserPointDto send(String topic, UserPointDto userPointDto){
        PayloadUserpoint payload = PayloadUserpoint.builder()
                .user_id(userPointDto.getUserId())
                .amount(userPointDto.getAmount())
                .build();
        KafkaUserpointDto kafkaUserpointDto = new KafkaUserpointDto(schema, payload);
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        kafkaProducer.send(topic, kafkaUserpointDto);
        log.info("User Producer send data for point from the User microservice"+kafkaUserpointDto);
        return userPointDto;
    }

}
