package com.pocopay.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);


    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private AccountService accountService;

    public synchronized Long insertPayment(Payment payment) {
        paymentValidation(payment);
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.info(
                    "ForbiddenException: Negative transfer attempt Amount:{} SourceId:{} DestId:{} ",
                    payment.getAmount(),
                    payment.getSourceAccountId(),
                    payment.getDestinationAccountId());
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
            logger.info("New Payment with ID:{} created", payment.getId());
            return payment.getId();
        } else {
            logger.info(
                    "ForbiddenException: Not enough funds to complete transfer SourceId:{} DestId{}",
                    payment.getSourceAccountId(),
                    payment.getDestinationAccountId());
            throw new ForbiddenException("Not enough funds to complete transfer");
        }
    }

    private void paymentValidation(Payment payment) {
        if (payment.getAmount() == null) {
            logger.info("BadRequestException: Payment amount missing");
            throw new BadRequestException("Payment amount missing");
        }
        if (payment.getDescription() == null) {
            logger.info("BadRequestException: Payment description missing");
            throw new BadRequestException("Payment description missing");
        }
        if (payment.getSourceAccountId() == null) {
            logger.info("BadRequestException: Payment sourceId missing");
            throw new BadRequestException("Payment sourceId missing");
        }
        if (payment.getDestinationAccountId() == null) {
            logger.info("BadRequestException: Payment destinationId missing");
            throw new BadRequestException("Payment destinationId missing");
        }
    }

    public Payment getPayment(Integer paymentId) {
        Payment payment = paymentMapper.getPaymentById(paymentId);
        if (payment == null) {
            logger.info("BadRequestException: Invalid payment ID:{} requested", paymentId);
            throw new BadRequestException("Invalid payment number");
        }
        return payment;
    }

}
