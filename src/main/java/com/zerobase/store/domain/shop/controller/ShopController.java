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
@RequestMapping("/shop")
@Api(tags = "유저 상점 API")
public class ShopController {

    private final ShopService shopService;

    @ApiOperation(value = "상점 목록", notes = "서비스에 등록되어 있는 모든 상점 조회")
    @GetMapping
    public ResponseEntity<List<ShopDTO>> getShopList(Pageable pageable){

        return shopService.getShopList(pageable);
    }

    @ApiOperation(value = "상점 조회", notes = "특정 상점의 상세정보 조회")
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDTO.getShop> getShop(@PathVariable("shopId") Long shopId) {
        ShopDTO.getShop shopDTO = shopService.getShop(shopId);
        return ResponseEntity.ok(shopDTO);
    }

    @ApiOperation(value = "상점 정렬", notes = "상점을 가나다 순으로 정렬")
    @GetMapping("/name")
    public ResponseEntity<List<ShopDTO>> getShopSortedByName(Pageable pageable){

        return shopService.getShopSortedByName(pageable);
    }

    @ApiOperation(value = "상점 정렬", notes = "상점을 별점 순으로 정렬")
    @GetMapping("/star")
    public ResponseEntity<List<ShopDTO>> getShopSortedByStarAvg(Pageable pageable){

        return shopService.getShopSortedByStarAvg(pageable);
    }

    @ApiOperation(value = "상점 검색", notes = "키워드로 상점검색")
    @GetMapping("/search")
    public ResponseEntity<List<ShopDTO>> searchShopByName(@RequestParam("name") String name,
                                                          Pageable pageable){

        return shopService.searchShopByName(name, pageable);
    }
}
