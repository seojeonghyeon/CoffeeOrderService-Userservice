package com.zayden.coffeeorderserviceuserservice.dto;


import lombok.Getter;

/**
 * kafka:
 *   topics:
 *     user-to-pay-for-point: "userservice-point-status"
 *     user-to-db-for-point: "userservice-user-point"
 *     user-to-db-for-log : "userservice-user-log"
 *     pay-to-user-for-pay: "userservice-payment-convey"
 */

@Getter
public enum KafkaTopic {
    PAYSERVICETOPOINTSTATUS("userservice-point-status"),
    DATABASESTOPOINTSTATUS("userservice-user-point"),
    DATABASESTOLOG("userservice-user-log"),
    USERSERVICETOPAYSTATUS("userservice-payment-convey");

    private String kafkaTopic;

    KafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }
}
