package myApplication.example.demo.repository;

import myApplication.example.demo.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,String>{


    }
