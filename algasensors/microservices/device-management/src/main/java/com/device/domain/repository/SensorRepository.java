package com.device.domain.repository;

import com.device.domain.model.Sensor;
import com.device.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, SensorId> {
}
