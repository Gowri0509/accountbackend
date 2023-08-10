package myApplication.example.demo;

import myApplication.example.demo.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class InsufficientBalanceExceptionTest {
    @Test
    public void testInsufficientBalanceException() {
        String errorMessage = "Insufficient balance";
        InsufficientBalanceException exception = new InsufficientBalanceException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }
}
