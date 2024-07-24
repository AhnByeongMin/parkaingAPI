package com.haruittl.parking.repository;

import com.haruittl.parking.entity.ParkingRecord;
import com.haruittl.parking.entity.ParkingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {
    List<ParkingRecord> findByCarNumberAndStatus(String carNumber, ParkingStatus status);
    List<ParkingRecord> findByLocationName(String locationName);
}