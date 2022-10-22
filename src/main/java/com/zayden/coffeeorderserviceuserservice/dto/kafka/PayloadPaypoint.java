package com.zayden.coffeeorderserviceuserservice.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayloadPaypoint {
    private String pay_id;
    private String user_id;
    private int amount;
    private String pay_status;
}
