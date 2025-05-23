package com.device.api.controller;

import com.device.api.client.SensorMonitoringClient;
import com.device.api.model.SensorDetailResponseDto;
import com.device.api.model.SensorMonitoringResponseDto;
import com.device.api.model.SensorRequestDto;
import com.device.api.model.SensorResponseDto;
import com.device.common.TSIDGenerator;
import com.device.domain.model.Sensor;
import com.device.domain.model.SensorId;
import com.device.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorRepository sensorRepository;
    private final SensorMonitoringClient sensorMonitoringClient;

    @GetMapping("{sensorId}")
    public SensorResponseDto getOne(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return toModel(sensor);
    }

    @GetMapping("{sensorId}/detail")
    public SensorDetailResponseDto getDetailSensor(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SensorMonitoringResponseDto sensorDetailDto = sensorMonitoringClient.getDetailSensor(sensorId);
        SensorResponseDto sensorResponseDto = toModel(sensor);

        return SensorDetailResponseDto.builder()
                .sensorMonitoringResponseDto(sensorDetailDto)
                .sensorResponseDto(sensorResponseDto)
                .build();
    }

    @GetMapping
    public Page<SensorResponseDto> search(@PageableDefault Pageable pageable) {
        Page<Sensor> sensors = sensorRepository.findAll(pageable);
        return sensors.map(this::toModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorResponseDto create(@RequestBody SensorRequestDto sensorInputDto) {

        // transforma os atributos para Dto para salvar no banco
        Sensor sensor= Sensor.builder()
                .id(new SensorId(TSIDGenerator.generateTSID()))
                .name(sensorInputDto.getName())
                .ip(sensorInputDto.getIp())
                .location(sensorInputDto.getLocation())
                .protocol(sensorInputDto.getProtocol())
                .model(sensorInputDto.getModel())
                .enabled(false)
                .build();

        sensor = sensorRepository.saveAndFlush(sensor);

        // transforma para Dto para exibição
        return toModel(sensor);
    }

    @PutMapping("{sensorId}")
    public SensorResponseDto update(@PathVariable TSID sensorId, @RequestBody SensorRequestDto requestDto) {
        // busca o id
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // copia as propriedades para a classe sensor
        sensor.setName(requestDto.getName());
        sensor.setLocation(requestDto.getLocation());
        sensor.setIp(requestDto.getIp());
        sensor.setModel(requestDto.getModel());
        sensor.setProtocol(requestDto.getProtocol());
        sensor.setEnabled(requestDto.getEnabled());

        // grava no banco
        sensor = sensorRepository.saveAndFlush(sensor);

        // exibe os novos dados
        return toModel(sensor);
    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensorRepository.delete(sensor);

        // quando excluir um sensor, desativar seu monitoramento
        sensorMonitoringClient.disableMonitoring(sensorId);
    }

    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(true);
        sensorRepository.save(sensor);

        sensorMonitoringClient.enableMonitoring(sensorId);
    }

    @PutMapping("/{sensorId}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(false);
        sensorRepository.save(sensor);

        sensorMonitoringClient.disableMonitoring(sensorId);
    }

    private SensorResponseDto toModel(Sensor sensor) {
        return SensorResponseDto.builder()
                .id(sensor.getId().getValue())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }
}
