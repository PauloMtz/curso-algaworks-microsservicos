package com.monitoring.api.model;

import lombok.Data;

@Data
public class SensorAlertRequestDto {

    private Double maxTemperature;
    private Double minTemperature;
}
