package dev.ahad.co2sensors;

public  class Co2Metrics {
    Integer maxLast30Days;
    Double avgLast30Days;

    public Co2Metrics(Integer maxLast30Days, Double avgLast30Days) {
        this.maxLast30Days = maxLast30Days;
        this.avgLast30Days = avgLast30Days;
    }

    public Integer getMaxLast30Days() {
        return maxLast30Days;
    }

    public Double getAvgLast30Days() {
        return avgLast30Days;
    }

    @Override
    public String toString() {
        return "Co2Metrics{" +
                "maxLast30Days=" + maxLast30Days +
                ", avgLast30Days=" + avgLast30Days +
                '}';
    }
}
