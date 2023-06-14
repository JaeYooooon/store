package com.zerobase.store.domain.review.service;

import com.zerobase.store.domain.reserve.entity.Reserve;
import com.zerobase.store.domain.reserve.entity.status.CheckStatus;
import com.zerobase.store.domain.reserve.repository.ReserveRepository;
import com.zerobase.store.domain.review.dto.ReviewDTO;
import com.zerobase.store.domain.review.entity.Review;
import com.zerobase.store.domain.review.repository.ReviewRepository;
import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.domain.shop.repository.ShopRepository;
import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.domain.user.repository.UserRepository;
import com.zerobase.store.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;

import static com.zerobase.store.domain.reserve.entity.status.CheckStatus.*;
import static com.zerobase.store.domain.reserve.entity.status.ReserveStatus.APPROVED;
import static com.zerobase.store.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReserveRepository reserveRepository;
    private final ShopRepository shopRepository;


    // 리뷰 작성
    @Transactional
    public void createReview(ReviewDTO reviewDTO, Principal principal) {
        User user = userRepository.findByUserName(principal.getName())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Shop shop = shopRepository.findById(reviewDTO.getShopId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        Reserve reserve = reserveRepository.findById(reviewDTO.getReserveId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVE));


        // 본인의 예약 내역이 아닌경우
        if (!reserve.getUser().equals(user)) {
            throw new CustomException(NO_PERMISSION);
        }

        // 이용을 하지않았거나, 예약시간보다 이전인 경우
        if(reserve.getReservedTime().isBefore(LocalDateTime.now()) || !reserve.getCheckStatus().equals(CHECKED_IN)){
            throw new CustomException(REVIEW_AFTER_SERVICE);
        }

        // 별점 0 ~ 5 사이
        if (reviewDTO.getStar() < 0 || reviewDTO.getStar() > 5) {
            throw new CustomException(INVALID_STAR);
        }

        Review review = new Review();
        review.setShop(shop);
        review.setUser(user);
        review.setContent(reviewDTO.getContent());
        review.setStar(reviewDTO.getStar());
        shop.getReviewList().add(review);

        // 리뷰 저장
        reviewRepository.save(review);

        // 별점 평균 계산
        shop.calculateAverageRating();
        shopRepository.save(shop);
    }
}
