package com.zayden.coffeeorderserviceuserservice.aspect.kafka;

import com.zayden.coffeeorderserviceuserservice.dto.KafkaTopic;
import com.zayden.coffeeorderserviceuserservice.dto.PaypointDto;
import com.zayden.coffeeorderserviceuserservice.dto.UserPointDto;
import com.zayden.coffeeorderserviceuserservice.messagequeue.producer.UserPointProducer;
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
public class SendKafkaToDBForUpdateUserPointAspect {
    private final UserPointProducer userPointProducer;

    private PaypointDto paypointDto;
    private KafkaTopic kafkaTopic;
    private UserPointDto userPointDto;

    @Before("@annotation(com.zayden.coffeeorderserviceuserservice.annotation.SendKafkaToDBForUpdateUserPointMessage)")
    public void after(JoinPoint joinPoint){
        Object[] outerArgs = joinPoint.getArgs();

        JoinPoint innerJointPoint = (JoinPoint) outerArgs[0];
        Object[] innerArgs = innerJointPoint.getArgs();

        this.paypointDto = (PaypointDto) innerArgs[0];
        this.kafkaTopic = (KafkaTopic) outerArgs[1];

        userPointDto = UserPointDto.builder()
                .userId(paypointDto.getUserId())
                .amount(paypointDto.getAmount())
                .build();

        userPointProducer.send(kafkaTopic.getKafkaTopic(), userPointDto);
    }
}
