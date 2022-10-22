package com.zayden.coffeeorderserviceuserservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaLogUserPointDto {
    private Schema schema;
    private PayloadLogUserPoint payload;
}
