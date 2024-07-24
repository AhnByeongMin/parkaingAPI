package com.haruittl.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_records")
@Getter
@Setter
public class ParkingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String carNumber;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Integer duration; // 주차 시간 (분)

    private Integer fee;

    private Integer discount;

    private Integer finalFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}