package com.pocopay.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.http.ContentType;
import com.pocopay.RestIntegrationTest;
import com.pocopay.TestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountControllerIntegrationTest extends RestIntegrationTest {

    @Test
    public void createAccount_shouldReturnPaymentId() {
        given().contentType(ContentType.JSON)
                .body(TestConstants.NEW_ACCOUNT_TEMPLATE)
                .when()
                .post(TestConstants.ACCOUNT_URL)
                .then()
                .statusCode(SC_OK);
    }

    @Test
    public void createAccount_shouldReturnErrorWhenNameMissing() {
        given().contentType(ContentType.JSON)
                .body(TestConstants.INCORRECT_ACCOUNT_TEMPLATE)
                .when()
                .post(TestConstants.ACCOUNT_URL)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void createAccount_shouldReturnErrorWhenNameAlreadyExists() {
        given().contentType(ContentType.JSON)
                .body(TestConstants.EXISTING_ACCOUNT_TEMPLATE)
                .when()
                .post(TestConstants.ACCOUNT_URL)
                .then()
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    public void getAccount_ShouldReturnAccount() {
        given().contentType(ContentType.JSON)
                .when()
                .get(TestConstants.ACCOUNT_URL + TestConstants.CORRECT_ACCOUNT_ID)
                .then()
                .statusCode(SC_OK)
                .body(Matchers.equalTo(TestConstants.ACCOUNT_RESULT));
    }

    @Test
    public void getAccount_ShouldReturnErrorWhenInvalidId() {
        given().contentType(ContentType.JSON)
                .when()
                .get(TestConstants.ACCOUNT_URL + TestConstants.INVALID_ACCOUNT_ID)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void getAccountHistory_ShouldReturnAccountHistory() {
        given().contentType(ContentType.JSON)
                .when()
                .get(TestConstants.ACCOUNT_HISTORY_URL, TestConstants.CORRECT_ACCOUNT_ID)
                .then()
                .statusCode(SC_OK)
                .body(Matchers.equalTo(TestConstants.HISTORY_RESULTS));
    }

    @Test
    public void getAccountHistory_ShouldReturnErrorWhenInvalidId() {
        given().contentType(ContentType.JSON)
                .when()
                .get(TestConstants.ACCOUNT_HISTORY_URL, TestConstants.INVALID_ACCOUNT_ID)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }


}