package myApplication.example.demo.repository;

import myApplication.example.demo.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public  interface TransactionRepository extends MongoRepository<Transaction,String> {
}
