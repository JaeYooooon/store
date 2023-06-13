package com.zerobase.store.domain.reserve.controller;

import com.zerobase.store.domain.reserve.dto.ReserveDTO;
import com.zerobase.store.domain.reserve.entity.Reserve;
import com.zerobase.store.domain.reserve.service.ReserveService;
import lombok.Getter;
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
public class ReserveController {
    private final ReserveService reserveService;

    // 예약
    @PostMapping
    public ResponseEntity<String> createReserve(@RequestBody ReserveDTO reserveDTO,
                                                    Principal principal){

        reserveService.createReserve(reserveDTO, principal);

        return ResponseEntity.ok("예약 성공");
    }

    // 예약취소
    @DeleteMapping("/{reserveId}")
    public ResponseEntity<String> cancelReserve(@PathVariable Long reserveId,
                                                Principal principal){

        reserveService.cancelReserve(reserveId, principal);

        return ResponseEntity.ok("예약 취소 성공");
    }

    // 예약변경
    @PutMapping("/{reserveId}")
    public ResponseEntity<String> updateReserve(@PathVariable Long reserveId,
                                                @RequestParam("time") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime time,
                                                Principal principal){

        reserveService.updateReserve(reserveId, time, principal);

        return ResponseEntity.ok("예약 변경 성공");
    }


    // 유저 예약내역
    @GetMapping
    public ResponseEntity<List<ReserveDTO>> getReserveListByUser(Principal principal) {
        List<ReserveDTO> reserveList = reserveService.getReserveListByUser(principal);
        return ResponseEntity.ok(reserveList);
    }
}
