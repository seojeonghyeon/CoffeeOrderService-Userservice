package com.zayden.coffeeorderserviceuserservice.service.pointservice;

import com.zayden.userservice.dto.PaypointDto;
import com.zayden.userservice.vo.RequestPoint;

public interface PointService {
    void updateUserPoint(PaypointDto paypointDto, String logMessage);
    int getUserPointByUserId(RequestPoint requestPoint, String logMessage);
    PaypointDto setPaypointDtoByRequsetPoint(RequestPoint requestPoint, String logMessage);
}
