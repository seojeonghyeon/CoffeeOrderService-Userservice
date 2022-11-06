package com.zayden.coffeeorderserviceuserservice.api;


import com.zayden.coffeeorderserviceuserservice.dto.PayPointStatus;
import com.zayden.coffeeorderserviceuserservice.dto.PaypointDto;
import com.zayden.coffeeorderserviceuserservice.service.pointservice.PointService;
import com.zayden.coffeeorderserviceuserservice.vo.RequestPoint;
import com.zayden.coffeeorderserviceuserservice.vo.ResponsePoint;
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
    private static final PayPointStatus PENDING = PayPointStatus.PENDING;
    private static final PayPointStatus LOOKUP = PayPointStatus.LOOKUP;

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

        PaypointDto paypointDto = PaypointDto.builder()
                .userId(requestPoint.getUserId())
                .amount(requestPoint.getAmount())
                .build();
        paypointDto.createPayId();
        paypointDto.setPayStatusName(PENDING);

        if(pointService.isUserId(paypointDto, LOOKUP)){
            pointService.sendKafkaToPayService(paypointDto, PENDING);
        }

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
        PaypointDto paypointDto = PaypointDto.builder()
                .userId(requestPoint.getUserId())
                .build();
        paypointDto.createPayId();
        paypointDto.setPayStatusName(LOOKUP);

        int amount = pointService.getUserPointByUserId(paypointDto, LOOKUP);

        ResponsePoint responsePoint = ResponsePoint.builder()
                .userId(paypointDto.getUserId())
                .amount(amount)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responsePoint);
    }
}
