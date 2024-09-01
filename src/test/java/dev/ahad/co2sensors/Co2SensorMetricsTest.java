package dev.ahad.co2sensors;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Co2SensorMetricsTest {

    private static final String COMMA_DELIMITER = ",";
    private final SensorRepository sensorRepository = new SensorRepository();
    Co2SensorMonitor co2SensorMonitor = new Co2SensorMonitor(sensorRepository);
    Co2SensorMetrics co2SensorMetrics = new Co2SensorMetrics(sensorRepository);


    @Test
    void last30DaysMetricsOf() throws IOException {
        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");
        File testDataOfSensorStatusOk = okFile();
        List<Co2Sensor> co2Sensors = readDataFrom(testDataOfSensorStatusOk);
        co2Sensors.forEach(sensorReadings -> co2SensorMonitor.measureSensorStatus(sensorReadings, sensorId));
        Co2Metrics co2Metrics = co2SensorMetrics.last30DaysMetricsOf(sensorId);
        assertNotNull(co2Metrics);
        assertEquals(2002, co2Metrics.getMaxLast30Days());
        assertEquals(1996, co2Metrics.getAvgLast30Days());

    }

    @Test
    void last30DaysMetricsShouldBe0ForUnknownSensorId() throws IOException {
        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");
        UUID randomUUID = UUID.randomUUID();
        File testDataOfSensorStatusOk = okFile();
        List<Co2Sensor> co2Sensors = readDataFrom(testDataOfSensorStatusOk);
        co2Sensors.forEach(sensorReadings -> co2SensorMonitor.measureSensorStatus(sensorReadings, sensorId));
        Co2Metrics co2Metrics = co2SensorMetrics.last30DaysMetricsOf(randomUUID);
        assertNotNull(co2Metrics);
        assertEquals(0, co2Metrics.getMaxLast30Days());
        assertEquals(0, co2Metrics.getAvgLast30Days());

    }

    private File okFile() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return new File(classLoader.getResource("sensor-data/co2sensors_ok.csv").getFile());

    }

    private List<Co2Sensor> readDataFrom(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        return Files.readAllLines(path)
                .stream()
                .map(line -> {
                    String[] split = line.split(COMMA_DELIMITER);
                    return new Co2Sensor(UUID.fromString(split[0]), Integer.valueOf(split[1]), Instant.parse(split[2]));
                })
                .collect(Collectors.toList());
    }
}