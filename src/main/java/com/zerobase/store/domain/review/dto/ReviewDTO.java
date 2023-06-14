package com.zerobase.store.domain.review.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private Long shopId;
    private Long userId;
    private Long reserveId;
    private String content;
    private int star;
}
