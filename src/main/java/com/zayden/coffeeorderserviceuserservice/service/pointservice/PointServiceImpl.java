package com.zayden.coffeeorderserviceuserservice.service.pointservice;

import com.zayden.userservice.annotation.SendKafkaToDBForLogMessage;
import com.zayden.userservice.annotation.SendKafkaToDBForUpdateUserPointMessage;
import com.zayden.userservice.annotation.SendKafkaToPayServiceMessage;
import com.zayden.userservice.dto.PaypointDto;
import com.zayden.userservice.dto.UserPointDto;
import com.zayden.userservice.jpa.UserPointEntity;
import com.zayden.userservice.jpa.UserPointRepository;
import com.zayden.userservice.vo.RequestPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService{


    private static final String statusPendingName = "point.pay.status.pending";
    private static final String statusConfirmedName = "point.pay.status.confirmed";
    private final Environment env;
    private final UserPointRepository userPointRepository;

    /*
     * 사용자의 Point 내역을 업데이트 한다.
     */
    @SendKafkaToDBForLogMessage
    @Override
    public void updateUserPoint(PaypointDto paypointDto, String logMessage) {
        if(paypointDto.getPayStatus().equals(env.getProperty(statusConfirmedName))){
            UserPointDto userPointDto = UserPointDto.builder()
                    .userId(paypointDto.getUserId())
                    .amount(paypointDto.getAmount())
                    .build();
            sendUpdatePayStatusToDB(userPointDto, logMessage);
        }
    }

    @SendKafkaToDBForUpdateUserPointMessage
    private void sendUpdatePayStatusToDB(UserPointDto userPointDto, String logMessage){
    }

    /*
     * 사용자의 ID를 이용해서 사용자의 Point 내역을 조회한다.
     */
    @SendKafkaToDBForLogMessage
    @Override
    public int getUserPointByUserId(RequestPoint requestPoint, String logMessage) {
        Optional<UserPointEntity> optionalUserPointEntity = userPointRepository.findByUserId(requestPoint.getUserId());
        if(optionalUserPointEntity.isPresent()){
            return optionalUserPointEntity.get().getAmount();
        }
        return 0;
    }

    /*
     * PaypointDto를 RequestPoint로 변환한다.
     * 이때 전, 후로 kafka를 통해 Payservice로 메세지큐를 발행하기 위한 처리를 실시한다.
     */
    @SendKafkaToDBForLogMessage
    @SendKafkaToPayServiceMessage
    @Override
    public PaypointDto setPaypointDtoByRequsetPoint(RequestPoint requestPoint, String logMessage) {
        PaypointDto paypointDto = PaypointDto.builder()
                .userId(requestPoint.getUserId())
                .amount(requestPoint.getAmount())
                .build();
        paypointDto.setPayStatusName(env.getProperty(statusPendingName));
        paypointDto.createPayId();
        return paypointDto;
    }

}
