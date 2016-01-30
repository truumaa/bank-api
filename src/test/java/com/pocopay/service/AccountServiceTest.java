package com.pocopay.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.pocopay.TestHelper;
import com.pocopay.domain.Account;
import com.pocopay.domain.Payment;
import com.pocopay.exception.BadRequestException;
import com.pocopay.persistence.AccountMapper;
import com.pocopay.persistence.PaymentMapper;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void getAccount_shouldReturnAccount() {
        Account account1 = TestHelper.getDummyAccount(1L, "name", 34.56);
        when(accountMapper.getAccountById(1L)).thenReturn(account1);

        Account account2 = accountService.getAccount(1L);
        assertEquals(account1.getId(), account2.getId());
        assertEquals(account1.getName(), account2.getName());
        assertEquals(account1.getAmount(), account2.getAmount());
    }

    @Test(expected = BadRequestException.class)
    public void getAccount_shouldThrowErrorWhenAccountIdInvalid() {
        accountService.getAccount(0L);
    }

    @Test(expected = BadRequestException.class)
    public void getAccountHistory_shouldReturnErrorWhenNoHistory() {
        Account account1 = TestHelper.getDummyAccount(1L, "name", 34.56);
        when(accountMapper.getAccountById(1L)).thenReturn(account1);
        when(paymentMapper.getPaymentsBySourceAccountId(anyLong())).thenReturn(new ArrayList<>());
        accountService.getAccountHistory(1L);
    }

    @Test
    public void getAccountHistory_shouldReturnAccountHistory() {
        Account account1 = TestHelper.getDummyAccount(1L, "name", 34.56);
        when(accountMapper.getAccountById(1L)).thenReturn(account1);

        List<Payment> payments = new ArrayList<>();
        Payment dummyPayment1 = TestHelper.getDummyPayment(1L, 33.5, "transfer1");
        Payment dummyPayment2 = TestHelper.getDummyPayment(2L, 76.5, "transfer2");
        payments.add(dummyPayment1);
        payments.add(dummyPayment2);
        when(paymentMapper.getPaymentsBySourceAccountId(anyLong())).thenReturn(payments);
        List<Payment> accountHistory = accountService.getAccountHistory(1L);

        TestHelper.verifyPayments(dummyPayment1, accountHistory.get(0));
        TestHelper.verifyPayments(dummyPayment2, accountHistory.get(1));
    }


}