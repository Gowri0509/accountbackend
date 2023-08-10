package myApplication.example.demo;
import myApplication.example.demo.model.Account;
import myApplication.example.demo.model.Transaction;
import myApplication.example.demo.repository.AccountRepository;
import myApplication.example.demo.repository.TransactionRepository;
import myApplication.example.demo.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import myApplication.example.demo.exception.AccountNotFoundException;
import myApplication.example.demo.exception.TransactionNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private List<Transaction> dummyTransactions;
    private Account dummyAccount;

    @Before
    public void setup() {
        dummyTransactions = new ArrayList<>();
        dummyTransactions.add(new Transaction("0509", "1403", "debited", 10));

        dummyAccount = new Account("0509", "Gowri", 100, "gowri@gmail.com", "767657", "jpnagar", "anu");
        dummyAccount.setTransactionList(dummyTransactions);
    }

    @Test
    public void testGetAllTransaction_Success() {
        // Arrange
        String accountNumber = "0509";
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(dummyAccount));

        // Act
        List<Transaction> result = transactionService.getAllTransaction(accountNumber);

        // Assert
        assertEquals(dummyTransactions, result);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testGetAllTransaction_AccountNotFound() {
        // Arrange
        String accountNumber = "1234";
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.empty());

        // Act
        transactionService.getAllTransaction(accountNumber);
    }

    @Test
    public void testFindTransactionByTransactionId_Success() {
        // Arrange
        String transactionId = "01";
        Transaction dummyTransaction = new Transaction("0509", "1403", "debited", 10);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(dummyTransaction));

        // Act
        Transaction result = transactionService.findTransactionByTransactionId(transactionId);

        // Assert
        assertEquals(dummyTransaction, result);
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testFindTransactionByTransactionId_TransactionNotFound() {
        // Arrange
        String transactionId = "01";
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // Act
        transactionService.findTransactionByTransactionId(transactionId);
    }

    @Test
    public void testDeleteTransaction_Success() {
        // Arrange
        String transactionId = "01";
        when(transactionRepository.existsById(transactionId)).thenReturn(true);

        // Act
        String result = transactionService.deleteTransaction(transactionId);

        // Assert
        assertEquals("transaction deleted with transactionId:" + transactionId, result);
        verify(transactionRepository, times(1)).deleteById(transactionId);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testCreateTransaction_SenderAccountNotFound() {
        // Arrange
        Transaction dummyTransaction = new Transaction("0509", "1403", "debited", 10);
        when(accountRepository.findById(dummyTransaction.getSenderAccountNumber())).thenReturn(Optional.empty());

        // Act
        transactionService.createTransaction(dummyTransaction);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testCreateTransaction_ReceiverAccountNotFound() {
        // Arrange
        Transaction dummyTransaction = new Transaction("0509", "1403", "debited", 10);
        when(accountRepository.findById(dummyTransaction.getSenderAccountNumber())).thenReturn(Optional.of(dummyAccount));
        when(accountRepository.findById(dummyTransaction.getReceiverAccountNumber())).thenReturn(Optional.empty());

        // Act
        transactionService.createTransaction(dummyTransaction);
    }
    @Test
    public void testSetSenderTransaction_Success() {
        // Arrange
        Transaction dummyTransaction = new Transaction("0509", "1403", "debited", 10);
        Account senderAccount = new Account("0509", "Gowri", 100, "gowri@gmail.com", "767657", "jpnagar", "anu");
        senderAccount.setTransactionList(dummyTransactions);

        // Mocking the behavior of accountRepository.findById()
        when(accountRepository.findById(dummyTransaction.getSenderAccountNumber())).thenReturn(Optional.of(senderAccount));

        // Act
        transactionService.setSenderTransaction(dummyTransaction, senderAccount);


        verify(accountRepository, times(1)).save(senderAccount);
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testSetSenderTransaction_InsufficientBalance() {
        // Arrange
        Transaction dummyTransaction = new Transaction("0509", "1403", "debited", 1000);
        Account senderAccount = new Account("0509", "Gowri", 100, "gowri@gmail.com", "767657", "jpnagar", "anu");

        // Mocking the behavior of accountRepository.findById()
        when(accountRepository.findById(dummyTransaction.getSenderAccountNumber())).thenReturn(Optional.of(senderAccount));

        // Act & Assert
        transactionService.setSenderTransaction(dummyTransaction, senderAccount);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testSetSenderTransaction_SenderAccountNotFound() {
        // Arrange
        Transaction dummyTransaction = new Transaction("0509", "1403", "debited", 10);

        // Mocking the behavior of accountRepository.findById()
        when(accountRepository.findById(dummyTransaction.getSenderAccountNumber())).thenReturn(Optional.empty());

        // Act & Assert
        transactionService.setSenderTransaction(dummyTransaction, new Account());
    }

    @Test
    public void testSetReceiverTransaction_Success() {
        // Arrange
        Transaction dummyTransaction = new Transaction("0509", "1403", "debited", 10);
        Account receiverAccount = new Account("0509", "Gowri", 100, "gowri@gmail.com", "767657", "jpnagar", "anu");
        List<Transaction> receiverTransactions = new ArrayList<>();

        // Mocking the behavior of accountRepository.findById()
        when(accountRepository.findById(dummyTransaction.getReceiverAccountNumber())).thenReturn(Optional.of(receiverAccount));

        // Act
        transactionService.setReceiverTransaction(dummyTransaction, receiverAccount, "0509");

        verify(accountRepository, times(1)).save(receiverAccount);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testSetReceiverTransaction_ReceiverAccountNotFound() {
        // Arrange
        Transaction dummyTransaction = new Transaction("0509", "1403", "debited", 10);

        // Mocking the behavior of accountRepository.findById()
        when(accountRepository.findById(dummyTransaction.getReceiverAccountNumber())).thenReturn(Optional.empty());

        // Act & Assert
        transactionService.setReceiverTransaction(dummyTransaction, new Account(), "0509");
    }
}