package com.haruittl.parking.repository;

import com.haruittl.parking.entity.ParkingPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPolicyRepository extends JpaRepository<ParkingPolicy, Long> {
    ParkingPolicy findByLocationName(String locationName);
}