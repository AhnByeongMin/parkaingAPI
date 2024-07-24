package com.haruittl.parking.controller;

import com.haruittl.parking.entity.DiscountPolicy;
import com.haruittl.parking.service.DiscountPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount-policies")
public class DiscountPolicyController {

  private final DiscountPolicyService discountPolicyService;

  /**
   * 제공된 {@linkDiscountPolicyService}를 사용하여 {@linkDiscountPolicyController}의 새 인스턴스를 생성
   *
   * @param discountPolicyService 할인 정책을 관리하는 데 사용되는 서비스.
   */
  @Autowired
  public DiscountPolicyController(DiscountPolicyService discountPolicyService) {
    this.discountPolicyService = discountPolicyService;
  }

  @PostMapping
  public ResponseEntity<DiscountPolicy> createDiscountPolicy(
      @RequestBody DiscountPolicy discountPolicy) {
    DiscountPolicy createdPolicy = discountPolicyService.createDiscountPolicy(discountPolicy);
    return ResponseEntity.ok(createdPolicy);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DiscountPolicy> getDiscountPolicyById(@PathVariable Long id) {
    DiscountPolicy policy = discountPolicyService.getDiscountPolicyById(id);
    if (policy != null) {
      return ResponseEntity.ok(policy);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<DiscountPolicy>> getAllDiscountPolicies() {
    List<DiscountPolicy> policies = discountPolicyService.getAllDiscountPolicies();
    return ResponseEntity.ok(policies);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DiscountPolicy> updateDiscountPolicy(@PathVariable Long id,
      @RequestBody DiscountPolicy discountPolicy) {
    discountPolicy.setId(id);
    DiscountPolicy updatedPolicy = discountPolicyService.updateDiscountPolicy(discountPolicy);
    return ResponseEntity.ok(updatedPolicy);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDiscountPolicy(@PathVariable Long id) {
    discountPolicyService.deleteDiscountPolicy(id);
    return ResponseEntity.ok().build();
  }
}