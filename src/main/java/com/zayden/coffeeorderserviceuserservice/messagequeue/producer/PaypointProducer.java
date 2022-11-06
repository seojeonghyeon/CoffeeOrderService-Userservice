package com.zayden.coffeeorderserviceuserservice.messagequeue.producer;

import com.zayden.coffeeorderserviceuserservice.dto.*;
import com.zayden.coffeeorderserviceuserservice.dto.kafka.Field;
import com.zayden.coffeeorderserviceuserservice.dto.kafka.KafkaPaypointDto;
import com.zayden.coffeeorderserviceuserservice.dto.kafka.PayloadPaypoint;
import com.zayden.coffeeorderserviceuserservice.dto.kafka.Schema;
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
public class PaypointProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string", true, "pay_id"),
            new Field("string", true, "user_id"),
            new Field("int32", true, "amount"),
            new Field("string", true, "pay_status")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("paypoint")
            .build();

    public PaypointDto send(String topic, PaypointDto paypointDto){
        PayloadPaypoint payload = PayloadPaypoint.builder()
                .pay_id(paypointDto.getPayId())
                .user_id(paypointDto.getUserId())
                .amount(paypointDto.getAmount())
                .pay_status(paypointDto.getPayStatus())
                .build();
        KafkaPaypointDto kafkaPaypointDto = new KafkaPaypointDto(schema, payload);
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        kafkaProducer.send(topic, kafkaPaypointDto);
        log.info("Point Producer send data for point from the User microservice"+kafkaPaypointDto);
        return paypointDto;
    }
}
