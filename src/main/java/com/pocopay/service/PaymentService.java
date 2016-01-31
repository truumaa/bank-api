package com.pocopay.service;

import java.math.BigDecimal;
import java.text.MessageFormat;

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
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ForbiddenException(
                    ForbiddenException.ExceptionCode.NEGATIVE_AMOUNT, MessageFormat.format(
                    "Negative transfer attempt Amount:{0} SourceId:{1} DestId:{2} ",
                    payment.getAmount(),
                    payment.getSourceAccountId(),
                    payment.getDestinationAccountId()));
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
            throw new ForbiddenException(
                    ForbiddenException.ExceptionCode.NOT_ENOUGH_FUNDS, MessageFormat.format(
                    "Not enough funds to complete transfer Amount:{0} SourceId:{1} DestId:{2}",
                    payment.getAmount(),
                    payment.getSourceAccountId(),
                    payment.getDestinationAccountId()));
        }
    }


    public Payment getPayment(Integer paymentId) {
        Payment payment = paymentMapper.getPaymentById(paymentId);
        if (payment == null) {
            logger.info("BadRequestException: Invalid payment ID:{} requested", paymentId);
            throw new BadRequestException(
                    BadRequestException.ExceptionCode.INVALID_PAYMENT_ID, MessageFormat.format(
                    "Invalid payment:{0} ", paymentId));
        }
        return payment;
    }

}
