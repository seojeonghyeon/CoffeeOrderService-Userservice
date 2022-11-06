package com.zayden.coffeeorderserviceuserservice.aspect.kafka;

import com.zayden.coffeeorderserviceuserservice.annotation.SendKafkaToDBForLogMessage;
import com.zayden.coffeeorderserviceuserservice.annotation.SendKafkaToDBForUpdateUserPointMessage;
import org.aspectj.lang.JoinPoint;

public class BranchSendKafka {

    @SendKafkaToDBForLogMessage
    public void SendKafkaToDBForLog(JoinPoint jointPoint, String kafkaTopicName){
    }

    @SendKafkaToDBForUpdateUserPointMessage
    public void SendKafkaToDBForUpdateUserPoint(JoinPoint jointPoint, String kafkaTopicName){
    }

}
