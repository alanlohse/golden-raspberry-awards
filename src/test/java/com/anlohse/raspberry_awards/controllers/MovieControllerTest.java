package com.anlohse.raspberry_awards.controllers;


import com.anlohse.raspberry_awards.GoldenRaspberryAwardsApplication;
import com.anlohse.raspberry_awards.vo.AwardsResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = GoldenRaspberryAwardsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAwards() {
        AwardsResultVO response = this.restTemplate.getForObject("http://localhost:" + port + "/api", AwardsResultVO.class);
        assertEquals(1, response.getMin().size());
        assertEquals(1, response.getMax().size());
        assertEquals(5, response.getMin().get(0).getInterval());
        assertEquals(7, response.getMax().get(0).getInterval());
    }

}
