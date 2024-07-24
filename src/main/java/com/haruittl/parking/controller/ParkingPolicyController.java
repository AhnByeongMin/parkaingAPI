package com.haruittl.parking.controller;

import com.haruittl.parking.entity.ParkingPolicy;
import com.haruittl.parking.service.ParkingPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-policies")
public class ParkingPolicyController {

  @Autowired
  private ParkingPolicyService parkingPolicyService;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<ParkingPolicy>> getAllPolicies() {
    return ResponseEntity.ok(parkingPolicyService.getAllParkingPolicies());
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ParkingPolicy> createPolicy(@RequestBody ParkingPolicy parkingPolicy) {
    return ResponseEntity.ok(parkingPolicyService.createParkingPolicy(parkingPolicy));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ParkingPolicy> updatePolicy(@PathVariable Long id,
      @RequestBody ParkingPolicy parkingPolicy) {
    parkingPolicy.setId(id);
    return ResponseEntity.ok(parkingPolicyService.updateParkingPolicy(parkingPolicy));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deletePolicy(@PathVariable Long id) {
    parkingPolicyService.deleteParkingPolicy(id);
    return ResponseEntity.ok().build();
  }
}