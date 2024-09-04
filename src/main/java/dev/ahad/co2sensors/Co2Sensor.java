package dev.ahad.co2sensors;

import java.time.Instant;
import java.util.UUID;

public class Co2Sensor {


    UUID sensorId;
    Integer co2;
    Instant time;

    Co2SensorStatus sensorStatus;
    Co2SensorStatus readingStatus;

    Instant alertStartTime;
    Instant alertEndTime;



    public Co2Sensor(){

    }

    public Co2Sensor(Integer co2, Instant time) {
        this.co2 = co2;
        this.time = time;

    }

    public Co2Sensor(UUID sensorId, Integer co2, Instant time) {
        this.sensorId = sensorId;
        this.co2 = co2;
        this.time = time;

    }

    public Integer getCo2() {
        return co2;
    }

    public void setCo2(Integer co2) {
        this.co2 = co2;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Co2SensorStatus getSensorStatus() {
        return sensorStatus;
    }

    public void setSensorStatus(Co2SensorStatus sensorStatus) {
        this.sensorStatus = sensorStatus;
    }

    public Instant getAlertStartTime() {
        return alertStartTime;
    }

    public void setAlertStartTime(Instant alertStartTime) {
        this.alertStartTime = alertStartTime;
    }

    public Instant getAlertEndTime() {
        return alertEndTime;
    }

    public void setAlertEndTime(Instant alertEndTime) {
        this.alertEndTime = alertEndTime;
    }

    @Override
    public String toString() {
        return "Co2Sensor{" +
                "sensorId=" + sensorId +
                ", time=" + time +
                ", co2=" + co2 +
                ", readingStatus=" + readingStatus +
                ", sensorStatus=" + sensorStatus +
                ", alertStartTime=" + alertStartTime +
                ", alertEndTime=" + alertEndTime +
                '}';
    }
}
