package dev.ahad.co2sensors;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SensorRepository {

    private Map<UUID, LinkedList<Co2Sensor>> sensors;

    public SensorRepository() {
        this.sensors = new ConcurrentHashMap<>();
    }

    public Map<UUID, LinkedList<Co2Sensor>> getSensors(){
       return this.sensors;
    }

    public LinkedList<Co2Sensor> getSensorData(UUID sensorId) {
        LinkedList<Co2Sensor> sensorReadings = this.sensors.getOrDefault(sensorId, new LinkedList<>());
        return sensorReadings;
    }
}
