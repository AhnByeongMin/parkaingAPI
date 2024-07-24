package com.haruittl.parking.service;

import com.haruittl.parking.entity.ParkingRecord;
import com.haruittl.parking.entity.ParkingStatus;
import com.haruittl.parking.repository.ParkingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingRecordService {

    private final ParkingRecordRepository parkingRecordRepository;

    @Autowired
    public ParkingRecordService(ParkingRecordRepository parkingRecordRepository) {
        this.parkingRecordRepository = parkingRecordRepository;
    }

    public ParkingRecord createParkingRecord(ParkingRecord parkingRecord) {
        return parkingRecordRepository.save(parkingRecord);
    }

    public ParkingRecord getParkingRecordById(Long id) {
        return parkingRecordRepository.findById(id).orElse(null);
    }

    public List<ParkingRecord> getParkingRecordsByCarNumberAndStatus(String carNumber, ParkingStatus status) {
        return parkingRecordRepository.findByCarNumberAndStatus(carNumber, status);
    }

    public List<ParkingRecord> getParkingRecordsByLocationName(String locationName) {
        return parkingRecordRepository.findByLocationName(locationName);
    }

    public List<ParkingRecord> getAllParkingRecords() {
        return parkingRecordRepository.findAll();
    }

    public ParkingRecord updateParkingRecord(ParkingRecord parkingRecord) {
        return parkingRecordRepository.save(parkingRecord);
    }

    public void deleteParkingRecord(Long id) {
        parkingRecordRepository.deleteById(id);
    }
}