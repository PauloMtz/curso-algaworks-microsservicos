package com.device.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorDetailResponseDto {

    private SensorResponseDto sensorResponseDto;
    private SensorMonitoringResponseDto sensorMonitoringResponseDto;
}
