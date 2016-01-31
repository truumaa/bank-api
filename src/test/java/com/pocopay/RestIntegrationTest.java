package com.pocopay;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jayway.restassured.RestAssured;
import com.pocopay.service.PaymentService;

@WebAppConfiguration

@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
@Transactional(rollbackFor = Exception.class)
public class RestIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Value("${server.port}")
    private int serverPort;

    @Before
    public void setup() {
        logger.debug("REST integration test started on port {}", serverPort);
        RestAssured.port = serverPort;
    }

}
