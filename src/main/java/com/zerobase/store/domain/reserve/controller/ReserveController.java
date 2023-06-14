package com.zerobase.store.domain.reserve.controller;

import com.zerobase.store.domain.reserve.dto.ReserveDTO;
import com.zerobase.store.domain.reserve.service.ReserveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve")
@Api(tags = "예약 API")
public class ReserveController {
    private final ReserveService reserveService;


    @ApiOperation(value = "예약 생성", notes = "상점, 예약할 시간을 입력하여 예약진행")
    @PostMapping
    public ResponseEntity<String> createReserve(@RequestBody ReserveDTO reserveDTO,
                                                    Principal principal){

        reserveService.createReserve(reserveDTO, principal);

        return ResponseEntity.ok("예약 성공");
    }


    @ApiOperation(value = "예약 취소", notes = "예약번호를 통한 예약취소")
    @DeleteMapping("/{reserveId}")
    public ResponseEntity<String> cancelReserve(@PathVariable Long reserveId,
                                                Principal principal){

        reserveService.cancelReserve(reserveId, principal);

        return ResponseEntity.ok("예약 취소 성공");
    }

    @ApiOperation(value = "예약 변경", notes = "예약번호, 변경할 시간을 입력하여 예약변경")
    @PutMapping("/{reserveId}")
    public ResponseEntity<String> updateReserve(@PathVariable Long reserveId,
                                                @RequestParam("time") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime time,
                                                Principal principal){

        reserveService.updateReserve(reserveId, time, principal);

        return ResponseEntity.ok("예약 변경 성공");
    }


    @ApiOperation(value = "예약 목록", notes = "본인의 예약내역을 조회")
    @GetMapping
    public ResponseEntity<List<ReserveDTO>> getReserveListByUser(Principal principal) {
        List<ReserveDTO> reserveList = reserveService.getReserveListByUser(principal);
        return ResponseEntity.ok(reserveList);
    }

    @ApiOperation(value = "10분전 체크", notes = "에약번호를 통해 예약시간 10분전 체크인")
    @PostMapping("/{reserveId}/check")
    public ResponseEntity<String> checkIn(@PathVariable Long reserveId,
                                          Principal principal){

        reserveService.checkIn(reserveId, principal);
        return ResponseEntity.ok("체크인 완료");
    }

}
