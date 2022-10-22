package com.zayden.coffeeorderserviceuserservice.messagequeue.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zayden.userservice.dto.PaypointDto;
import com.zayden.userservice.service.pointservice.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayConsumer {

    private static final String logMessageUPDATE = "UPDATE";
    private static final String kafkaTopicNamefromPayRealName = "userservice-payment-convey";
    private final PointService pointService;

    @KafkaListener(topics = kafkaTopicNamefromPayRealName)
    public void updatePointpayment(String kafkaMessage){
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
            pointService.updateUserPoint(initPaypointDtoMapper(map), logMessageUPDATE);
        }catch (JsonProcessingException ex){
            log.info(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PaypointDto initPaypointDtoMapper(Map<Object, Object> map){
        LinkedHashMap<String, Object> payload = (LinkedHashMap<String, Object>)map.get("payload");
        PaypointDto paypointDto = PaypointDto.builder()
                .payId((String)payload.get("pay_id"))
                .userId((String)payload.get("user_id"))
                .amount((Integer)payload.get("amount"))
                .payStatus((String)payload.get("pay_status"))
                .build();
        return paypointDto;
    }
}
