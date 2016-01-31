package com.pocopay.service;

import static com.pocopay.exception.ForbiddenException.ExceptionCode;

import java.math.BigDecimal;
import java.text.MessageFormat;
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
    private BigDecimal defaultAmount;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    public synchronized Long insertAccount(Account account) {
        if (accountMapper.getAccountByName(account.getName()) != null) {
            throw new ForbiddenException(
                    ExceptionCode.ACCOUNT_NAME_EXISTS, MessageFormat.format(
                    "Account Name:{0} already exists ", account.getName()));
        }

        account.setAmount(defaultAmount);
        accountMapper.insertAccount(account);
        logger.info("New Account ID:{} and Name:{} created", account.getId(), account.getName());
        return account.getId();
    }

    public List<Payment> getAccountHistory(Long accountId) {
        verifyAccountExistence(accountId);
        return paymentMapper.getPaymentsBySourceAccountId(accountId);
    }

    private void verifyAccountExistence(Long accountId) {getAccount(accountId);}

    public Account getAccount(Long accountId) {
        Account account = accountMapper.getAccountById(accountId);
        if (account == null) {
            throw new BadRequestException(
                    BadRequestException.ExceptionCode.INVALID_ACCOUNT_ID, MessageFormat.format(
                    "Invalid account ID:{0} ", accountId));
        }
        return account;
    }

}
