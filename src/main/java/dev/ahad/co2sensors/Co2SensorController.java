package dev.ahad.co2sensors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sensors")
public class Co2SensorController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Co2SensorTracker calculator;
    private Co2SensorMetrics sensorMetrics;

    public Co2SensorController(Co2SensorTracker co2SensorTracker, Co2SensorMetrics co2SensorMetrics) {
        this.calculator = co2SensorTracker;
        this.sensorMetrics = co2SensorMetrics;
    }

    @PostMapping("/{uuid}/measurements")
    public void co2Sensor(@PathVariable String uuid, @RequestBody Co2Sensor sensorData) {

        logger.info("co2Sensor: {} - {}", uuid, sensorData);

        this.calculator.measureSensorStatus(sensorData, UUID.fromString(uuid));

    }


    @GetMapping("/{uuid}")
    public Co2Sensor statusOf(@PathVariable String uuid) {
        Co2Sensor co2Sensor = this.calculator.currentStatusOfSensor(UUID.fromString(uuid));

        return co2Sensor;

    }

    @GetMapping("/{uuid}/metrics")
    public Co2Metrics metricsOf(@PathVariable String uuid) {
        Co2Metrics co2Metrics = this.sensorMetrics.last30DaysMetricsOf(UUID.fromString(uuid));
        return co2Metrics;

    }

    @GetMapping("/test")
    public String metricsOf() {
        return "HEllo";

    }
}
