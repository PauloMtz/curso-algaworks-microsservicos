package com.monitoring.infrastructure.rabbitmq;

import com.monitoring.api.model.TemperatureLogResponseDto;
import com.monitoring.domain.service.SensorAlertService;
import com.monitoring.domain.service.TemperatureMonitoringService;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

import static com.monitoring.infrastructure.rabbitmq.RabbitMQConfig.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService;
    private final SensorAlertService sensorAlertService;

    @SneakyThrows
    @RabbitListener(queues = QUEUE_PROCESS_TEMPERATURE)
    public void handleProcessTemperature(@Payload TemperatureLogResponseDto temperatureLogData,
                       @Headers Map<String, Object> headers) {

        /*TSID sensorId = temperatureLogData.getSensorId();
        Double temperature = temperatureLogData.getValue();
        log.info("Temperature updated: SensorId {} Temp {}", sensorId, temperature);
        log.info("Headers: {}", headers.toString());*/

        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5).toMillis());
    }

    @SneakyThrows
    @RabbitListener(queues = QUEUE_ALERTING)
    public void handleAlerting(@Payload TemperatureLogResponseDto temperatureLogData,
                       @Headers Map<String, Object> headers) {
        //log.info("Alerting: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
        sensorAlertService.handleAlert(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5).toMillis());
    }
}
