package dev.ahad.co2sensors;

import java.util.*;

import static dev.ahad.co2sensors.Co2SensorMonitor.OK;
import static dev.ahad.co2sensors.Co2SensorMonitor.WARNING;


public class AlertChecker {

    private SensorRepository sensorRepository;

    AlertChecker(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    void checkSensorStatus(UUID sensorId) {
        LinkedList<Co2Sensor> sensorMetrics = this.sensorRepository.getSensorData(sensorId);
        LinkedList<SensorAlert> sensorAlerts = this.sensorRepository
                .getSensorAlerts(sensorId);
        List<Co2Sensor> last3SensorReadings = new ArrayList<>();


        ListIterator<Co2Sensor> iterator = sensorMetrics.listIterator(sensorMetrics.size());

        for (int i = 0; i < 3 && iterator.hasPrevious(); i++) {
            Co2Sensor previous = iterator.previous();
            last3SensorReadings.add(previous);
        }

        if (last3SensorReadings.size() == 3) {
            Co2Sensor lastReading = last3SensorReadings.get(0);
            Co2Sensor secondLastReading = last3SensorReadings.get(1);
            if (last3SensorReadings.stream().allMatch(WARNING)) {
                lastReading.setSensorStatus(Co2SensorStatus.ALERT);
                SensorAlert alert = new SensorAlert(sensorId, lastReading.time);
                sensorAlerts.add(alert);
                this.sensorRepository.sensorAlerts().putIfAbsent(sensorId, sensorAlerts);
                lastReading.setAlertStartTime(lastReading.time);
            } else if (last3SensorReadings.stream().allMatch(OK)) {

                lastReading.setSensorStatus(Co2SensorStatus.OK);
//                sensorAlerts.peekLast().setAlertEndTime(lastReading.time);
                this.sensorRepository.sensorAlerts()
                        .putIfAbsent(sensorId, sensorAlerts);
                lastReading.setAlertEndTime(lastReading.time);
            } else if (secondLastReading.getSensorStatus() == Co2SensorStatus.ALERT) {
                lastReading.setSensorStatus(Co2SensorStatus.ALERT);
                lastReading.setAlertStartTime(secondLastReading.getAlertStartTime());
            }
        }

    }
}

