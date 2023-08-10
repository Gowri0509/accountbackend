package myApplication.example.demo;

import myApplication.example.demo.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void testNoArgsConstructor() {
        Transaction transaction = new Transaction();
        assertEquals(null, transaction.getId());
        assertEquals(null, transaction.getSenderAccountNumber());
        assertEquals(null, transaction.getReceiverAccountNumber());
        assertEquals(null, transaction.getTransactionType());
        assertEquals(0, transaction.getAmount());
        assertEquals(null, transaction.getDate());
    }

    @Test
    public void testAllArgsConstructor() {
        String id = "transaction123";
        String senderAccountNumber = "sender123";
        String receiverAccountNumber = "receiver456";
        String transactionType = "credit";
        int amount = 100;
        LocalDateTime date = LocalDateTime.now();

        Transaction transaction = new Transaction(id, senderAccountNumber, receiverAccountNumber,
                transactionType, amount, date);

        assertEquals(id, transaction.getId());
        assertEquals(senderAccountNumber, transaction.getSenderAccountNumber());
        assertEquals(receiverAccountNumber, transaction.getReceiverAccountNumber());
        assertEquals(transactionType, transaction.getTransactionType());
        assertEquals(amount, transaction.getAmount());
        assertEquals(date, transaction.getDate());
    }

    @Test
    public void testIdGetterSetter() {
        String id = "transaction123";
        Transaction transaction = new Transaction();
        transaction.setId(id);

        assertEquals(id, transaction.getId());
    }

    @Test
    public void testSenderAccountNumberGetterSetter() {
        String senderAccountNumber = "sender123";
        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(senderAccountNumber);

        assertEquals(senderAccountNumber, transaction.getSenderAccountNumber());
    }

    @Test
    public void testReceiverAccountNumberGetterSetter() {
        String receiverAccountNumber = "receiver456";
        Transaction transaction = new Transaction();
        transaction.setReceiverAccountNumber(receiverAccountNumber);

        assertEquals(receiverAccountNumber, transaction.getReceiverAccountNumber());
    }

    @Test
    public void testTransactionTypeGetterSetter() {
        String transactionType = "credit";
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);

        assertEquals(transactionType, transaction.getTransactionType());
    }

    @Test
    public void testAmountGetterSetter() {
        int amount = 100;
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        assertEquals(amount, transaction.getAmount());
    }

    @Test
    public void testDateGetterSetter() {
        LocalDateTime date = LocalDateTime.now();
        Transaction transaction = new Transaction();
        transaction.setDate(date);

        assertEquals(date, transaction.getDate());
    }
}
