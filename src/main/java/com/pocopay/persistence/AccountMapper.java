package com.pocopay.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pocopay.domain.Account;

public interface AccountMapper {

    @Select("SELECT * FROM account WHERE id = #{accountId}")
    Account getAccountById(Long accountId);

    @Select("SELECT * FROM account WHERE name = #{name}")
    Account getAccountByName(String name);

    @Update("UPDATE account SET amount = #{amount} WHERE id = #{id}")
    void updateAccount(Account account);

    @Insert("INSERT INTO account (name, amount) VALUES(#{name}, #{amount})")
    @Options(useGeneratedKeys = true)
    void insertAccount(Account account);
}
