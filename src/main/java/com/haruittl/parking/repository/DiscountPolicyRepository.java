package com.haruittl.parking.repository;

import com.haruittl.parking.entity.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {
    List<DiscountPolicy> findByLocationName(String locationName);
}