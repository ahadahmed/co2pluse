package dev.ahad.co2sensors;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.UUID;

@Component
public class Co2SensorMetrics {

    private SensorRepository sensorRepository;

    public Co2SensorMetrics(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    public Co2Metrics last30DaysMetricsOf(UUID sensorId){
        Instant time = Instant.now().minus(30, ChronoUnit.DAYS);
        LinkedList<Co2Sensor> sensorData = this.sensorRepository.getSensorData(sensorId);
//        if(sensorData == null)
//            return null;

        Optional<Integer> maxLast30Days = sensorData.stream()
                .filter(data -> data.time.isAfter(time))
                .map(data -> data.co2)
                .reduce(Integer::max);
        OptionalDouble avgLast30Days = sensorData.stream()
                .filter(data -> data.time.isAfter(time))
                .map(data -> data.co2)
                .mapToInt(Integer::intValue)
                .average();

        System.out.println("Last 30 days: " + maxLast30Days + " avg: " + avgLast30Days);
        Co2Metrics co2Metrics = new Co2Metrics(maxLast30Days.orElse(0), avgLast30Days.orElse(0));
        return co2Metrics;

    }
}
