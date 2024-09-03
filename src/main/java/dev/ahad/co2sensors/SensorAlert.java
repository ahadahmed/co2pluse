package dev.ahad.co2sensors;

import java.time.Instant;
import java.util.UUID;

public class SensorAlert {
    UUID sensorId;
    Instant alertStartTime;
    Instant alertEndTime;

    SensorAlert(UUID sensorId, Instant alertStartTime) {
        this.sensorId = sensorId;
        this.alertStartTime = alertStartTime;
    }

    public Instant getAlertStartTime() {
        return alertStartTime;
    }

    public Instant getAlertEndTime() {
        return alertEndTime;
    }

    public void setAlertEndTime(Instant alertEndTime) {
        this.alertEndTime = alertEndTime;
    }
}
