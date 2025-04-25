package com.device.api.client.impl;

import com.device.api.client.RestClientFactory;
import com.device.api.client.SensorMonitoringClient;
import com.device.api.model.SensorMonitoringResponseDto;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    /*---------------------------------------------------------------------
        na aula 11.7 demonstrou como substituir essa classe por annotation
        na interface SensorMonitoring, podendo desconsiderar esta classe
        nesta classe pode-se obter mais configurações e maior controle
        com as annotations fica mais simples e mais genérico
     ----------------------------------------------------------------------*/

    private final RestClient restClient;

    // construtor rest client
    // 8082 é o microsserviço temperature-monitoring
    public SensorMonitoringClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
    }

    @Override
    public void enableMonitoring(TSID sensorId) {
        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void disableMonitoring(TSID sensorId) {
        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity(); // delete não tem body
    }

    @Override
    public SensorMonitoringResponseDto getDetailSensor(TSID sensorId) {
        return restClient.get()
                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
                .retrieve()
                .body(SensorMonitoringResponseDto.class);
    }
}
