package com.device.api.client;

import com.device.api.client.impl.SensorMonitoringClientBadGatewayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    private final RestClient.Builder builder;

    public RestClient temperatureMonitoringRestClient() {
        /*--------------------------------------------------------------------
            demonstrou no final da aula 11.6 que se utilizar
            return RestClient.create("http://localhost:8082");
            não chama os métodos do jackson (serialização e desserailização)
            a aplicação dá erro
         ---------------------------------------------------------------------*/
        return builder.baseUrl("http://localhost:8082")
                .requestFactory(generateClientHttpRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new SensorMonitoringClientBadGatewayException();
                })
                .build();
    }

    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // valores didáticos para teste (simular erro de timeout)
        factory.setReadTimeout((int) Duration.ofSeconds(5).toMillis());
        factory.setConnectTimeout((int) Duration.ofSeconds(3).toMillis());
        return factory;
    }
}
