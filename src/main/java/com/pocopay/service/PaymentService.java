package com.pocopay.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pocopay.domain.Account;
import com.pocopay.domain.Payment;
import com.pocopay.exception.BadRequestException;
import com.pocopay.exception.ForbiddenException;
import com.pocopay.persistence.AccountMapper;
import com.pocopay.persistence.PaymentMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaymentService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private AccountService accountService;

    public synchronized Integer insertPayment(Payment payment) {
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ForbiddenException("Transfer amount must be greater than zero");
        }

        Account sourceAccount = accountService.getAccount(payment.getSourceAccountId());
        Account destinationAccount = accountService.getAccount(payment.getDestinationAccountId());
        if (sourceAccount.getAmount().compareTo(payment.getAmount()) >= 0) {
            sourceAccount.setAmount(sourceAccount.getAmount().subtract(payment.getAmount()));
            accountMapper.updateAccount(sourceAccount);

            destinationAccount.setAmount(destinationAccount.getAmount().add(payment.getAmount()));
            accountMapper.updateAccount(destinationAccount);

            paymentMapper.insertPayment(payment);
            return payment.getId();
        } else {
            throw new ForbiddenException("Not enough funds to complete transfer");
        }
    }

    public Payment getPayment(Integer paymentId) {
        Payment payment = paymentMapper.getPaymentById(paymentId);
        if (payment == null) {
            throw new BadRequestException("Invalid payment number");
        }
        return payment;
    }

}
