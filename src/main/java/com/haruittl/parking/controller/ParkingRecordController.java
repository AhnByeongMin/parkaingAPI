package com.haruittl.parking.controller;

import com.haruittl.parking.entity.ParkingRecord;
import com.haruittl.parking.service.ParkingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-records")
public class ParkingRecordController {

    private final ParkingRecordService parkingRecordService;

    @Autowired
    public ParkingRecordController(ParkingRecordService parkingRecordService) {
        this.parkingRecordService = parkingRecordService;
    }

    @PostMapping
    public ResponseEntity<ParkingRecord> createParkingRecord(@RequestBody ParkingRecord parkingRecord) {
        ParkingRecord createdRecord = parkingRecordService.createParkingRecord(parkingRecord);
        return ResponseEntity.ok(createdRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingRecord> getParkingRecordById(@PathVariable Long id) {
        ParkingRecord record = parkingRecordService.getParkingRecordById(id);
        if (record != null) {
            return ResponseEntity.ok(record);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ParkingRecord>> getAllParkingRecords() {
        List<ParkingRecord> records = parkingRecordService.getAllParkingRecords();
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingRecord> updateParkingRecord(@PathVariable Long id, @RequestBody ParkingRecord parkingRecord) {
        parkingRecord.setId(id);
        ParkingRecord updatedRecord = parkingRecordService.updateParkingRecord(parkingRecord);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingRecord(@PathVariable Long id) {
        parkingRecordService.deleteParkingRecord(id);
        return ResponseEntity.ok().build();
    }
}