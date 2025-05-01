package com.monitoring.domain.service;

import com.monitoring.api.model.TemperatureLogResponseDto;
import com.monitoring.domain.model.SensorId;
import com.monitoring.domain.repository.SensorAlertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorAlertService {

    private final SensorAlertRepository sensorAlertRepository;

    @Transactional
    public void handleAlert(TemperatureLogResponseDto temperatureLogData) {

        sensorAlertRepository.findById(new SensorId(temperatureLogData.getSensorId()))
                .ifPresentOrElse(alert -> {
                    if (alert.getMaxTemperature() != null
                        && temperatureLogData.getValue().compareTo(alert.getMaxTemperature()) >= 0) {
                            log.info("Alert max temperature: SensorId {} Temp {}",
                                    temperatureLogData.getSensorId(), temperatureLogData.getValue());
                    } else if (alert.getMinTemperature() != null
                        && temperatureLogData.getValue().compareTo(alert.getMinTemperature()) < 0) {
                            log.info("Alert min temperature: SensorId {} Temp {}",
                                    temperatureLogData.getSensorId(), temperatureLogData.getValue());
                    } else {
                        logIgnoredAlert(temperatureLogData);
                    }
                }, () -> {
                    logIgnoredAlert(temperatureLogData);
                });
    }

    private static void logIgnoredAlert(TemperatureLogResponseDto temperatureLogData) {
        log.info("Alert ignored: SensorId {} Temp {}",
                temperatureLogData.getSensorId(), temperatureLogData.getValue());
    }
}
