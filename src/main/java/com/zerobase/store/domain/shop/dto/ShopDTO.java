package com.zerobase.store.domain.shop.dto;

import com.zerobase.store.domain.review.entity.Review;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDTO {
    private Long shopId;
    private String name;
    private String address1;
    private String address2;
    private Double starAvg;

    @Data
    public static class createShop{
        private String name;
        private String description;
        private String address1;
        private String address2;
    }

    @Data
    public static class updateShop {
        private String name;
        private String description;
        private String address1;
        private String address2;
    }

    @Data
    @Builder
    public static class getShop{
        private Long shopId;
        private String name;
        private String description;
        private String address1;
        private String address2;
        private Double starAvg;
        private List<Review> reviews;
    }
}
