package com.zerobase.store.domain.review.controller;

import com.zerobase.store.domain.review.dto.ReviewDTO;
import com.zerobase.store.domain.review.repository.ReviewRepository;
import com.zerobase.store.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO reviewDTO, Principal principal) {
        reviewService.createReview(reviewDTO, principal);
        return ResponseEntity.ok("리뷰 작성 성공");
    }
}
