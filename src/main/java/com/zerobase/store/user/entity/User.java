package com.zerobase.store.user.entity;

import com.zerobase.store.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

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
}
