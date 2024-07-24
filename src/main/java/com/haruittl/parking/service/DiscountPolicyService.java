package com.haruittl.parking.service;

import com.haruittl.parking.entity.DiscountPolicy;
import com.haruittl.parking.repository.DiscountPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountPolicyService {

    private final DiscountPolicyRepository discountPolicyRepository;

    @Autowired
    public DiscountPolicyService(DiscountPolicyRepository discountPolicyRepository) {
        this.discountPolicyRepository = discountPolicyRepository;
    }

    public DiscountPolicy createDiscountPolicy(DiscountPolicy discountPolicy) {
        return discountPolicyRepository.save(discountPolicy);
    }

    public DiscountPolicy getDiscountPolicyById(Long id) {
        return discountPolicyRepository.findById(id).orElse(null);
    }

    public List<DiscountPolicy> getDiscountPoliciesByLocationName(String locationName) {
        return discountPolicyRepository.findByLocationName(locationName);
    }

    public List<DiscountPolicy> getAllDiscountPolicies() {
        return discountPolicyRepository.findAll();
    }

    public DiscountPolicy updateDiscountPolicy(DiscountPolicy discountPolicy) {
        return discountPolicyRepository.save(discountPolicy);
    }

    public void deleteDiscountPolicy(Long id) {
        discountPolicyRepository.deleteById(id);
    }
}