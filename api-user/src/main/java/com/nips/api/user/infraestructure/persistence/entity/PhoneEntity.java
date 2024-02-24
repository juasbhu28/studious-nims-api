package com.nips.api.user.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.User;

@Entity
@Getter
@Setter
@Table(name = "phone")
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String number;

    @Column(nullable = false, length = 10)
    private String citycode;

    @Column(nullable = false, length = 10)
    private String countrycode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}