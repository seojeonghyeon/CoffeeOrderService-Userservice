package com.zayden.coffeeorderserviceuserservice.aspect.kafka;

import com.zayden.coffeeorderserviceuserservice.annotation.SendKafkaToDBForLogMessage;
import com.zayden.coffeeorderserviceuserservice.annotation.SendKafkaToDBForUpdateUserPointMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BranchSendKafka {

    @SendKafkaToDBForLogMessage
    public void SendKafkaToDBForLog(JoinPoint jointPoint, String kafkaTopicName){
    }

    @SendKafkaToDBForUpdateUserPointMessage
    public void SendKafkaToDBForUpdateUserPoint(JoinPoint jointPoint, String kafkaTopicName){
    }

}
