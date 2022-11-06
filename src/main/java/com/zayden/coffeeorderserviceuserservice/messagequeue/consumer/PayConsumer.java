package com.zayden.coffeeorderserviceuserservice.messagequeue.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zayden.coffeeorderserviceuserservice.dto.PayPointStatus;
import com.zayden.coffeeorderserviceuserservice.dto.PaypointDto;
import com.zayden.coffeeorderserviceuserservice.service.pointservice.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayConsumer {

    private static final String kafkaTopicNamefromPayRealName = "userservice-payment-convey";
    private final PointService pointService;

    @KafkaListener(topics = kafkaTopicNamefromPayRealName)
    public void updatePointpayment(String kafkaMessage){
        Map<Object, Object> map;
        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<>() {});
            PaypointDto paypointDto = initPaypointDtoMapper(map);
            pointService.updateUserPoint(paypointDto, paypointDto.getPayStatus());
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
                .payStatus((PayPointStatus) payload.get("pay_status"))
                .build();
        return paypointDto;
    }
}
