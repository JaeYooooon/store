package com.zerobase.store.domain.review.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private Long userId;
    private String content;
    private int star;
}
