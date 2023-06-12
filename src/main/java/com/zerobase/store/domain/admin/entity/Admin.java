package com.zerobase.store.domain.admin.entity;

import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ADMIN")
public class Admin extends BaseEntity {
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
    private String role = "ADMIN";

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Shop> shopList;
}
