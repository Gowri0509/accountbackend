package myApplication.example.demo;
import myApplication.example.demo.model.LogInDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInDtoTest {



        @Test
        public void testNoArgsConstructor() {
            LogInDto logInDto = new LogInDto();
            assertEquals(null, logInDto.getAccountNumber());
            assertEquals(null, logInDto.getPassword());
        }

        @Test
        public void testAllArgsConstructor() {
            String accountNumber = "1234567890";
            String password = "secret";

            LogInDto logInDto = new LogInDto(accountNumber, password);
            assertEquals(accountNumber, logInDto.getAccountNumber());
            assertEquals(password, logInDto.getPassword());
        }

        @Test
        public void testAccountNumberGetterSetter() {
            String accountNumber = "1234567890";
            LogInDto logInDto = new LogInDto();
            logInDto.setAccountNumber(accountNumber);

            assertEquals(accountNumber, logInDto.getAccountNumber());
        }

        @Test
        public void testPasswordGetterSetter() {
            String password = "secret";
            LogInDto logInDto = new LogInDto();
            logInDto.setPassword(password);

            assertEquals(password, logInDto.getPassword());
        }
    }


