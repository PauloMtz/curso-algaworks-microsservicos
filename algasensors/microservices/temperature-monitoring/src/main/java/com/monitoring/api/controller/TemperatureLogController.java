package com.monitoring.api.controller;

import com.monitoring.api.model.TemperatureLogResponseDto;
import com.monitoring.domain.model.SensorId;
import com.monitoring.domain.model.TemperatureLog;
import com.monitoring.domain.repository.TemperatureLogRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensors/{sensorId}/temperature")
public class TemperatureLogController {

    private final TemperatureLogRepository temperatureLogRepository;

    @GetMapping
    public Page<TemperatureLogResponseDto> search(@PathVariable TSID sensorId,
                                                  @PageableDefault Pageable pageable) {

        Page<TemperatureLog> temperatureLogs = temperatureLogRepository
                .findAllBySensorId(new SensorId(sensorId), pageable);

        return temperatureLogs.map(tempLog -> TemperatureLogResponseDto.builder()
                .id(tempLog.getId().getValue())
                .value(tempLog.getValue())
                .registeredAt(tempLog.getRegisteredAt())
                .sensorId(tempLog.getSensorId().getValue())
                .build());
    }
}
