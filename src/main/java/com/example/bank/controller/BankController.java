package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BankController {

    // Request bodies (simple records)
    public record CreateAccountRequest(String name, double openingBalance) {}
    public record AmountRequest(double amount) {}
    public record TransferRequest(int fromAccount, int toAccount, double amount) {}

    private final BankService bankService;

    // Spring injects BankService automatically (constructor injection)
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody CreateAccountRequest req) {
        return bankService.createAccount(req.name(), req.openingBalance());
    }

    @GetMapping("/accounts")
    public List<Account> all() {
        return bankService.getAllAccounts();
    }

    @GetMapping("/accounts/{number}")
    public Account get(@PathVariable int number) {
        return bankService.getAccount(number);
    }

    @GetMapping("/accounts/{number}/transactions")
    public List<String> history(@PathVariable int number) {
        return bankService.getAccount(number).getTransactions();
    }

    @PostMapping("/accounts/{number}/deposit")
    public Account deposit(@PathVariable int number, @RequestBody AmountRequest req) {
        return bankService.deposit(number, req.amount());
    }

    @PostMapping("/accounts/{number}/withdraw")
    public Account withdraw(@PathVariable int number, @RequestBody AmountRequest req) {
        return bankService.withdraw(number, req.amount());
    }

    @PostMapping("/transfer")
    public Map<String, String> transfer(@RequestBody TransferRequest req) {
        bankService.transfer(req.fromAccount(), req.toAccount(), req.amount());
        return Map.of("message", "Transfer successful");
    }
}
