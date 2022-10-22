package com.zayden.coffeeorderserviceuserservice.vo;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class RequestPoint {
    private String userId;
    private int amount;
}
