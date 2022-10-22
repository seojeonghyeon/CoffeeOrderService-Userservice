package com.zayden.coffeeorderserviceuserservice.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 해당 Annotation이 걸리면 Kafka를 통해 Pay Service로 MessageQueue가 전송 된다.
 * Before에서 KafkaTopicName을 가져오며
 * After에서 생성된 객체를 가져와 Kafka로 MessageQueue를 발행한다.
 *
 * com.zayden.userservice.aspect.SendKafkaToPayServiceAspect에 위치
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SendKafkaToPayServiceMessage {
}
