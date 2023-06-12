package com.zerobase.store.user.entity;

import com.zerobase.store.global.BaseEntity;
import com.zerobase.store.reserve.entity.Reserve;
import com.zerobase.store.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "USER")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 신짱구

    @Column(unique = true)
    private String userName; // zzang9

    private String password; // zzang99

    @Column(unique = true)
    private String phoneNum; // 010-0000-0000

    @Builder.Default
    private String role = "USER";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reserve> reserveList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviewList;
}
