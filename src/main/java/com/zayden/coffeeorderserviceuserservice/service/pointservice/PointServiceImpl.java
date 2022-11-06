package com.zayden.coffeeorderserviceuserservice.service.pointservice;

import com.zayden.coffeeorderserviceuserservice.annotation.SendKafka;
import com.zayden.coffeeorderserviceuserservice.annotation.SendKafkaToPayServiceMessage;
import com.zayden.coffeeorderserviceuserservice.dto.PayPointStatus;
import com.zayden.coffeeorderserviceuserservice.dto.PaypointDto;
import com.zayden.coffeeorderserviceuserservice.jpa.UserPointEntity;
import com.zayden.coffeeorderserviceuserservice.jpa.UserPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService{

    private final UserPointRepository userPointRepository;

    /*
     * 사용자의 Point 내역을 업데이트 한다.
     */
    @SendKafka
    @Override
    public void updateUserPoint(PaypointDto paypointDto, PayPointStatus payPointStatus) {
    }

    /*
     * 사용자의 ID를 이용해서 사용자의 Point 내역을 조회한다.
     */
    @SendKafka
    @Override
    public int getUserPointByUserId(PaypointDto paypointDto, PayPointStatus payPointStatus) {
        int amount = 0;
        Optional<UserPointEntity> optionalUserPointEntity = userPointRepository.findByUserId(paypointDto.getUserId());
        if(optionalUserPointEntity.isPresent()){
            amount = optionalUserPointEntity.get().getAmount();
        }
        return amount;
    }

    @SendKafka
    @Override
    public boolean isUserId(PaypointDto paypointDto, PayPointStatus payPointStatus) {
        return userPointRepository.findByUserId(paypointDto.getUserId()).isPresent();
    }

    @SendKafka
    @SendKafkaToPayServiceMessage
    @Override
    public void sendKafkaToPayService(PaypointDto paypointDto, PayPointStatus payPointStatus) {
    }

}
