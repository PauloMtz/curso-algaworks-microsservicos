package com.device.api.model;

import lombok.Data;

@Data
public class SensorRequestDto {

    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;
    private Boolean enabled;
}
