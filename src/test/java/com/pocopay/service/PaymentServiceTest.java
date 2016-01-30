package com.pocopay.service;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.pocopay.TestHelper;
import com.pocopay.domain.Account;
import com.pocopay.domain.Payment;
import com.pocopay.exception.BadRequestException;
import com.pocopay.exception.ForbiddenException;
import com.pocopay.persistence.AccountMapper;
import com.pocopay.persistence.PaymentMapper;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void getPayment_shouldReturnPayment() {
        Payment payment = TestHelper.getDummyPayment(1L, 53.5, "transfer1");
        when(paymentMapper.getPaymentById(1)).thenReturn(payment);
        Payment payment1 = paymentService.getPayment(1);
        TestHelper.verifyPayments(payment, payment1);
    }

    @Test(expected = BadRequestException.class)
    public void getPayment_shouldThrowErrorWhenWrongId() {
        Payment payment = TestHelper.getDummyPayment(1L, 53.5, "transfer1");
        when(paymentMapper.getPaymentById(1)).thenReturn(payment);
        Payment payment1 = paymentService.getPayment(2);
        TestHelper.verifyPayments(payment, payment1);
    }

    @Test(expected = ForbiddenException.class)
    public void insertPayment_shouldThrowErrorWhenNegativePaymentAmount() {
        Payment payment = TestHelper.getDummyPayment(1L, -123.50, "transfer1");
        paymentService.insertPayment(payment);
    }

    @Test(expected = ForbiddenException.class)
    public void insertPayment_shouldThrowErrorWhenNotEnoughFunds() {
        Account account1 = TestHelper.getDummyAccount(0L, "Account1", 54.32);
        when(accountService.getAccount(0L)).thenReturn(account1);
        Account account2 = TestHelper.getDummyAccount(1L, "Account2", 1002.15);
        when(accountService.getAccount(1L)).thenReturn(account2);

        Payment payment = TestHelper.getDummyPayment(1L, 133.5, "transfer1");
        paymentService.insertPayment(payment);
    }

    @Test(expected = BadRequestException.class)
    public void insertPayment_shouldThrowErrorWhenInvalidAccountNumber() {
        Account account1 = TestHelper.getDummyAccount(0L, "Account1", 554.32);
        when(accountService.getAccount(0L)).thenReturn(account1);
        when(accountService.getAccount(1L)).thenThrow(new BadRequestException("Invalid account number"));

        Payment payment = TestHelper.getDummyPayment(1L, 53.5, "transfer1");
        paymentService.insertPayment(payment);
    }

}