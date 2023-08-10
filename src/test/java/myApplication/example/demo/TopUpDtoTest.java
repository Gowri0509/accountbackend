package myApplication.example.demo;

import myApplication.example.demo.model.TopUpDto;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TopUpDtoTest {
    @Test
    public void testNoArgsConstructor() {
        TopUpDto topUpDto = new TopUpDto();
        assertEquals(null, topUpDto.getAccountNumber());
        assertEquals(0, topUpDto.getAmount());
    }

    @Test
    public void testAllArgsConstructor() {
        String accountNumber = "1234567890";
        int amount = 100;

        TopUpDto topUpDto = new TopUpDto(accountNumber, amount);
        assertEquals(accountNumber, topUpDto.getAccountNumber());
        assertEquals(amount, topUpDto.getAmount());
    }

    @Test
    public void testAccountNumberGetterSetter() {
        String accountNumber = "1234567890";
        TopUpDto topUpDto = new TopUpDto();
        topUpDto.setAccountNumber(accountNumber);

        assertEquals(accountNumber, topUpDto.getAccountNumber());
    }

    @Test
    public void testAmountGetterSetter() {
        int amount = 100;
        TopUpDto topUpDto = new TopUpDto();
        topUpDto.setAmount(amount);

        assertEquals(amount, topUpDto.getAmount());
    }
}
