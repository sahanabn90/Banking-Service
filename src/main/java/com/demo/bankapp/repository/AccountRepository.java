package com.demo.bankapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.bankapp.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query(value = "select * from account where account_number=:accountNumber", nativeQuery = true)
	Account getAccountByAccountNumber(@Param("accountNumber") String accountNumber);

	Account findByAccountNumberEquals(String accountNumber);
}
