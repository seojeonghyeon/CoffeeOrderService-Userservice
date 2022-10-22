package com.zayden.coffeeorderserviceuserservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PointDto {
    private String payId;
    private String userId;
    private Integer amount;
}
