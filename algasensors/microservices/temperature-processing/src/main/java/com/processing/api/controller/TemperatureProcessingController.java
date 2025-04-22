package com.processing.api.controller;

import com.processing.api.model.TemperatureLogResponseDto;
import com.processing.common.UUIDGenerator;
import io.hypersistence.tsid.TSID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequestMapping("/api/sensors/{sensorId}/temperature/data")
public class TemperatureProcessingController {

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public void data(@PathVariable TSID sensorId, @RequestBody String input) {

        if (input == null || input.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Double temperature;

        try {
            temperature = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        TemperatureLogResponseDto output = TemperatureLogResponseDto.builder()
                .id(UUIDGenerator.generateTimeBasedUUID())
                .sensorId(sensorId)
                .value(temperature)
                .registeredAt(OffsetDateTime.now())
                .build();

        log.info(output.toString());
    }
}
