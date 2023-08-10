package myApplication.example.demo.service;
import myApplication.example.demo.model.TopUpDto;
import myApplication.example.demo.model.Transaction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import myApplication.example.demo.exception.AccountNotFoundException;
import myApplication.example.demo.model.Account;
import myApplication.example.demo.model.LogInDto;
import myApplication.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private static AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<Account> getAllAccount() {
        List<Account> accountList = new ArrayList<>();
        accountList = accountRepository.findAll();
        return accountList;
    }

    public AccountService(AccountRepository accountRepository) {
        AccountService.accountRepository = accountRepository;
    }

    public Account findAccountByAccountNumber(String accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Account not found with accountNumber:" + accountNumber);
        }
        return account.get();
    }

    public Account createAccount(Account account) {
        boolean existAccount = accountRepository.existsById(account.getAccountNumber());
        if (existAccount) {
            throw new AccountNotFoundException("Account exist with accountNumber:" + account.getAccountNumber());
        }
        account.setPassword(passwordEncoder().encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account LogInAccount(LogInDto logInDto) {
        Optional<Account> account = accountRepository.findById(logInDto.getAccountNumber());
        if (account.isEmpty())
            throw new AccountNotFoundException("Account Not Found With accountNumber:" + logInDto.getAccountNumber());
        if (!passwordEncoder().matches(logInDto.getPassword(), account.get().getPassword())) {
            throw new AccountNotFoundException("UnAuthorised User");
        }
        return account.get();
    }

    public String deleteAccountByAccountNumber(String accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Account not found with accountNumber:" + accountNumber);
        }
        accountRepository.deleteById(accountNumber);
        return "Account deleted with account Number:" + accountNumber;
    }

    public Account updateAccount(String accountNumber, Account account) {
        Optional<Account> existAccount = accountRepository.findById(account.getAccountNumber());
        if (existAccount.isEmpty()) {
            throw new AccountNotFoundException("Account doesn't exist with accountNumber:" + accountNumber);
        }
        Account getExistingAccount = existAccount.get();
        getExistingAccount.setAccountHolderName(account.getAccountNumber());
        getExistingAccount.setEmail(account.getEmail());
        getExistingAccount.setMobileNumber(account.getMobileNumber());
        getExistingAccount.setAddress(account.getAddress());
        getExistingAccount.setPassword(account.getPassword());
        getExistingAccount.setBalance(account.getBalance());   //remove it
        return accountRepository.save(getExistingAccount);
    }

    public String topUp(TopUpDto topUpDto) {
        Optional<Account> existAccount = accountRepository.findById(topUpDto.getAccountNumber());
        if (existAccount.isEmpty()) {
            throw new AccountNotFoundException("Account doesn't exist with accountNumber:" + topUpDto.getAccountNumber());
        }
        existAccount.get().setBalance(existAccount.get().getBalance() + topUpDto.getAmount());
        Transaction transaction = new Transaction("-","-","TopUp",topUpDto.getAmount(), LocalDateTime.now());
        existAccount.get().getTransactionList().add(transaction);
        accountRepository.save(existAccount.get());
        return "Top Up Successfull";
    }
}