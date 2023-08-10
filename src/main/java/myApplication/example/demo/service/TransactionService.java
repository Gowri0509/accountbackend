package myApplication.example.demo.service;

import myApplication.example.demo.exception.AccountNotFoundException;
import myApplication.example.demo.exception.InsufficientBalanceException;
import myApplication.example.demo.exception.TransactionNotFoundException;
import myApplication.example.demo.model.Account;
import myApplication.example.demo.model.Transaction;
import myApplication.example.demo.repository.AccountRepository;
import myApplication.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private static TransactionRepository transactionRepository;
    @Autowired
    private static AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,AccountRepository accountRepository){
        TransactionService.transactionRepository = transactionRepository;
        TransactionService.accountRepository = accountRepository;
    }
    public List<Transaction> getAllTransaction(String accountNumber){
        Optional<Account> existingAccount = accountRepository.findById(accountNumber);
        if (existingAccount.isEmpty())
            throw new AccountNotFoundException("Account Not Found");
        return existingAccount.get().getTransactionList();
    }

    public Transaction findTransactionByTransactionId(String transactionId){
        Optional<Transaction> existingTransaction = transactionRepository.findById(transactionId);
        if(existingTransaction.isEmpty()){
            throw new TransactionNotFoundException("Transaction Not Found!");
        }
        return existingTransaction.get();
    }

    public String deleteTransaction(String transactionId){
        boolean transactionExisting = transactionRepository.existsById(transactionId);
        if(!transactionExisting){
            throw new TransactionNotFoundException("Transaction Not Found");
        }
        transactionRepository.deleteById(transactionId);
        return "transaction deleted with transactionId:"+transactionId;
    }
    public String createTransaction(Transaction transaction) {
        Optional<Account> senderAccount = accountRepository.findById(transaction.getSenderAccountNumber());
        Optional<Account> receiverAccount = accountRepository.findById(transaction.getReceiverAccountNumber());
        if (senderAccount.isEmpty()) {
            throw new TransactionNotFoundException("Sender Account Not Found");
        }
        if (receiverAccount.isEmpty()) {
            throw new TransactionNotFoundException("Receiver Account Not Found!");
        }
        setSenderTransaction(transaction, senderAccount.get());
        setReceiverTransaction(transaction, receiverAccount.get(), senderAccount.get().getAccountNumber());
        return "Transaction Completed Successfully";
    }
    public void setSenderTransaction(Transaction transaction, Account sender){
             //sender side transactionList
        Transaction senderTransaction=new Transaction(transaction.getSenderAccountNumber(),transaction.getReceiverAccountNumber(),"Debited",transaction.getAmount(),LocalDateTime.now());
        transaction.setTransactionType("Debited");
        transaction.setSenderAccountNumber("SELF");
        transaction.setDate(LocalDateTime.now());

        List<Transaction> transactionList = sender.getTransactionList();
        transactionList.add(transaction);
        sender.setTransactionList(transactionList);
        if(sender.getBalance()<transaction.getAmount()){

            throw new InsufficientBalanceException("Insufficient Balance Please Do Top Up");
        }
        sender.setBalance(sender.getBalance()-transaction.getAmount());
        accountRepository.save(sender);
        transactionRepository.save(senderTransaction);
    }

    public void setReceiverTransaction(Transaction transaction, Account receiver,String senderAccountNumber){
        //receiver side transactionList
        Transaction receiverTransaction=new Transaction(transaction.getSenderAccountNumber(),transaction.getReceiverAccountNumber(),"Credited",transaction.getAmount(),LocalDateTime.now());
        transaction.setTransactionType("Credited");
        transaction.setSenderAccountNumber(senderAccountNumber);
        transaction.setReceiverAccountNumber("SELF");
        transaction.setDate(LocalDateTime.now());
        List<Transaction>  transactionList = receiver.getTransactionList();
        transactionList.add(transaction);
        receiver.setTransactionList(transactionList);
        receiver.setBalance(receiver.getBalance()+transaction.getAmount());
        accountRepository.save(receiver);
        transactionRepository.save(receiverTransaction);
    }
}
