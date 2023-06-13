package com.zerobase.store.domain.shop.entity;

import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.global.entity.BaseEntity;
import com.zerobase.store.domain.review.entity.Review;
import lombok.*;
import org.springframework.validation.annotation.Validated;

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

    @Column(nullable = false)
    private String name;
    private String description;

    private String address1;
    private String address2;

    private Double starAvg;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviewList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Shop(Long shopId) {
        super();
    }

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
