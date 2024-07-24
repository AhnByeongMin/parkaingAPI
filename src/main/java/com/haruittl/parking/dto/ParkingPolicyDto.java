package com.haruittl.parking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParkingPolicyDto {
    @NotBlank(message = "Location name is required")
    private String locationName;

    @NotNull(message = "Base time is required")
    @Min(value = 1, message = "Base time must be at least 1 minute")
    private Integer baseTime;

    @NotNull(message = "Base fee is required")
    @Min(value = 0, message = "Base fee must be non-negative")
    private Integer baseFee;

    @NotNull(message = "Additional time is required")
    @Min(value = 1, message = "Additional time must be at least 1 minute")
    private Integer additionalTime;

    @NotNull(message = "Additional fee is required")
    @Min(value = 0, message = "Additional fee must be non-negative")
    private Integer additionalFee;
}