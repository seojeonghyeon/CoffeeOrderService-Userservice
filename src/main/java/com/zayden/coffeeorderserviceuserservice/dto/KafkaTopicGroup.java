package com.zayden.coffeeorderserviceuserservice.dto;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * point:
 *   pay:
 *     status:
 *       pending: "PENDING"
 *       canceled: "CANCELED"
 *       confirmed: "CONFIRMED"
 *       rejected: "REJECTED"
 */
@Getter
public enum KafkaTopicGroup {
    PENDING(PayPointStatus.PENDING, Arrays.asList(KafkaTopic.DATABASESTOLOG)),
    CANCELED(PayPointStatus.CANCELED, Arrays.asList(KafkaTopic.DATABASESTOLOG, KafkaTopic.DATABASESTOPOINTSTATUS)),
    CONFIRMED(PayPointStatus.CONFIRMED, Arrays.asList(KafkaTopic.DATABASESTOLOG, KafkaTopic.DATABASESTOPOINTSTATUS)),
    REJECTED(PayPointStatus.REJECTED, Arrays.asList(KafkaTopic.DATABASESTOLOG)),
    LOOKUP(PayPointStatus.LOOKUP, Arrays.asList(KafkaTopic.DATABASESTOLOG)),
    NOEXIST(PayPointStatus.NOEXIST, Collections.EMPTY_LIST);

    private PayPointStatus payPointStatus;
    private List<KafkaTopic> kafkaTopicList;

    KafkaTopicGroup(PayPointStatus payPointStatus, List<KafkaTopic> kafkaTopicList){
        this.payPointStatus = payPointStatus;
        this.kafkaTopicList = kafkaTopicList;
    }

    public static KafkaTopicGroup findByPayPointStatus(PayPointStatus payPointStatus){
        return Arrays.stream(KafkaTopicGroup.values())
                .filter(kafkaTopicGroup -> kafkaTopicGroup.hasKafkaTopicGroup(payPointStatus))
                .findAny()
                .orElse(NOEXIST);
    }

    private boolean hasKafkaTopicGroup(PayPointStatus payPointStatus){
        return kafkaTopicList.stream()
                .anyMatch(kafkaTopic -> kafkaTopic.equals(payPointStatus));
    }


}
