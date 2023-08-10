package myApplication.example.demo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import myApplication.example.demo.exception.ErrorDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ErrorDetailsTest {



        @Test
        public void testGetTimestamp() {
            LocalDateTime expectedTimestamp = LocalDateTime.now();
            ErrorDetails errorDetails = new ErrorDetails(expectedTimestamp, "Test Message");

            LocalDateTime actualTimestamp = errorDetails.getTimestamp();

            assertEquals(expectedTimestamp, actualTimestamp);
        }

        @Test
        public void testGetMessage() {
            String expectedMessage = "Test Message";
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), expectedMessage);

            String actualMessage = errorDetails.getMessage();

            assertEquals(expectedMessage, actualMessage);
        }
    }


