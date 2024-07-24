package com.haruittl.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking_policies")
@Getter
@Setter
public class ParkingPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private Integer baseTime; // 기본 시간 (분)

    @Column(nullable = false)
    private Integer baseFee; // 기본 요금

    @Column(nullable = false)
    private Integer additionalTime; // 추가 시간 단위 (분)

    @Column(nullable = false)
    private Integer additionalFee; // 추가 시간당 요금
}