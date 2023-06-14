package com.zerobase.store.domain.shop.service;

import com.zerobase.store.domain.review.dto.ReviewDTO;
import com.zerobase.store.domain.review.dto.ReviewDetailDTO;
import com.zerobase.store.domain.shop.dto.ShopDTO;
import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.domain.shop.repository.ShopRepository;
import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.domain.user.repository.UserRepository;
import com.zerobase.store.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.store.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createShop(@RequestBody ShopDTO.createShop createShop,
                                       Principal principal){

        String userName = principal.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if(!user.getRoles().contains("PARTNER")){
            throw new CustomException(NO_PERMISSION);
        }

        // 상점명이 없거나 이미 등록된 상점명인경우
        if (createShop.getName() != null && shopRepository.existsByName(createShop.getName())) {
            throw new CustomException(ALREADY_EXIST_SHOP);
        }

        Shop shop = new Shop();
        shop.setUser(user);
        shop.setName(createShop.getName());
        shop.setDescription(createShop.getDescription());
        shop.setAddress1(createShop.getAddress1());
        shop.setAddress2(createShop.getAddress2());
        shop.setStarAvg(0.0);

        shopRepository.save(shop);
    }

    // 상점 수정
    @Transactional
    public void updateShop(Long shopId, ShopDTO.updateShop updateShop, Principal principal) {
        String userName = principal.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        // 파트너 계정이여도 본인의 가게가 아니면 수정 불가능
        if (!shop.getUser().equals(user) || !user.getId().equals(shop.getUser().getId())) {
            throw new CustomException(NO_PERMISSION);
        }

        // 이미 등록된 이름이거나, 기존의 이름과 동일하다면
        if (updateShop.getName().equals(shop.getName()) || shopRepository.existsByName(updateShop.getName())) {
            throw new CustomException(ALREADY_EXIST_SHOP);
        }

        shop.setName(updateShop.getName());
        shop.setDescription(updateShop.getDescription());
        shop.setAddress1(updateShop.getAddress1());
        shop.setAddress2(updateShop.getAddress2());

        shopRepository.save(shop);
    }

    // 상점 삭제
    @Transactional
    public void deleteShop(Long shopId, Principal principal) {
        String userName = principal.getName();

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        // 파트너 계정이여도 본인의 가게가 아니면 삭제 불가능
        if (!shop.getUser().equals(user) || !user.getId().equals(shop.getUser().getId())) {
            throw new CustomException(NO_PERMISSION);
        }

        shopRepository.delete(shop);
    }

    // 상점 목록
    public ResponseEntity<List<ShopDTO>> getShopList(Pageable pageable){
        Page<Shop> shopPage = shopRepository.findAll(pageable);
        List<Shop> shopList = shopPage.getContent();

        List<ShopDTO> shopDTOList = new ArrayList<>();
        for(Shop shop : shopList){
            shop.calculateAverageRating();
            ShopDTO shopDTO = ShopDTO.builder()
                    .shopId(shop.getId())
                    .name(shop.getName())
                    .address1(shop.getAddress1())
                    .address2(shop.getAddress2())
                    .starAvg(shop.getStarAvg())
                    .build();

            shopDTOList.add(shopDTO);
        }

        return ResponseEntity.ok(shopDTOList);
    }


    // 상점 상세 조회
    public ShopDTO.getShop getShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        List<ReviewDetailDTO> reviewDTOList = shop.getReviewList().stream()
                .map(review -> {
                    ReviewDetailDTO reviewDTO = new ReviewDetailDTO();
                    reviewDTO.setId(review.getId());
                    reviewDTO.setShopId(shopId);
                    reviewDTO.setUserId(review.getUser().getId());
                    reviewDTO.setContent(review.getContent());
                    reviewDTO.setStar(review.getStar());

                    return reviewDTO;
                })
                .collect(Collectors.toList());

        shop.calculateAverageRating();

        ShopDTO.getShop shopDTO = ShopDTO.getShop.builder()
                .shopId(shopId)
                .name(shop.getName())
                .description(shop.getDescription())
                .address1(shop.getAddress1())
                .address2(shop.getAddress2())
                .starAvg(shop.getStarAvg())
                .reviews(reviewDTOList)  // 리뷰 목록 설정
                .build();

        return shopDTO;
    }

    // 이름순
    public ResponseEntity<List<ShopDTO>> getShopSortedByName(Pageable pageable){
        Page<Shop> shopPage = shopRepository.findAllByOrderByNameAsc(pageable);
        List<Shop> shopList = shopPage.getContent();

        List<ShopDTO> shopDTOList = shopList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(shopDTOList);
    }

    // 별점순
    public ResponseEntity<List<ShopDTO>> getShopSortedByStarAvg(Pageable pageable){
        Page<Shop> shopPage = shopRepository.findAllByOrderByStarAvgDesc(pageable);
        List<Shop> shopList = shopPage.getContent();

        List<ShopDTO> shopDTOList = shopList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(shopDTOList);
    }

    // 리뷰 많은순

    // 이름으로 상점 검색
    public ResponseEntity<List<ShopDTO>> searchShopByName(String name, Pageable pageable){
        Page<Shop> shopPage = shopRepository.findByNameContainingIgnoreCase(name, pageable);
        List<Shop> shopList = shopPage.getContent();

        List<ShopDTO> shopDTOList = shopList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(shopDTOList);
    }

    public ShopDTO convertToDTO(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopId(shop.getId());
        shopDTO.setName(shop.getName());
        shopDTO.setAddress1(shop.getAddress1());
        shopDTO.setAddress2(shop.getAddress2());
        shopDTO.setStarAvg(shop.getStarAvg());
        return shopDTO;
    }
}
