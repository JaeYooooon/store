package com.zerobase.store.domain.shop.controller;

import com.zerobase.store.domain.shop.dto.ShopDTO;
import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.domain.shop.service.ShopService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    // 상점 등록
    @PostMapping
    public ResponseEntity<String> createShop(@RequestBody ShopDTO.createShop createShop,
                                           Principal principal){
        shopService.createShop(createShop, principal);

        return ResponseEntity.ok("상점등록 완료");
    }

    // 상점 수정
    @PutMapping("/{shopId}")
    public ResponseEntity<String> updateShop(@PathVariable("shopId") Long shopId,
                                             @RequestBody ShopDTO.updateShop updateShop,
                                             Principal principal) {
        shopService.updateShop(shopId, updateShop, principal);

        return ResponseEntity.ok("상점 수정 완료");
    }

    // 상점 삭제
    @DeleteMapping("/{shopId}")
    public ResponseEntity<String> deleteShop(@PathVariable("shopId") Long shopId,
                                             Principal principal) {
        shopService.deleteShop(shopId, principal);

        return ResponseEntity.ok("상점 삭제 완료");
    }

    // 상점 목록
    @GetMapping
    public ResponseEntity<List<ShopDTO>> getShopList(Pageable pageable){

        return shopService.getShopList(pageable);
    }

    // 상점 상세 조회
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDTO.getShop> getShop(@PathVariable("shopId") Long shopId) {
        ShopDTO.getShop shopDTO = shopService.getShop(shopId);
        return ResponseEntity.ok(shopDTO);
    }

    // 이름순
    @GetMapping("/name")
    public ResponseEntity<List<ShopDTO>> getShopSortedByName(Pageable pageable){

        return shopService.getShopSortedByName(pageable);
    }

    // 별점순
    @GetMapping("/star")
    public ResponseEntity<List<ShopDTO>> getShopSortedByStarAvg(Pageable pageable){

        return shopService.getShopSortedByStarAvg(pageable);
    }

    // 이름으로 상점 검색
    @GetMapping("/search")
    public ResponseEntity<List<ShopDTO>> searchShopByName(@RequestParam("name") String name,
                                                          Pageable pageable){

        return shopService.searchShopByName(name, pageable);
    }
}
