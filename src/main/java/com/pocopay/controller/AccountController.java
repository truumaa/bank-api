package com.pocopay.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pocopay.domain.Account;
import com.pocopay.domain.Payment;
import com.pocopay.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.POST)
    public Integer createAccount(@RequestBody Account account) {
        return accountService.insertAccount(account);
    }

    @RequestMapping(value = "{accountId}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable("accountId") Integer accountId) {
        logger.info("getAccountById-->--");
        return accountService.getAccount(accountId);
    }

    @RequestMapping(value = "{accountId}/history", method = RequestMethod.GET)
    public List<Payment> getAccountHistory(@PathVariable("accountId") Integer accountId) {
        logger.info("getAccountHistory-->--");
        return accountService.getAccountHistory(accountId);
    }

}
