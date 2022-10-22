package com.zayden.coffeeorderserviceuserservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPointDto {
    private String userId;
    private Integer amount;
}
