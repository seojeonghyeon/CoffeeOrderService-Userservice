package com.zayden.coffeeorderserviceuserservice.aspect.kafka;

import com.zayden.userservice.dto.LogUserPointDto;
import com.zayden.userservice.messagequeue.producer.LogProducer;
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
public class SendKafkaToDBForLogAspect {

    private final LogProducer logProducer;
    private static final String kafkaTopicName= "kafka.topics.user-to-db-for-log";
    private String userId;
    private String logMessage;

    @Before("@annotation(com.zayden.userservice.annotation.SendKafkaToDBForLogMessage)")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        this.userId = (String) args[0];
        this.logMessage = (String) args[1];
        log.info("BEFORE THE KAFKA SEND("+joinPoint.getTarget()+") : " + kafkaTopicName
                +" - "+userId+" "+logMessage);
    }

    @After("@annotation(com.zayden.userservice.annotation.SendKafkaToDBForLogMessage)")
    public void after(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        int amount = (int) args[0];
        LogUserPointDto logUserPointDto = LogUserPointDto.builder()
                .userId(userId)
                .logMessage(logMessage)
                .amount(amount)
                .build();
        logProducer.send(kafkaTopicName, logUserPointDto);
        log.info("AFTER THE KAFKA SEND : "+ setLogMessage(kafkaTopicName, logUserPointDto));
    }

    private String setLogMessage(String kafkaTopicName, LogUserPointDto logUserPointDto){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(kafkaTopicName);
        stringBuilder.append("[");
        stringBuilder.append(logUserPointDto.getUserId());
        stringBuilder.append(" ] ");
        stringBuilder.append(" : ");
        stringBuilder.append(logUserPointDto.getAmount());
        return stringBuilder.toString();
    }
}
