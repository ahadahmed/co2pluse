package dev.ahad.co2sensors;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class Co2SensorTracker {


    private SensorRepository sensorRepository;

    public Co2SensorTracker(SensorRepository sensorRepository) {
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
        LinkedList<Co2Sensor> sensorData = this.sensorRepository.getSensorData(sensorId);
        sensorData.add(sensorReading);
        this.sensorRepository.getSensors().put(sensorId, sensorData);
        this.checkLatest3(sensorData);

    }


    private void checkLatest3(LinkedList<Co2Sensor> sensorData) {

        List<Co2Sensor> last3SensorReadings = new ArrayList<>();
        Predicate<Co2Sensor> waring = data -> data.co2 >= 2000;
        Predicate<Co2Sensor> ok = data -> data.co2 < 2000;

        ListIterator<Co2Sensor> co2SensorListIterator = sensorData.listIterator(sensorData.size());

        for (int i = 0; i < 3 && co2SensorListIterator.hasPrevious(); i++) {
            Co2Sensor previous = co2SensorListIterator.previous();
            last3SensorReadings.add(previous);
        }
        Co2Sensor latestSensorData = last3SensorReadings.get(0);

        if (last3SensorReadings.size() == 3) {
            if (last3SensorReadings.stream().allMatch(waring)) {
                latestSensorData.setCurrentStatus(Co2SensorStatus.ALERT);
                latestSensorData.setAlertStartTime(latestSensorData.time);
            } else if (last3SensorReadings.stream().allMatch(ok)) {
                latestSensorData.setCurrentStatus(Co2SensorStatus.OK);
                latestSensorData.setAlertEndTime(latestSensorData.time);
            } else if (last3SensorReadings.get(1).getCurrentStatus() == Co2SensorStatus.ALERT) {
                latestSensorData.setCurrentStatus(Co2SensorStatus.ALERT);
                latestSensorData.setAlertStartTime(last3SensorReadings.get(1).getAlertStartTime());
            }
        }

    }


}
