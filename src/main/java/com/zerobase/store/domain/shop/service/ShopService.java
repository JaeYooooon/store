package com.zerobase.store.domain.shop.service;

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
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.zerobase.store.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public void createShop(@RequestBody ShopDTO.createShop createShop,
                                       Principal principal){

        String userName = principal.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NONE_EXIST_USER));

        if(!user.getRoles().contains("PARTNER")){
            throw new CustomException(NO_PERMISSION);
        }

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
    public void updateShop(Long shopId, ShopDTO.updateShop updateShop, Principal principal) {
        String userName = principal.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NONE_EXIST_USER));

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(NONE_EXIST_SHOP));

        if (!shop.getUser().equals(user) && !user.getRoles().contains("PARTNER")) {
            throw new CustomException(NO_PERMISSION);
        }

        if (updateShop.getName() != null && !updateShop.getName().equals(shop.getName()) &&
                shopRepository.existsByName(updateShop.getName())) {
            throw new CustomException(ALREADY_EXIST_SHOP);
        }

        shop.setName(updateShop.getName());
        shop.setDescription(updateShop.getDescription());
        shop.setAddress1(updateShop.getAddress1());
        shop.setAddress2(updateShop.getAddress2());

        shopRepository.save(shop);
    }

    // 상점 삭제
    public void deleteShop(Long shopId, Principal principal) {
        String userName = principal.getName();

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NONE_EXIST_USER));
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(NONE_EXIST_SHOP));

        if (!shop.getUser().equals(user) && !user.getRoles().contains("PARTNER")) {
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
                .orElseThrow(() -> new CustomException(NONE_EXIST_SHOP));

        ShopDTO.getShop shopDTO = ShopDTO.getShop.builder()
                .shopId(shopId)
                .name(shop.getName())
                .description(shop.getDescription())
                .address1(shop.getAddress1())
                .address2(shop.getAddress2())
                .starAvg(shop.getStarAvg())
                .reviews(shop.getReviewList())
                .build();

        return shopDTO;
    }
}
