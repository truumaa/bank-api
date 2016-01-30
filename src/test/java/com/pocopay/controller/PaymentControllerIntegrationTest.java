package com.pocopay.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.http.ContentType;
import com.pocopay.RestIntegrationTest;
import com.pocopay.TestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentControllerIntegrationTest extends RestIntegrationTest {

    @Test
    public void insertPayment_shouldReturnPaymentId() {
        given().contentType(ContentType.JSON)
                .body(TestConstants.CORRECT_PAYMENT_TEMPLATE)
                .when()
                .post(TestConstants.PAYMENT_URL)
                .then()
                .statusCode(SC_OK);
        //.body(Matchers.contains(Integer.class);
    }

    @Test
    public void insertPayment_shouldThrowErrorWhenInvalidSenderId() {
        given().contentType(ContentType.JSON)
                .body(TestConstants.INVALID_ID_PAYMENT_TEMPLATE)
                .when()
                .post(TestConstants.PAYMENT_URL)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void insertPayment_shouldThrowErrorWhenNegativeAmount() {
        given().contentType(ContentType.JSON)
                .body(TestConstants.NEGATIVE_AMOUNT_PAYMENT_TEMPLATE)
                .when()
                .post(TestConstants.PAYMENT_URL)
                .then()
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    public void insertPayment_shouldThrowErrorWhenNotEnoughFunds() {
        given().contentType(ContentType.JSON)
                .body(TestConstants.NOT_ENOUGH_FUNDS_PAYMENT_TEMPLATE)
                .when()
                .post(TestConstants.PAYMENT_URL)
                .then()
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    public void getPayment_shouldReturnPayment() {
        given().contentType(ContentType.JSON)
                .when()
                .get(TestConstants.PAYMENT_URL + TestConstants.CORRECT_PAYMENT_ID)
                .then()
                .statusCode(SC_OK)
                .body(containsString(TestConstants.PAYMENT_RESULTS));
    }

    @Test
    public void getPayment_shouldReturnErrorWhenInvalidId() {
        given().contentType(ContentType.JSON)
                .when()
                .get(TestConstants.PAYMENT_URL + TestConstants.INVALID_PAYMENT_ID)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

}