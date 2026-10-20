package com.example.bank.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final int accountNumber;
    private final String holderName;
    private double balance;
    private final List<String> transactions = new ArrayList<>();

    public Account(int accountNumber, String holderName, double openingBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = openingBalance;
        transactions.add("Account opened with Rs. " + openingBalance);
    }

    public int getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public List<String> getTransactions() { return transactions; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0.");
        balance += amount;
        transactions.add("Deposited Rs. " + amount + " | Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient balance.");
        balance -= amount;
        transactions.add("Withdrew Rs. " + amount + " | Balance: " + balance);
    }

    public void addNote(String note) {
        transactions.add(note + " | Balance: " + balance);
    }
}
