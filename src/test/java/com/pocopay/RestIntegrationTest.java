package com.pocopay;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jayway.restassured.RestAssured;

@WebAppConfiguration

@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port:0")
@Transactional(rollbackFor = Exception.class)
public class RestIntegrationTest {

    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setup() {
        //log.debug("REST integration test started on port {}", serverPort);
        RestAssured.port = serverPort;
    }

}
