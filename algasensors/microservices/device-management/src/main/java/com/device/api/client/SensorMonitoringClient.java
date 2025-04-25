package com.device.api.client;

import com.device.api.model.SensorMonitoringResponseDto;
import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {

    void enableMonitoring(TSID sensorId);
    void disableMonitoring(TSID sensorId);
    SensorMonitoringResponseDto getDetailSensor(TSID sensorId);
}
