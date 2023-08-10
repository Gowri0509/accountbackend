package myApplication.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account")
public class Account {
    @Id
    private String accountNumber;
    private String accountHolderName;
    private int balance;
    private String email;
    private String mobileNumber;
    private String address;
    private String password;
    private List<Transaction> transactionList = new ArrayList<>();

    public Account(String number, String gowri, int i, String mail, String number1, String jpnagar, String anu) {
    }

}



