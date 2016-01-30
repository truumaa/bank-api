package com.pocopay.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Value("${config.defaultAmount}")
    private double defaultAmount;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    public synchronized Long insertAccount(Account account) {
        if (account.getName().isEmpty()) {
            logger.info("BadRequestException: Account name missing");
            throw new BadRequestException("Account name missing");
        }

        if (accountMapper.getAccountByName(account.getName()) != null) {
            logger.info("ForbiddenException: Account name already exists Name:{}", account.getName());
            throw new ForbiddenException("Account name already exists");
        }

        account.setAmount(BigDecimal.valueOf(defaultAmount));
        accountMapper.insertAccount(account);
        logger.info("New Account ID:{} and Name:{} created", account.getId(), account.getName());
        return account.getId();
    }

    public List<Payment> getAccountHistory(Long accountId) {
        //Validate that account exists
        getAccount(accountId);
        List<Payment> payments = paymentMapper.getPaymentsBySourceAccountId(accountId);
        if (payments.isEmpty()) {
            logger.info("BadRequestException: No payment history for ID:{} account", accountId);
            throw new BadRequestException("No payment history for an account");
        }
        return payments;
    }

    public Account getAccount(Long accountId) {
        Account account = accountMapper.getAccountById(accountId);
        if (account == null) {
            logger.info("BadRequestException: Invalid account ID:{} requested", accountId);
            throw new BadRequestException("Invalid account number");
        }
        return account;
    }

}
