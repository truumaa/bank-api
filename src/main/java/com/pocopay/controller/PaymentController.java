package com.pocopay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pocopay.domain.Payment;
import com.pocopay.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.POST)
    public Integer insertPayment(@RequestBody Payment payment) {
        logger.info("new payment--<><<>--");
        return paymentService.insertPayment(payment);
    }

    @RequestMapping(value = "{paymentId}", method = RequestMethod.GET)
    public Payment getPayment(@PathVariable("paymentId") Integer paymentId) {
        logger.info("new payment--<><<>--");
        return paymentService.getPayment(paymentId);
    }

}
