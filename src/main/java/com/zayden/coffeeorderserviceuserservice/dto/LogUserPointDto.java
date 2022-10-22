package com.zayden.coffeeorderserviceuserservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogUserPointDto {
    String userId;
    String logMessage;
    int amount;
}
