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

import static org.junit.jupiter.api.Assertions.assertEquals;

class Co2SensorTrackerTest {

    private static final String COMMA_DELIMITER = ",";
    private final String testDataDirectory = "sensor-data/";
    Co2SensorTracker co2SensorTracker = new Co2SensorTracker(new SensorRepository());


    @Test
    void latestSensorStatusShouldBeOK() throws IOException {
        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");
        File censorStatusOkOnCompletion = okFile();
        List<Co2Sensor> co2Sensors = readDataFrom(censorStatusOkOnCompletion);
        co2Sensors.forEach(data -> co2SensorTracker.measureSensorStatus(data, sensorId));
        Co2Sensor co2Sensor = co2SensorTracker.currentStatusOfSensor(sensorId);
//        co2Sensor.forEach(data -> System.out.println(data));


        assertEquals(Co2SensorStatus.OK, co2Sensor.currentStatus);

    }

    @Test
    void latestSensorStatusShouldBeOK1() throws IOException {
        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");
        File censorStatusOkOnCompletion = okFile1();
        List<Co2Sensor> co2Sensors = readDataFrom(censorStatusOkOnCompletion);
        co2Sensors.forEach(data -> co2SensorTracker.measureSensorStatus(data, sensorId));
        Co2Sensor co2Sensor = co2SensorTracker.currentStatusOfSensor(sensorId);

//        co2Sensor.forEach(data -> System.out.println(data));


        assertEquals(Co2SensorStatus.OK, co2Sensor.currentStatus);

    }

    @Test
    void latestSensorStatusShouldBeALERT() throws IOException {
        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");
        File censorStatusOkOnCompletion = alertFile();
        List<Co2Sensor> co2Sensors = readDataFrom(censorStatusOkOnCompletion);
        co2Sensors.forEach(data -> co2SensorTracker.measureSensorStatus(data, sensorId));
        Co2Sensor co2Sensor = co2SensorTracker.currentStatusOfSensor(sensorId);

//        co2Sensor.forEach(data -> System.out.println(data));


        assertEquals(Co2SensorStatus.ALERT, co2Sensor.currentStatus);

    }

    @Test
    void latestSensorStatusShouldBeALERT1() throws IOException {
        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");
        File censorStatusOkOnCompletion = alertFile1();
        List<Co2Sensor> co2Sensors = readDataFrom(censorStatusOkOnCompletion);
        co2Sensors.forEach(data -> co2SensorTracker.measureSensorStatus(data, sensorId));
        Co2Sensor co2Sensor = co2SensorTracker.currentStatusOfSensor(sensorId);

//        co2Sensor.forEach(data -> System.out.println(data));


        assertEquals(Co2SensorStatus.ALERT, co2Sensor.currentStatus);

    }

    @Test
    void latestSensorStatusShouldBeWARNING() throws IOException {
        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");
        File censorStatusOkOnCompletion = warningFile();
        List<Co2Sensor> co2Sensors = readDataFrom(censorStatusOkOnCompletion);
        co2Sensors.forEach(data -> co2SensorTracker.measureSensorStatus(data, sensorId));
        Co2Sensor co2Sensor = co2SensorTracker.currentStatusOfSensor(sensorId);


        assertEquals(Co2SensorStatus.WARN, co2Sensor.currentStatus);

    }


    private File okFile() {
        System.out.println(UUID.randomUUID());
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(testDataDirectory + "co2sensors_ok.csv").getFile());
        return file;

    }

    private File okFile1() {
        System.out.println(UUID.randomUUID());
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(testDataDirectory + "co2sensors_ok_1.csv").getFile());
        return file;

    }

    private File alertFile() {
        System.out.println(UUID.randomUUID());
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(testDataDirectory + "co2sensors_alert.csv").getFile());
        return file;

    }

    private File alertFile1() {
        System.out.println(UUID.randomUUID());
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(testDataDirectory + "co2sensors_alert_1.csv").getFile());
        return file;

    }

    private File warningFile() {
        System.out.println(UUID.randomUUID());
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(testDataDirectory + "co2sensors_warning.csv").getFile());
        return file;

    }


    private List<Co2Sensor> readDataFrom(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        List<Co2Sensor> records = Files.readAllLines(path)
                .stream()
                .map(line -> {
                    String[] split = line.split(COMMA_DELIMITER);
                    return new Co2Sensor(UUID.fromString(split[0]), Integer.valueOf(split[1]), Instant.parse(split[2]));
                })
                .collect(Collectors.toList());
        return records;
    }


}