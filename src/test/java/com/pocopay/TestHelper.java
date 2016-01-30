package com.pocopay;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.pocopay.domain.Account;
import com.pocopay.domain.Payment;

public class TestHelper {

    public static void verifyPayments(Payment payment, Payment payment1) {
        assertEquals(payment1.getId(), payment.getId());
        assertEquals(payment1.getAmount(), payment.getAmount());
        assertEquals(payment1.getDescription(), payment.getDescription());
        assertEquals(payment1.getSourceAccountId(), payment.getSourceAccountId());
        assertEquals(payment1.getDestinationAccountId(), payment.getDestinationAccountId());
        assertEquals(payment1.getTransactionDate(), payment.getTransactionDate());
    }

    public static Account getDummyAccount(int id, String name, double amount) {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setAmount(BigDecimal.valueOf(amount));
        return account;
    }

    public static Payment getDummyPayment(int id, double amount, String description) {
        Payment payment = new Payment();
        payment.setId(id);
        payment.setAmount(BigDecimal.valueOf(amount));
        payment.setDescription(description);
        payment.setSourceAccountId(0);
        payment.setDestinationAccountId(1);
        payment.setTransactionDate(Timestamp.valueOf("2016-01-30 10:10:10.0"));
        return payment;
    }
}
