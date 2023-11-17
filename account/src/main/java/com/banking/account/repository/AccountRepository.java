package com.banking.account.repository;

import com.banking.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Optional<Account> findByAccountNumber(Long AccountNumber);
//    public void deleteByAccountNumber(Long accountNumber);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM account WHERE account_number = ?1", nativeQuery = true)
    public void deleteByAccountNumber(Long accountNumber);
}
