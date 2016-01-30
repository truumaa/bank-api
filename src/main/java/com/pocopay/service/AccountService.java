package com.pocopay.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pocopay.domain.Account;
import com.pocopay.domain.Payment;
import com.pocopay.exception.BadRequestException;
import com.pocopay.exception.ForbiddenException;
import com.pocopay.persistence.AccountMapper;
import com.pocopay.persistence.PaymentMapper;

@Service
public class AccountService {
    @Value("${config.defaultAmount}")
    private double defaultAmount;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    public synchronized Integer insertAccount(Account account) {
        if (account.getName().isEmpty()) {
            throw new BadRequestException("Account name missing");
        }

        if (accountMapper.getAccountByName(account.getName()) != null) {
            throw new ForbiddenException("Account name already exists");
        }

        account.setAmount(BigDecimal.valueOf(defaultAmount));
        accountMapper.insertAccount(account);
        return account.getId();
    }

    public List<Payment> getAccountHistory(Integer accountId) {
        getAccount(accountId);
        List<Payment> payments = paymentMapper.getPaymentsBySourceAccountId(accountId);
        if (payments.isEmpty()) {
            throw new BadRequestException("No payment history for an account");
        }
        return payments;
    }

    public Account getAccount(Integer accountId) {
        Account account = accountMapper.getAccountById(accountId);
        if (account == null) {
            throw new BadRequestException("Invalid account number");
        }
        return account;
    }

}
