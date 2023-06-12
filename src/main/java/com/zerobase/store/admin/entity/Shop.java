package com.zerobase.store.admin.entity;

import com.zerobase.store.global.BaseEntity;
import com.zerobase.store.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "SHOP")
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private String address1;
    private String address2;

    private double latitude;
    private double longitude;

    private Double starAvg;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviewList;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public void calculateAverageRating() {
        if (reviewList.isEmpty()) {
            starAvg = 0.0;
        } else {
            double sum = 0.0;
            for (Review review : reviewList) {
                sum += review.getStar();
            }
            starAvg = sum / reviewList.size();
        }
    }
}
