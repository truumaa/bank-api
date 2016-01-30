package com.pocopay.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.pocopay.domain.Payment;

public interface PaymentMapper {

    @Select("SELECT * FROM payment WHERE id = #{paymentId}")
    Payment getPaymentById(Integer paymentId);

    @Insert("insert into payment (sourceAccountId, destinationAccountId, amount, description, transactionDate) values" + "" +
            "(#{sourceAccountId}, #{destinationAccountId}, #{amount}, #{description}, now())")
    @Options(useGeneratedKeys = true)
    void insertPayment(Payment payment);

    @Select("SELECT * FROM payment WHERE sourceAccountId = #{accountId}")
    List<Payment> getPaymentsBySourceAccountId(Long accountId);

}
