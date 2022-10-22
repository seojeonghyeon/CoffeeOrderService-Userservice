package com.zayden.coffeeorderserviceuserservice.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayloadLogUserPoint {
    private String user_id;
    private int amount;
    private String log_message;
}
