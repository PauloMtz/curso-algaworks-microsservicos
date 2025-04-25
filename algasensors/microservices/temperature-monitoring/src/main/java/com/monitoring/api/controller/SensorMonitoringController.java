package com.monitoring.api.controller;

import com.monitoring.api.model.SensorMonitoringResponseDto;
import com.monitoring.domain.model.SensorId;
import com.monitoring.domain.model.SensorMonitoring;
import com.monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {

    private final SensorMonitoringRepository sensorMonitoringRepository;

    @GetMapping
    public SensorMonitoringResponseDto getDeatil(@PathVariable TSID sensorId) {

        // nesse caso, em vez de lançar exceção, optou-se por retornar os dados nulos
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);

        return SensorMonitoringResponseDto.builder()
                .id(sensorMonitoring.getId().getValue())
                .enabled(sensorMonitoring.getEnabled())
                .lastTemperature(sensorMonitoring.getLastTemperature())
                .updatedAt(sensorMonitoring.getUpdatedAt())
                .build();
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);

        // se o sensor já estiver ativo, retorna erro
        if (sensorMonitoring.getEnabled()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }

        sensorMonitoring.setEnabled(true);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

    @SneakyThrows
    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);

        // simula um erro por timeout
        if (!sensorMonitoring.getEnabled()) {
            Thread.sleep(Duration.ofSeconds(10).toMillis());
        }

        sensorMonitoring.setEnabled(false);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

    private SensorMonitoring findByIdOrDefault(TSID sensorId) {
        return sensorMonitoringRepository.findById(new SensorId(sensorId))
                .orElse(SensorMonitoring.builder()
                        .id(new SensorId(sensorId))
                        .enabled(false)
                        .lastTemperature(null)
                        .updatedAt(null)
                        .build());
    }
}
