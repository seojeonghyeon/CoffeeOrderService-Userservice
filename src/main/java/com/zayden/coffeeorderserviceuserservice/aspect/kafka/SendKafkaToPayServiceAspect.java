package com.zayden.coffeeorderserviceuserservice.aspect.kafka;

import com.zayden.userservice.dto.PaypointDto;
import com.zayden.userservice.messagequeue.producer.PaypointProducer;
import com.zayden.userservice.vo.RequestPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SendKafkaToPayServiceAspect {

    private final PaypointProducer paypointProducer;
    private static final String kafkaTopicName = "kafka.topics.user-to-pay-for-point";

    @Before("@annotation(com.zayden.userservice.annotation.SendKafkaToPayServiceMessage)")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        RequestPoint requestPoint = (RequestPoint) args[0];
        log.info("BEFORE THE KAFKA SEND("+joinPoint.getTarget()+") : " + kafkaTopicName
                +" - "+requestPoint.getUserId()+" : "+requestPoint.getAmount());
    }

    @After("@annotation(com.zayden.userservice.annotation.SendKafkaToPayServiceMessage)")
    public void after(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        PaypointDto paypointDto = (PaypointDto) args[0];
        String logMessage = setLogMessage(kafkaTopicName, paypointDto);
        paypointProducer.send(kafkaTopicName, paypointDto);
        log.info("AFTER THE KAFKA SEND : "+ logMessage);
    }

    private String setLogMessage(String kafkaTopicName, PaypointDto paypointDto){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(kafkaTopicName);
        stringBuilder.append("[");
        stringBuilder.append(paypointDto.getUserId());
        stringBuilder.append(" + ");
        stringBuilder.append(paypointDto.getPayId());
        stringBuilder.append(" ] ");
        stringBuilder.append(paypointDto.getPayStatus());
        stringBuilder.append(" : ");
        stringBuilder.append(paypointDto.getAmount());
        return stringBuilder.toString();
    }
}
