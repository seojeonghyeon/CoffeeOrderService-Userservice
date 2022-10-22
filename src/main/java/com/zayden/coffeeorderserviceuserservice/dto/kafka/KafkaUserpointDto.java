package com.zayden.coffeeorderserviceuserservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaUserpointDto implements Serializable {
    private Schema schema;
    private PayloadUserpoint payload;
}
