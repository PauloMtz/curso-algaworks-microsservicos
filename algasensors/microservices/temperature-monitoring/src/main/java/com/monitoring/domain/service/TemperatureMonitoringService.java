package com.monitoring.domain.service;

import com.monitoring.api.model.TemperatureLogResponseDto;
import com.monitoring.domain.model.SensorId;
import com.monitoring.domain.model.SensorMonitoring;
import com.monitoring.domain.model.TemperatureLog;
import com.monitoring.domain.model.TemperatureLogId;
import com.monitoring.domain.repository.SensorMonitoringRepository;
import com.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureMonitoringService {

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    @Transactional
    public void processTemperatureReading(TemperatureLogResponseDto temperatureLogData) {
        sensorMonitoringRepository.findById(new SensorId(temperatureLogData.getSensorId()))
                .ifPresentOrElse(sensor -> handleSensorMonitoring(temperatureLogData, sensor),
                        () -> logIgnoredTemperature(temperatureLogData));
    }

    private void handleSensorMonitoring(TemperatureLogResponseDto temperatureLogData, SensorMonitoring sensor) {
        if (sensor.isEnabled()) {
            sensor.setLastTemperature(temperatureLogData.getValue());
            sensor.setUpdatedAt(OffsetDateTime.now());
            sensorMonitoringRepository.save(sensor);

            TemperatureLog temperatureLog = TemperatureLog.builder()
                    .id(new TemperatureLogId(temperatureLogData.getId()))
                    .registeredAt(temperatureLogData.getRegisteredAt())
                    .value(temperatureLogData.getValue())
                    .sensorId(new SensorId(temperatureLogData.getSensorId()))
                    .build();

            temperatureLogRepository.save(temperatureLog);
            log.info("Temperature updated: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
        } else {
            logIgnoredTemperature(temperatureLogData);
        }
    }

    private void logIgnoredTemperature(TemperatureLogResponseDto temperatureLogData) {
        log.info("Temperature ignored: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
    }
}
