package dev.ahad.co2sensors;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class Co2SensorMonitor {


    private SensorRepository sensorRepository;
    public static final Predicate<Co2Sensor> WARNING = metrics -> metrics.co2 >= 2000;
    public static final Predicate<Co2Sensor> OK = metrics -> metrics.co2 < 2000;
    private AlertChecker alertChecker;

    public Co2SensorMonitor(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
        this.alertChecker = new AlertChecker(sensorRepository);
    }


    public Co2Sensor currentStatusOfSensor(UUID sensorId) {
        Co2Sensor co2Sensor = this.sensorRepository.getSensorData(sensorId).peekLast();
        return co2Sensor;
    }


    public void measureSensorStatus(Co2Sensor sensorReading, UUID sensorId) {


        if (WARNING.test(sensorReading)) {
            sensorReading.readingStatus = Co2SensorStatus.WARN;
            sensorReading.sensorStatus = Co2SensorStatus.WARN;
        }

        if (OK.test(sensorReading)) {
            sensorReading.readingStatus = Co2SensorStatus.OK;
            sensorReading.sensorStatus = Co2SensorStatus.OK;

        }
        LinkedList<Co2Sensor> sensorMetrics = this.sensorRepository.getSensorData(sensorId);
        sensorMetrics.add(sensorReading);

        this.sensorRepository.getSensors()
                .putIfAbsent(sensorId, sensorMetrics);

        this.alertChecker.checkSensorStatus(sensorId);

    }


}
