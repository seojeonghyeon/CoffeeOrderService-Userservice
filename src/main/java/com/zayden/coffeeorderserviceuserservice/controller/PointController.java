package com.zayden.coffeeorderserviceuserservice.controller;


import com.zayden.userservice.dto.PaypointDto;
import com.zayden.userservice.service.pointservice.PointService;
import com.zayden.userservice.vo.RequestPoint;
import com.zayden.userservice.vo.ResponsePoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/point")
public class PointController {
    private static final String logMessageGET = "GET";
    private static final String logMessagePOST = "POST";
    private final PointService pointService;

    /*
     * Point 충전하기
     */
    @PostMapping("/plusPoint")
    public ResponseEntity<ResponsePoint> plusPointByUserUid(@RequestBody RequestPoint requestPoint){
        if(requestPoint.getUserId().isEmpty() || 0 > requestPoint.getAmount()){
            ResponsePoint responsePoint = ResponsePoint.builder().build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePoint);
        }

        PaypointDto paypointDto = pointService.setPaypointDtoByRequsetPoint(
                requestPoint, logMessagePOST
        );

        ResponsePoint responsePoint = ResponsePoint.builder()
                .userId(paypointDto.getUserId())
                .amount(paypointDto.getAmount())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responsePoint);
    }

    /*
     * 사용자 ID로 Point 조회하기
     */
    @GetMapping("/point")
    public ResponseEntity<ResponsePoint> getPointByUserUid(@RequestBody RequestPoint requestPoint){
        if(requestPoint.getUserId().isEmpty()){
            ResponsePoint responsePoint = ResponsePoint.builder().build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePoint);
        }
        int amount = pointService.getUserPointByUserId(requestPoint, logMessageGET);

        ResponsePoint responsePoint = ResponsePoint.builder()
                .userId(requestPoint.getUserId())
                .amount(amount)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responsePoint);
    }
}
