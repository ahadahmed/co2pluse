package dev.ahad.co2sensors;

import java.util.*;

import static dev.ahad.co2sensors.Co2SensorMonitor.OK;
import static dev.ahad.co2sensors.Co2SensorMonitor.WARNING;


public class AlertChecker {

    private SensorRepository sensorRepository;
    private LinkedList<Co2Sensor> sensorMetrics;
    private LinkedList<SensorAlert> sensorAlerts;

    AlertChecker(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;

    }

    void checkSensorStatus(UUID sensorId) {
        this.sensorMetrics = this.sensorRepository.getSensorData(sensorId);
        this.sensorAlerts = this.sensorRepository.getSensorAlerts(sensorId);
        Map<UUID, LinkedList<SensorAlert>> alerts = this.sensorRepository.sensorAlerts();

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
                this.sensorAlerts.add(alert);
                alerts.putIfAbsent(sensorId, sensorAlerts);
                lastReading.setAlertStartTime(lastReading.time);
            } else if (last3SensorReadings.stream().allMatch(OK)) {
                lastReading.setSensorStatus(Co2SensorStatus.OK);
                if (!sensorAlerts.isEmpty()) {
                    lastReading.setAlertEndTime(lastReading.time);
                    sensorAlerts.peekLast().setAlertEndTime(lastReading.time);
                    alerts.putIfAbsent(sensorId, sensorAlerts);
                }

            } else if (secondLastReading.getSensorStatus() == Co2SensorStatus.ALERT) {
                lastReading.setSensorStatus(Co2SensorStatus.ALERT);
                lastReading.setAlertStartTime(secondLastReading.getAlertStartTime());
            }
        }

    }
}

