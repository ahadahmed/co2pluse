package dev.ahad.co2sensors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Co2PulseApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String URI = "/api/v1/sensors";


    @ParameterizedTest
    @CsvFileSource(resources = "/sensor-data/co2sensors_ok.csv")
    void measureCo2Sensor(String uuid, Integer co2, Instant time) throws Exception {

        UUID sensorId = UUID.fromString(uuid);
        Co2Sensor co2Sensor = new Co2Sensor(co2, time);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(URI + "/{uuid}/measurements", sensorId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(co2Sensor)))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult);
    }


    @Test
    void getSensorStatus() throws Exception {
        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(URI + "/{uuid}", sensorId)
                )
                .andExpect(status().isOk())
                .andReturn();


        assertNotNull(mvcResult);
        String responseBody = mvcResult
                .getResponse()
                .getContentAsString();
        Co2Sensor co2Sensor = objectMapper.readValue(responseBody, Co2Sensor.class);
        System.out.println(co2Sensor);
        assertNotNull(co2Sensor);
        assertEquals(Co2SensorStatus.OK,co2Sensor.currentStatus);

    }

    @Test
    void getMetrics() throws Exception {

        UUID sensorId = UUID.fromString("f6a6daf8-191c-4d2e-81d2-d31350361689");
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(URI + "/{uuid}/metrics", sensorId)
                )
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        String responseBody = mvcResult.getResponse().getContentAsString();
        Co2Metrics co2Metrics = objectMapper.readValue(responseBody, Co2Metrics.class);
        assertNotNull(co2Metrics);
        assertEquals(2002, co2Metrics.getMaxLast30Days());
        assertEquals(1996, co2Metrics.getAvgLast30Days());

    }



}
