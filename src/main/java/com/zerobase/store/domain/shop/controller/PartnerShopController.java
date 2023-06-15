package com.zerobase.store.domain.shop.controller;

import com.zerobase.store.domain.shop.dto.ShopDTO;
import com.zerobase.store.domain.shop.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/shop")
@Api(tags = "파트너 상점 API")
public class PartnerShopController {
    private final ShopService shopService;

    @ApiOperation(value = "상점 등록", notes = "이름, 설명, 주소를 입력하여 상점 등록")
    @PostMapping
    public ResponseEntity<String> createShop(@RequestBody ShopDTO.createShop createShop,
                                             Principal principal){
        shopService.createShop(createShop, principal);

        return ResponseEntity.ok("상점등록 완료");
    }

    @ApiOperation(value = "상점 수정", notes = "본인의 상점 이름,설명,주소 수정 가능")
    @PutMapping("/{shopId}")
    public ResponseEntity<String> updateShop(@PathVariable("shopId") Long shopId,
                                             @RequestBody ShopDTO.updateShop updateShop,
                                             Principal principal) {
        shopService.updateShop(shopId, updateShop, principal);

        return ResponseEntity.ok("상점 수정 완료");
    }

    @ApiOperation(value = "상점 삭제", notes = "본인이 등록한 상점 삭제")
    @DeleteMapping("/{shopId}")
    public ResponseEntity<String> deleteShop(@PathVariable("shopId") Long shopId,
                                             Principal principal) {
        shopService.deleteShop(shopId, principal);

        return ResponseEntity.ok("상점 삭제 완료");
    }

}
