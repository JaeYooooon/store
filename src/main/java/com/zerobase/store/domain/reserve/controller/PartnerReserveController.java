package com.zerobase.store.domain.reserve.controller;

import com.zerobase.store.domain.reserve.dto.ReserveDTO;
import com.zerobase.store.domain.reserve.entity.status.ReserveStatus;
import com.zerobase.store.domain.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/reserve")
public class PartnerReserveController {
    private final ReserveService reserveService;

    // 예약상태변경
    @PutMapping("/status/{reserveId}")
    public ResponseEntity<String> updateReservationStatus(@PathVariable("reserveId") Long reserveId,
                                                          @RequestParam("status") ReserveStatus status,
                                                          Principal principal) {

        reserveService.updateReservationStatus(reserveId, status, principal);
        return ResponseEntity.ok("예약 상태 변경 완료");
    }

    // 내 상점의 예약 목록
    @GetMapping("/{shopId}")
    public ResponseEntity<List<ReserveDTO>> getReserveList(@PathVariable Long shopId,
                                                           Principal principal) {

        return ResponseEntity.ok(reserveService.getReserveList(shopId, principal));
    }
}
