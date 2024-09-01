package dev.ahad.co2sensors;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class Co2SensorMonitor {


    private SensorRepository sensorRepository;

    public Co2SensorMonitor(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    public Co2Sensor currentStatusOfSensor(UUID sensorId) {
        Co2Sensor co2Sensor = this.sensorRepository.getSensorData(sensorId).peekLast();
        return co2Sensor;
    }


    public void measureSensorStatus(Co2Sensor sensorReading, UUID sensorId) {


        if (sensorReading.co2 >= 2000) {
            sensorReading.dataStatus = Co2SensorStatus.WARN;
            sensorReading.currentStatus = Co2SensorStatus.WARN;
        }

        if (sensorReading.co2 < 2000) {
            sensorReading.dataStatus = Co2SensorStatus.OK;
            sensorReading.currentStatus = Co2SensorStatus.OK;

        }
        LinkedList<Co2Sensor> sensorMetrics = this.sensorRepository.getSensorData(sensorId);
        sensorMetrics.add(sensorReading);
        this.sensorRepository.getSensors().put(sensorId, sensorMetrics);
        this.checkLatest3(sensorMetrics);

    }


    private void checkLatest3(LinkedList<Co2Sensor> sensorMetrics) {

        List<Co2Sensor> last3SensorReadings = new ArrayList<>();
        Predicate<Co2Sensor> waring = metrics -> metrics.co2 >= 2000;
        Predicate<Co2Sensor> ok = metrics -> metrics.co2 < 2000;

        ListIterator<Co2Sensor> co2MetricsIterator = sensorMetrics.listIterator(sensorMetrics.size());

        for (int i = 0; i < 3 && co2MetricsIterator.hasPrevious(); i++) {
            Co2Sensor previous = co2MetricsIterator.previous();
            last3SensorReadings.add(previous);
        }
        Co2Sensor latestSensorReadings = last3SensorReadings.get(0);

        if (last3SensorReadings.size() == 3) {
            if (last3SensorReadings.stream().allMatch(waring)) {
                latestSensorReadings.setCurrentStatus(Co2SensorStatus.ALERT);
                latestSensorReadings.setAlertStartTime(latestSensorReadings.time);
            } else if (last3SensorReadings.stream().allMatch(ok)) {
                latestSensorReadings.setCurrentStatus(Co2SensorStatus.OK);
                latestSensorReadings.setAlertEndTime(latestSensorReadings.time);
            } else if (last3SensorReadings.get(1).getCurrentStatus() == Co2SensorStatus.ALERT) {
                latestSensorReadings.setCurrentStatus(Co2SensorStatus.ALERT);
                latestSensorReadings.setAlertStartTime(last3SensorReadings.get(1).getAlertStartTime());
            }
        }

    }


}
