package myApplication.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction")
public class Transaction {
    @Id
    private String id;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private String transactionType;
    private int amount;
    private LocalDateTime date;

    public Transaction(String senderAccountNumber,String receiverAccountNumber, String transactionType, int amount, LocalDateTime date) {
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

   public Transaction(String number, String number1, String debited, int i) {}
}
