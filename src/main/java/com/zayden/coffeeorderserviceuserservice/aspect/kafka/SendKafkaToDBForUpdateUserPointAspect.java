package com.zayden.coffeeorderserviceuserservice.aspect.kafka;

import com.zayden.userservice.dto.UserPointDto;
import com.zayden.userservice.messagequeue.producer.UserPointProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SendKafkaToDBForUpdateUserPointAspect {
    private final UserPointProducer userPointProducer;
    private static final String kafkaTopicName= "kafka.topics.user-to-db-for-point";
    private String logMessage;

    @Before("@annotation(com.zayden.userservice.annotation.SendKafkaToDBForUpdateUserPointMessage)")
    public void after(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        UserPointDto userPointDto = (UserPointDto) args[0];
        this.logMessage = (String) args[1];
        userPointProducer.send(kafkaTopicName, userPointDto);
        log.info("AFTER THE KAFKA SEND : "+ setLogMessage(kafkaTopicName, userPointDto));
    }

    private String setLogMessage(String kafkaTopicName, UserPointDto userPointDto){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(kafkaTopicName);
        stringBuilder.append("[");
        stringBuilder.append(userPointDto.getUserId());
        stringBuilder.append(" ] ");
        stringBuilder.append(" : ");
        stringBuilder.append(userPointDto.getAmount()+" "+logMessage);
        return stringBuilder.toString();
    }
}
