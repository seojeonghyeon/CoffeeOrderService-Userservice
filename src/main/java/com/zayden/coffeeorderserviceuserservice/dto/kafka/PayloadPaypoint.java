package com.zayden.coffeeorderserviceuserservice.dto.kafka;

import com.zayden.coffeeorderserviceuserservice.dto.PayPointStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayloadPaypoint {
    private String pay_id;
    private String user_id;
    private int amount;
    private PayPointStatus pay_status;
}
