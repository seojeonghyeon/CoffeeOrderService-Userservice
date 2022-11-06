package com.zayden.coffeeorderserviceuserservice.service.pointservice;

import com.zayden.coffeeorderserviceuserservice.dto.PayPointStatus;
import com.zayden.coffeeorderserviceuserservice.dto.PaypointDto;

public interface PointService {
    void updateUserPoint(PaypointDto paypointDto, PayPointStatus payPointStatus);
    int getUserPointByUserId(PaypointDto paypointDto, PayPointStatus payPointStatus);
    boolean isUserId(PaypointDto paypointDto, PayPointStatus payPointStatus);
    void sendKafkaToPayService(PaypointDto paypointDto, PayPointStatus payPointStatus);
}
