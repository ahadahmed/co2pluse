package dev.ahad.co2sensors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Predicate;

@SpringBootApplication
public class Co2PulseApplication {
    public static final Predicate<Co2Sensor> WARNING = metrics -> metrics.co2 >= 2000;
    public static final Predicate<Co2Sensor> OK = metrics -> metrics.co2 < 2000;

    public static void main(String[] args) {
        SpringApplication.run(Co2PulseApplication.class, args);
    }

}
