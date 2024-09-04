# CO2 PULSE

## Preface

Carbon Dioxide (CO2) is all around us and we are constantly expelling it, but in
high concentration CO2 can be harmful or even lethal.

CO2 Levels between 2000 and 5000 ppm are associated with headaches, sleeping-ness poor concentration, loss of attention, increased heart rate and slight nausea
may also be present.


## How to Start the Application

The application requires Java 17 and Maven 3.6.0+.

1. Run/Debug class `Co2PulseApplication` in the IDE of your choice

or

1. Run `mvn clean package` to build your application
2. Start the application with `java -jar target/co2-pulse-0.0.1-SNAPSHOT.jar`
   

## Task Overview
The task is to build a service capable of collecting data from hundreds of thousands of sensors and alert if the CO2 concentrations reach critical levels.

## Acceptance criteria
  • The service should be able to receive measurements from each sensor at the rate of 1 per minute
1. Alerting
2. Regular sensor status is OK
3. If the CO2 level equals or exceeds 2000 ppm, the sensor status should be set to
   WARN
4. If the service receives 3 or more consecutive measurements higher than 2000,
   the sensor status should be set to ALERT
5. When the sensor reaches status ALERT, it stays in this state until it receives 3
   consecutive measurements lower than 2000; then it moves to OK
6. Each sensor alert is stored, including the start time of the alert and end time of
   the alert
7. The service should provide the following metrics about each sensor:
   ◦ Average CO2 level for the last 30 days
   ◦ Maximum CO2 Level in the last 30 day


## API
#### Collect sensor measurements
`POST /api/v1/sensors/{uuid}/measurements`
```json
{
"co2" : 2000,
"time" : "2019-02-01T18:55:47+00:00"
}
```


#### Sensor status
`GET /api/v1/sensors/{uuid}`
#### Response:
```json
{
"status" : "OK" // Possible status OK,WARN,ALERT
}
```

#### Sensor metrics
`GET /api/v1/sensors/{uuid}/metrics`
#### Response:
```json
{
"maxLast30Days" : 1200,
"avgLast30Days" : 900
}
```


## Implementation Note:

For this project, an in-memory implementation is used for simplicity and rapid development. All sensor data, measurements, and alert statuses are stored in memory, allowing for quick access and manipulation without the need for a persistent database. This approach is suitable for testing and small-scale use but has limitations when scaling to production environments with large data volumes.
Sensor measurements, statuses, and alerts are stored in data structures `map` and `list` within the application memory. So the data will be lost upon application startup
