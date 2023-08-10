package myApplication.example.demo.controller;

import myApplication.example.demo.model.Account;
import myApplication.example.demo.model.LogInDto;
import myApplication.example.demo.model.TopUpDto;
import myApplication.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping("/allaccounts")
    public ResponseEntity<List<Account>> getAllAccounts(){
        return new ResponseEntity<>(accountService.getAllAccount(), HttpStatus.OK);
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account>getAccountDetails(@PathVariable("accountNumber") String accountNumber){
        return new ResponseEntity<>(accountService.findAccountByAccountNumber(accountNumber), HttpStatus.OK);
    }
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccountByAccountNumber(@PathVariable("accountNumber")String accountNumber){
        return new ResponseEntity<>(accountService.deleteAccountByAccountNumber(accountNumber),HttpStatus.ACCEPTED);
    }
    @PostMapping("/")
    public  ResponseEntity<Account>createNewAccount(@RequestBody Account account){
        return new ResponseEntity<>(accountService.createAccount(account),HttpStatus.OK);
    }
    @PutMapping("/{accountNumber}")
    public ResponseEntity<Account>updateAccount(@PathVariable("accountNumber")String accountNumber,@RequestBody Account account){
        return new ResponseEntity<>(accountService.updateAccount(accountNumber,account),HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<Account>logIn(@RequestBody LogInDto logInDto){
        return new ResponseEntity<>(accountService.LogInAccount(logInDto),HttpStatus.OK);

    }
    @PostMapping("/topUp")
    public ResponseEntity<String>topUp(@RequestBody TopUpDto topUpDto){
        return new ResponseEntity<>(accountService.topUp(topUpDto),HttpStatus.OK);

    }
}
