package com.zayden.coffeeorderserviceuserservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PaypointDto {
    private String payId;
    private String userId;
    private Integer amount;
    private PayPointStatus payStatus;

    public void setPayStatusName(PayPointStatus payStatus){
        this.payStatus = payStatus;
    }
    public void createPayId(){
        this.payId = UUID.randomUUID().toString();
    }
}
