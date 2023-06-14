package com.zerobase.store.domain.review.controller;

import com.zerobase.store.domain.review.dto.ReviewDTO;
import com.zerobase.store.domain.review.repository.ReviewRepository;
import com.zerobase.store.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;


    // 리뷰 작성
    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO reviewDTO, Principal principal) {
        reviewService.createReview(reviewDTO, principal);
        return ResponseEntity.ok("리뷰 작성 성공");
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO, Principal principal) {
        reviewService.updateReview(reviewId, reviewDTO, principal);
        return ResponseEntity.ok("리뷰 수정 성공");
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId, Principal principal) {
        reviewService.deleteReview(reviewId, principal);
        return ResponseEntity.ok("리뷰 삭제 성공");
    }
}
