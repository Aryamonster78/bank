package com.example.bank.service;

import com.example.bank.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class BankService {

    // Temporary in-memory "database". Cleared when the app stops.
    private final Map<Integer, Account> accounts = new HashMap<>();
    private int nextAccountNumber = 1001;

    public synchronized Account createAccount(String name, double openingBalance) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be empty.");
        if (openingBalance < 0)
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        Account account = new Account(nextAccountNumber++, name.trim(), openingBalance);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public synchronized Account getAccount(int number) {
        Account account = accounts.get(number);
        if (account == null)
            throw new NoSuchElementException("Account " + number + " not found.");
        return account;
    }

    public synchronized List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public synchronized Account deposit(int number, double amount) {
        Account account = getAccount(number);
        account.deposit(amount);
        return account;
    }

    public synchronized Account withdraw(int number, double amount) {
        Account account = getAccount(number);
        account.withdraw(amount);
        return account;
    }

    public synchronized void transfer(int fromNumber, int toNumber, double amount) {
        if (fromNumber == toNumber)
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        Account from = getAccount(fromNumber);
        Account to = getAccount(toNumber);
        from.withdraw(amount);
        to.deposit(amount);
        from.addNote("Transferred Rs. " + amount + " to #" + toNumber);
        to.addNote("Received Rs. " + amount + " from #" + fromNumber);
    }
}
