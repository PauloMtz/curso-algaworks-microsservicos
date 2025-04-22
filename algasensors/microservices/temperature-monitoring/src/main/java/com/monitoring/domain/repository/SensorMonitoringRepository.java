package com.monitoring.domain.repository;

import com.monitoring.domain.model.SensorId;
import com.monitoring.domain.model.SensorMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorMonitoringRepository extends JpaRepository<SensorMonitoring, SensorId> {
}
