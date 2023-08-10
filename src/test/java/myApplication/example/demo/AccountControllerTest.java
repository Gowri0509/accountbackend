package myApplication.example.demo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import myApplication.example.demo.controller.AccountController;
import myApplication.example.demo.model.Account;
import myApplication.example.demo.model.LogInDto;
import myApplication.example.demo.repository.AccountRepository;
import myApplication.example.demo.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;


    private MockMvc mockMvc;

    // Test cases for account controller
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        // Mock the accountService.getAllAccount() method to return an empty list of accounts
        List<Account> accounts = Collections.emptyList();
        when(accountService.getAllAccount()).thenReturn(accounts);

        // Perform the GET request to "/account/allaccounts" and validate the response
        mockMvc.perform(get("/account/allaccounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    public void testGetAccountDetailsException() throws Exception {
        // Mock the accountService.findAccountByAccountNumber() method to throw an exception
        when(accountService.findAccountByAccountNumber("0509")).thenThrow(new RuntimeException("Account not found"));

        // Perform the GET request to "/account/{accountNumber}" and validate the response
        mockMvc.perform(get("/account/{accountNumber}", "0509"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Account not found"));
    }
    @Test
    public void testDeleteAccountByAccountNumber() throws Exception {
        // Mock the accountService.deleteAccountByAccountNumber() method to return a success message
        String accountNumber = "0509";
        when(accountService.deleteAccountByAccountNumber(accountNumber)).thenReturn("Account deleted successfully");

        // Perform the DELETE request to "/account/{accountNumber}" and validate the response
        mockMvc.perform(delete("/account/{accountNumber}", accountNumber))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Account deleted successfully"));

        // Verify that the accountService.deleteAccountByAccountNumber() method was called with the correct accountNumber
        verify(accountService, times(1)).deleteAccountByAccountNumber(accountNumber);
    }
    @Test
    public void testCreateNewAccount_Success() {
        // Create a sample account object
        Account sampleAccount = new Account(/* add relevant parameters */);

        // Mock the behavior of the AccountService's createAccount method
        when(accountService.createAccount(any(Account.class))).thenReturn(sampleAccount);

        // Call the createNewAccount method
        ResponseEntity<Account> responseEntity = accountController.createNewAccount(sampleAccount);

        // Assert that the response status is HttpStatus.OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert that the returned account object is the same as the mock
        assertEquals(sampleAccount, responseEntity.getBody());

        // Verify that the createAccount method of the AccountService is called once with the correct argument
        verify(accountService, times(1)).createAccount(sampleAccount);
    }
    @Test
    public void testUpdateAccount_Success() {
        // Create a sample account object
        Account sampleAccount = new Account(/* add relevant parameters */);
        String accountNumber = "12345"; // Replace with a valid account number

        // Mock the behavior of the AccountService's updateAccount method
        when(accountService.updateAccount(eq(accountNumber), any(Account.class))).thenReturn(sampleAccount);

        // Call the updateAccount method
        ResponseEntity<Account> responseEntity = accountController.updateAccount(accountNumber, sampleAccount);

        // Assert that the response status is HttpStatus.OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert that the returned account object is the same as the mock
        assertEquals(sampleAccount, responseEntity.getBody());

        // Verify that the updateAccount method of the AccountService is called once with the correct arguments
        verify(accountService, times(1)).updateAccount(eq(accountNumber), eq(sampleAccount));
    }


    @Test
    public void testLogIn_Success() {
        // Create a sample LogInDto object
        LogInDto sampleLogInDto = new LogInDto(/* add relevant parameters */);

        // Create a sample account object to be returned by the AccountService
        Account sampleAccount = new Account(/* add relevant parameters */);

        // Mock the behavior of the AccountService's LogInAccount method
        when(accountService.LogInAccount(eq(sampleLogInDto))).thenReturn(sampleAccount);

        // Call the logIn method
        ResponseEntity<Account> responseEntity = accountController.logIn(sampleLogInDto);

        // Assert that the response status is HttpStatus.OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert that the returned account object is the same as the mock
        assertEquals(sampleAccount, responseEntity.getBody());

        // Verify that the LogInAccount method of the AccountService is called once with the correct argument
        verify(accountService, times(1)).LogInAccount(eq(sampleLogInDto));
    }
    @MockBean
    private SpringApplication springApplication;

    @Test
    public void contextLoads() {
        // Test if the application context loads successfully
    }


}



