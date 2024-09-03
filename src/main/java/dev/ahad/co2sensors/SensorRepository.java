package dev.ahad.co2sensors;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SensorRepository {

    private Map<UUID, LinkedList<Co2Sensor>> sensors;
    private Map<UUID, LinkedList<SensorAlert>> sensorAlerts;

    public SensorRepository() {
        this.sensors = new ConcurrentHashMap<>();
        this.sensorAlerts = new ConcurrentHashMap<>();
    }

    public Map<UUID, LinkedList<Co2Sensor>> getSensors(){
       return this.sensors;
    }

    public Map<UUID, LinkedList<SensorAlert>> sensorAlerts(){
        return this.sensorAlerts;
    }

    public LinkedList<Co2Sensor> getSensorData(UUID sensorId) {
        LinkedList<Co2Sensor> sensorReadings = this.sensors.getOrDefault(sensorId, new LinkedList<>());
        return sensorReadings;
    }

    public LinkedList<SensorAlert> getSensorAlerts(UUID sensorId) {
        LinkedList<SensorAlert> sensorAlerts = this.sensorAlerts.getOrDefault(sensorId, new LinkedList<>());
        return sensorAlerts;
    }
}
