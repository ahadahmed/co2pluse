package dev.ahad.co2sensors;

import java.time.Instant;
import java.util.UUID;

public class Co2Sensor {


    UUID sensorId;
    Integer co2;
    Instant time;

    Co2SensorStatus currentStatus;
    Co2SensorStatus dataStatus;

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

    public Co2SensorStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Co2SensorStatus currentStatus) {
        this.currentStatus = currentStatus;
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
                ", co2=" + co2 +
                ", time=" + time +
                ", currentStatus=" + currentStatus +
                ", dataStatus=" + dataStatus +
                ", alertStartTime=" + alertStartTime +
                ", alertEndTime=" + alertEndTime +
                '}';
    }
}
