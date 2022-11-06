package com.zayden.coffeeorderserviceuserservice.aspect.kafka;

import com.zayden.coffeeorderserviceuserservice.dto.KafkaTopic;
import com.zayden.coffeeorderserviceuserservice.dto.PaypointDto;
import com.zayden.coffeeorderserviceuserservice.messagequeue.producer.PaypointProducer;
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
public class SendKafkaToPayServiceAspect {

    private final PaypointProducer paypointProducer;

    @Before("@annotation(com.zayden.coffeeorderserviceuserservice.annotation.SendKafkaToPayServiceMessage)")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        PaypointDto paypointDto = (PaypointDto) args[0];
        paypointProducer.send(KafkaTopic.PAYSERVICETOPOINTSTATUS.getKafkaTopic(), paypointDto);
    }
}
