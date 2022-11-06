package com.zayden.coffeeorderserviceuserservice.aspect.kafka;


import com.zayden.coffeeorderserviceuserservice.dto.KafkaTopic;
import com.zayden.coffeeorderserviceuserservice.dto.KafkaTopicGroup;
import com.zayden.coffeeorderserviceuserservice.dto.PayPointStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SendKafkaAspect {
    private PayPointStatus payPointStatus;
    private final BranchSendKafka sendKafka;

    @Before("@annotation(com.zayden.coffeeorderserviceuserservice.annotation.SendKafka)")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        this.payPointStatus = (PayPointStatus) args[1];
        KafkaTopicGroup kafkaTopicGroup = KafkaTopicGroup.findByPayPointStatus(payPointStatus);
        List<KafkaTopic> kafkaTopicList = kafkaTopicGroup.getKafkaTopicList();
        for (KafkaTopic kafkaTopic : kafkaTopicList) {
            if(kafkaTopic == KafkaTopic.DATABASESTOLOG){
                sendKafka.SendKafkaToDBForLog(joinPoint, kafkaTopic.getKafkaTopic());
            }else if(kafkaTopic == KafkaTopic.DATABASESTOPOINTSTATUS){
                sendKafka.SendKafkaToDBForUpdateUserPoint(joinPoint, kafkaTopic.getKafkaTopic());
            }
        }
    }
}
