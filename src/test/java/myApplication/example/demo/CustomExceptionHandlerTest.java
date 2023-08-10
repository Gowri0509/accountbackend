package myApplication.example.demo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import myApplication.example.demo.exception.AccountNotFoundException;
import myApplication.example.demo.exception.CustomExceptionHandler;
import myApplication.example.demo.exception.ErrorDetails;
import myApplication.example.demo.exception.InsufficientBalanceException;
import myApplication.example.demo.exception.TransactionNotFoundException;

@WebMvcTest(CustomExceptionHandler.class)
public class CustomExceptionHandlerTest {



        @InjectMocks
        private CustomExceptionHandler customExceptionHandler;

        @Mock
        private AccountNotFoundException accountNotFoundException;

        @Mock
        private TransactionNotFoundException transactionNotFoundException;

        @Mock
        private InsufficientBalanceException insufficientBalanceException;

        @Autowired
        private MockMvc mockMvc;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testHandleAccountNotFoundException() throws Exception {
            when(accountNotFoundException.getMessage()).thenReturn("Account not found");
            mockMvc.perform(get("/not-found-account")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void testHandleTransactionNotFoundException() throws Exception {
            when(transactionNotFoundException.getMessage()).thenReturn("Transaction not found");
            mockMvc.perform(get("/not-found-transaction")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void testHandleInsufficientBalanceException() throws Exception {
            when(insufficientBalanceException.getMessage()).thenReturn("Insufficient balance");
            mockMvc.perform(get("/insufficient-balance")
                            .contentType(MediaType.APPLICATION_JSON));
                   // .andExpect(status().isForbidden());
        }
    }


