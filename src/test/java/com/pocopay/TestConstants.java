package com.pocopay;

public class TestConstants {

    public static final String ACCOUNT_URL = "/account/";
    public static final String ACCOUNT_HISTORY_URL = "/account/{accountId}/history";

    public static final String PAYMENT_URL = "/payment/";
    public static final String CORRECT_PAYMENT_TEMPLATE = " {\"sourceAccountId\": 0,\"destinationAccountId\": 1," +
            "\"amount\": 100.50," + "\"description\": \"money transfer\"}";
    public static final String INVALID_ID_PAYMENT_TEMPLATE = " {\"sourceAccountId\": 999,\"destinationAccountId\": 1," +
            "\"amount\": 100.50," + "\"description\": \"money transfer\"}";
    public static final String NEGATIVE_AMOUNT_PAYMENT_TEMPLATE = " {\"sourceAccountId\": 0,\"destinationAccountId\": 1," +
            "\"amount\": -100.50," + "\"description\": \"money transfer\"}";
    public static final String NOT_ENOUGH_FUNDS_PAYMENT_TEMPLATE = " {\"sourceAccountId\": 0,\"destinationAccountId\": 1," +
            "\"amount\": 100000.50," + "\"description\": \"money transfer\"}";
    public static final String HISTORY_RESULTS = "[{\"id\":3,\"sourceAccountId\":1,\"destinationAccountId\":0,\"amount\":100"
            + ".25,\"description\":\"transfer4\",\"transactionDate\":\"2015-01-28T15:04:10\"},{\"id\":4," +
            "\"sourceAccountId\":1,\"destinationAccountId\":0,\"amount\":237.10,\"description\":\"transfer5\"," +
            "\"transactionDate\":\"2015-01-28T15:04:40\"}]";
    public static final String ACCOUNT_RESULT = "{\"id\":1,\"name\":\"Existing Account\",\"amount\":3000.55}";
    public static final String NEW_ACCOUNT_TEMPLATE = "{\"name\":\"Integration Test Account\"}";
    public static final String INCORRECT_ACCOUNT_TEMPLATE = "{\"name\":\"\"}";
    public static final String EXISTING_ACCOUNT_TEMPLATE = "{\"name\":\"Existing Account\"}";
    public static final String PAYMENT_RESULTS = "{\"id\":1,\"sourceAccountId\":0,\"destinationAccountId\":1,\"amount\":437"
            + ".35," + "\"description\":\"transfer2\",\"transactionDate\":";

    public static Integer CORRECT_ACCOUNT_ID = 1;
    public static Integer INVALID_ACCOUNT_ID = 999;
    public static Integer NO_HISTORY_ACCOUNT_ID = 10;

    public static Integer CORRECT_PAYMENT_ID = 1;
    public static Integer INVALID_PAYMENT_ID = 999;

}
