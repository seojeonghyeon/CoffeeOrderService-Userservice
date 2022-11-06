package com.zayden.coffeeorderserviceuserservice.aspect.kafka;

import com.zayden.coffeeorderserviceuserservice.dto.KafkaTopic;
import com.zayden.coffeeorderserviceuserservice.dto.LogUserPointDto;
import com.zayden.coffeeorderserviceuserservice.dto.PayPointStatus;
import com.zayden.coffeeorderserviceuserservice.dto.PaypointDto;
import com.zayden.coffeeorderserviceuserservice.messagequeue.producer.LogProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class SendKafkaToDBForLogAspect {

    private final LogProducer logProducer;
    private PaypointDto paypointDto;
    private PayPointStatus payPointStatus;
    private KafkaTopic kafkaTopic;

    @Before("@annotation(com.zayden.coffeeorderserviceuserservice.annotation.SendKafkaToDBForLogMessage)")
    public void before(JoinPoint joinPoint){
        Object[] outerArgs = joinPoint.getArgs();

        JoinPoint innerJointPoint = (JoinPoint) outerArgs[0];
        Object[] innerArgs = innerJointPoint.getArgs();

        this.paypointDto = (PaypointDto) innerArgs[0];
        this.payPointStatus = (PayPointStatus) innerArgs[1];
        this.kafkaTopic = (KafkaTopic) outerArgs[1];

        LogUserPointDto logUserPointDto = LogUserPointDto.builder()
                .userId(paypointDto.getUserId())
                .logMessage(payPointStatus.toString())
                .amount(paypointDto.getAmount())
                .build();
        logProducer.send(kafkaTopic.getKafkaTopic(), logUserPointDto);
    }
}
