package myApplication.example.demo;


import myApplication.example.demo.controller.TransactionController;
import myApplication.example.demo.model.Transaction;
import myApplication.example.demo.service.TransactionService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testDeleteTransactionByTransactionId_Success() {
        String mockTransactionId = "0509";
        when(transactionService.deleteTransaction(mockTransactionId));

        ResponseEntity<String> response = transactionController.deleteTransactionByTransactionId(mockTransactionId);

       // verify(transactionService, times(1)).deleteTransaction(mockTransactionId);
        //assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
      //  assertThat(response.getBody()).isEqualTo("Transaction deleted successfully.");
    }

    @Test
    public void testDeleteTransactionByTransactionId_NotFound() {
        String mockTransactionId = "nonExistentTransactionId";
        when(transactionService.deleteTransaction(mockTransactionId));

        ResponseEntity<String> response = transactionController.deleteTransactionByTransactionId(mockTransactionId);

    }

    @Test
    public void testDeleteTransactionByTransactionId_InternalServerError() {
        String mockTransactionId = "mockTransactionId";
        when(transactionService.deleteTransaction(mockTransactionId)).thenThrow(new RuntimeException("Some error occurred."));

        ResponseEntity<String> response = transactionController.deleteTransactionByTransactionId(mockTransactionId);

        verify(transactionService, times(1)).deleteTransaction(mockTransactionId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("Error deleting transaction.");
    }
    @Test
    public void testCreateTransaction() {
        // Arrange
        Transaction mockTransaction = new Transaction();
        when(transactionService.createTransaction(any(Transaction.class)))
                .thenReturn("Transaction created successfully");

        // Act
        ResponseEntity<String> response = transactionController.createTransaction(mockTransaction);

    }}