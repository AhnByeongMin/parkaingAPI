package com.haruittl.parking.service;

import com.haruittl.parking.entity.ParkingPolicy;
import com.haruittl.parking.repository.ParkingPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingPolicyService {

    private final ParkingPolicyRepository parkingPolicyRepository;

    @Autowired
    public ParkingPolicyService(ParkingPolicyRepository parkingPolicyRepository) {
        this.parkingPolicyRepository = parkingPolicyRepository;
    }

    public ParkingPolicy createParkingPolicy(ParkingPolicy parkingPolicy) {
        return parkingPolicyRepository.save(parkingPolicy);
    }

    public ParkingPolicy getParkingPolicyById(Long id) {
        return parkingPolicyRepository.findById(id).orElse(null);
    }

    public ParkingPolicy getParkingPolicyByLocationName(String locationName) {
        return parkingPolicyRepository.findByLocationName(locationName);
    }

    public List<ParkingPolicy> getAllParkingPolicies() {
        return parkingPolicyRepository.findAll();
    }

    public ParkingPolicy updateParkingPolicy(ParkingPolicy parkingPolicy) {
        return parkingPolicyRepository.save(parkingPolicy);
    }

    public void deleteParkingPolicy(Long id) {
        parkingPolicyRepository.deleteById(id);
    }
}