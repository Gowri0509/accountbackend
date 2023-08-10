package myApplication.example.demo;

import myApplication.example.demo.exception.AccountNotFoundException;
import myApplication.example.demo.model.Account;
import myApplication.example.demo.model.LogInDto;
import myApplication.example.demo.model.TopUpDto;
import myApplication.example.demo.repository.AccountRepository;
import myApplication.example.demo.service.AccountService;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    // Create a mock of the AccountRepository
    private AccountRepository accountRepository = mock(AccountRepository.class);

    // Create an instance of the AccountService and inject the mock AccountRepository
    private AccountService accountService = new AccountService(accountRepository);

    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Test
    public void testGetAllAccount_Success() {
        // Create a list of sample accounts to be returned by the AccountRepository
        List<Account> sampleAccountList;
        sampleAccountList = new ArrayList<>();
        sampleAccountList.add(new Account(/* add relevant parameters */));
        sampleAccountList.add(new Account(/* add relevant parameters */));
        sampleAccountList.add(new Account(/* add relevant parameters */));

        // Mock the behavior of the AccountRepository's findAll method
        when(accountRepository.findAll()).thenReturn(sampleAccountList);

        // Call the getAllAccount method
        List<Account> resultAccountList = accountService.getAllAccount();

        // Assert that the returned list of accounts is the same as the mock
        assertEquals(sampleAccountList, resultAccountList);

        // Verify that the findAll method of the AccountRepository is called once

    }

    @Test
    public void testGetAllAccount_EmptyList() {
        // Mock the behavior of the AccountRepository's findAll method to return an empty list
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the getAllAccount method
        List<Account> resultAccountList = accountService.getAllAccount();

        // Assert that the returned list of accounts is empty
        assertTrue(resultAccountList.isEmpty());

        // Verify that the findAll method of the AccountRepository is called once

    }
    @Test
    public void testFindAccountByAccountNumber_AccountFound() {
        // Create a sample account to be returned by the AccountRepository
        String accountNumber = "12345"; // Replace with a valid account number
        Account sampleAccount = new Account(/* add relevant parameters */);

        // Mock the behavior of the AccountRepository's findById method to return the sample account
        when(accountRepository.findById(eq(accountNumber))).thenReturn(Optional.of(sampleAccount));

        // Call the findAccountByAccountNumber method
        Account resultAccount = accountService.findAccountByAccountNumber(accountNumber);

        // Assert that the returned account is the same as the mock
        assertEquals(sampleAccount, resultAccount);

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(accountNumber));
    }

    @Test
    public void testFindAccountByAccountNumber_AccountNotFound() {
        // Create a non-existent account number
        String nonExistentAccountNumber = "non_existent_account"; // Replace with a non-existent account number

        // Mock the behavior of the AccountRepository's findById method to return an empty optional
        when(accountRepository.findById(eq(nonExistentAccountNumber))).thenReturn(Optional.empty());

        // Call the findAccountByAccountNumber method and expect an AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () -> accountService.findAccountByAccountNumber(nonExistentAccountNumber));

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(nonExistentAccountNumber));
    }
    @Test
    public void testCreateAccount_Success() {
        // Create a sample account to be saved
        Account sampleAccount = new Account(/* add relevant parameters */);

        // Mock the behavior of the AccountRepository's existsById method to return false (account doesn't exist)
        when(accountRepository.existsById(eq(sampleAccount.getAccountNumber()))).thenReturn(false);

        // Mock the behavior of the PasswordEncoder's encode method to return the encoded password
        String encodedPassword = "encoded_password";
        when(passwordEncoder.encode(eq(sampleAccount.getPassword()))).thenReturn(encodedPassword);

        // Call the createAccount method
        Account savedAccount = accountService.createAccount(sampleAccount);

        // Assert that the returned account is the same as the mock
        assertEquals(sampleAccount, savedAccount);

        // Verify that the existsById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).existsById(eq(sampleAccount.getAccountNumber()));

        // Verify that the encode method of the PasswordEncoder is called once with the correct argument
        verify(passwordEncoder, times(1)).encode(eq(sampleAccount.getPassword()));

        // Verify that the save method of the AccountRepository is called once
        verify(accountRepository, times(1)).save(eq(sampleAccount));
    }

    @Test
    public void testCreateAccount_AccountAlreadyExists() {
        // Create a sample account to be saved
        Account sampleAccount = new Account(/* add relevant parameters */);

        // Mock the behavior of the AccountRepository's existsById method to return true (account already exists)
        when(accountRepository.existsById(eq(sampleAccount.getAccountNumber()))).thenReturn(true);

        // Call the createAccount method and expect an AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () -> accountService.createAccount(sampleAccount));

        // Verify that the existsById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).existsById(eq(sampleAccount.getAccountNumber()));

        // Verify that the encode method of the PasswordEncoder is never called since the account creation fails
        verify(passwordEncoder, never()).encode(anyString());

        // Verify that the save method of the AccountRepository is never called since the account creation fails
        verify(accountRepository, never()).save(any());
    }
    @Test
    public void testLogInAccount_Success() {
        // Create a sample LogInDto object
        LogInDto sampleLogInDto = new LogInDto(/* add relevant parameters */);

        // Create a sample account to be returned by the AccountRepository
        String accountNumber = "12345"; // Replace with a valid account number
        String hashedPassword = "hashed_password"; // Replace with the hashed password of the account
        Account sampleAccount = new Account(/* add relevant parameters */);
        sampleAccount.setPassword(hashedPassword);

        // Mock the behavior of the AccountRepository's findById method to return the sample account
        when(accountRepository.findById(eq(sampleLogInDto.getAccountNumber()))).thenReturn(Optional.of(sampleAccount));

        // Mock the behavior of the PasswordEncoder's matches method to return true (password matches)
        when(passwordEncoder.matches(eq(sampleLogInDto.getPassword()), eq(hashedPassword))).thenReturn(true);

        // Call the LogInAccount method
        Account loggedInAccount = accountService.LogInAccount(sampleLogInDto);

        // Assert that the returned account is the same as the mock
        assertEquals(sampleAccount, loggedInAccount);

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(sampleLogInDto.getAccountNumber()));

        // Verify that the matches method of the PasswordEncoder is called once with the correct arguments
        verify(passwordEncoder, times(1)).matches(eq(sampleLogInDto.getPassword()), eq(hashedPassword));
    }

    @Test
    public void testLogInAccount_AccountNotFound() {
        // Create a sample LogInDto object
        LogInDto sampleLogInDto = new LogInDto(/* add relevant parameters */);

        // Mock the behavior of the AccountRepository's findById method to return an empty optional
        when(accountRepository.findById(eq(sampleLogInDto.getAccountNumber()))).thenReturn(Optional.empty());

        // Call the LogInAccount method and expect an AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () -> accountService.LogInAccount(sampleLogInDto));

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(sampleLogInDto.getAccountNumber()));

        // Verify that the matches method of the PasswordEncoder is never called since the account is not found
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    public void testLogInAccount_IncorrectPassword() {
        // Create a sample LogInDto object
        LogInDto sampleLogInDto = new LogInDto(/* add relevant parameters */);

        // Create a sample account with a different hashed password than the provided LogInDto
        String accountNumber = "12345"; // Replace with a valid account number
        String hashedPassword = "hashed_password"; // Replace with the hashed password of the account
        Account sampleAccount = new Account(/* add relevant parameters */);
        sampleAccount.setPassword(hashedPassword);

        // Mock the behavior of the AccountRepository's findById method to return the sample account
        when(accountRepository.findById(eq(sampleLogInDto.getAccountNumber()))).thenReturn(Optional.of(sampleAccount));

        // Mock the behavior of the PasswordEncoder's matches method to return false (password doesn't match)
        when(passwordEncoder.matches(eq(sampleLogInDto.getPassword()), eq(hashedPassword))).thenReturn(false);

        // Call the LogInAccount method and expect an AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () -> accountService.LogInAccount(sampleLogInDto));

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(sampleLogInDto.getAccountNumber()));

        // Verify that the matches method of the PasswordEncoder is called once with the correct arguments
        verify(passwordEncoder, times(1)).matches(eq(sampleLogInDto.getPassword()), eq(hashedPassword));
    }
    @Test
    public void testDeleteAccountByAccountNumber_Success() {
        // Create a sample account to be deleted
        String accountNumber = "12345"; // Replace with a valid account number
        Account sampleAccount = new Account(/* add relevant parameters */);

        // Mock the behavior of the AccountRepository's findById method to return the sample account
        when(accountRepository.findById(eq(accountNumber))).thenReturn(Optional.of(sampleAccount));

        // Call the deleteAccountByAccountNumber method
        String resultMessage = accountService.deleteAccountByAccountNumber(accountNumber);

        // Assert that the returned message is correct
        assertEquals("Account deleted with account Number:" + accountNumber, resultMessage);

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(accountNumber));

        // Verify that the deleteById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).deleteById(eq(accountNumber));
    }

    @Test
    public void testDeleteAccountByAccountNumber_AccountNotFound() {
        // Create a non-existent account number
        String nonExistentAccountNumber = "non_existent_account"; // Replace with a non-existent account number

        // Mock the behavior of the AccountRepository's findById method to return an empty optional
        when(accountRepository.findById(eq(nonExistentAccountNumber))).thenReturn(Optional.empty());

        // Call the deleteAccountByAccountNumber method and expect an AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccountByAccountNumber(nonExistentAccountNumber));

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(nonExistentAccountNumber));

        // Verify that the deleteById method of the AccountRepository is never called since the account is not found
        verify(accountRepository, never()).deleteById(any());
    }

    @Test
    public void testTopUp_Success() {
        // Create a sample TopUpDto object
        TopUpDto sampleTopUpDto = new TopUpDto(/* add relevant parameters */);

        // Create a sample account to be topped up
        String accountNumber = "0509"; // Replace with a valid account number
        int initialBalance = 100; // Replace with the initial balance of the account
        Account sampleAccount = new Account(/* add relevant parameters */);
        sampleAccount.setBalance(initialBalance);

        // Mock the behavior of the AccountRepository's findById method to return the sample account
        when(accountRepository.findById(eq(accountNumber))).thenReturn(Optional.of(sampleAccount));

        // Call the topUp method
        String resultMessage = accountService.topUp(sampleTopUpDto);

        // Assert that the returned message is correct
        assertEquals("Top Up Successfull", resultMessage);

        // Assert that the account's balance is updated correctly
        double expectedBalance = initialBalance + sampleTopUpDto.getAmount();
        assertEquals(expectedBalance, sampleAccount.getBalance());

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(accountNumber));

        // Verify that the save method of the AccountRepository is called once with the correct account
        verify(accountRepository, times(1)).save(eq(sampleAccount));
    }

    @Test
    public void testTopUp_AccountNotFound() {
        // Create a sample TopUpDto object
        TopUpDto sampleTopUpDto = new TopUpDto(/* add relevant parameters */);

        // Create a non-existent account number
        String nonExistentAccountNumber = "non_existent_account"; // Replace with a non-existent account number

        // Mock the behavior of the AccountRepository's findById method to return an empty optional
        when(accountRepository.findById(eq(nonExistentAccountNumber))).thenReturn(Optional.empty());

        // Call the topUp method and expect an AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () -> accountService.topUp(sampleTopUpDto));

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(nonExistentAccountNumber));

        // Verify that the save method of the AccountRepository is never called since the account is not found
        verify(accountRepository, never()).save(any());
    }
    @Test
    public void testUpdateAccount_Success() {
        // Create a sample account to be updated
        String accountNumber = "12345"; // Replace with a valid account number
        Account existingAccount = new Account(/* add relevant parameters */);

        // Create a sample updated account with modified values
        Account updatedAccount = new Account(/* add relevant parameters with updated values */);

        // Mock the behavior of the AccountRepository's findById method to return the existing account
        when(accountRepository.findById(eq(existingAccount.getAccountNumber()))).thenReturn(Optional.of(existingAccount));

        // Mock the behavior of the AccountRepository's save method to return the updated account
        when(accountRepository.save(eq(existingAccount))).thenReturn(updatedAccount);

        // Call the updateAccount method
        Account resultAccount = accountService.updateAccount(accountNumber, updatedAccount);

        // Assert that the returned account is the same as the mock
        assertEquals(updatedAccount, resultAccount);

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(existingAccount.getAccountNumber()));

        // Verify that the save method of the AccountRepository is called once with the correct account
        verify(accountRepository, times(1)).save(eq(existingAccount));
    }

    @Test
    public void testUpdateAccount_AccountNotFound() {
        // Create a sample account number and an account with updated values
        String nonExistentAccountNumber = "non_existent_account"; // Replace with a non-existent account number
        Account updatedAccount = new Account(/* add relevant parameters with updated values */);

        // Mock the behavior of the AccountRepository's findById method to return an empty optional
        when(accountRepository.findById(eq(nonExistentAccountNumber))).thenReturn(Optional.empty());

        // Call the updateAccount method and expect an AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () -> accountService.updateAccount(nonExistentAccountNumber, updatedAccount));

        // Verify that the findById method of the AccountRepository is called once with the correct argument
        verify(accountRepository, times(1)).findById(eq(nonExistentAccountNumber));

        // Verify that the save method of the AccountRepository is never called since the account is not found
        verify(accountRepository, never()).save(any());
    }
}
