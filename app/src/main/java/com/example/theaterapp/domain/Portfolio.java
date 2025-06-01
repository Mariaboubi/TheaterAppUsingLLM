package com.example.theaterapp.domain;

import java.io.Serializable;

public class Portfolio implements Serializable
{
    private int balance;
    private String IBAN;

    public Portfolio(String IBAN)
    {
        this.IBAN = IBAN;
        this.balance=0;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public int getBalance()
    {
        return this.balance;
    }

    public String getIBAN()
    {
        return IBAN;
    }

    public void deposit(int amount)
    {
        balance += Math.abs(amount);
    }

    public void withdraw(int amount)
    {
        balance -= Math.abs(amount);

        if (balance < 0)
        {
            balance = 0;
        }
    }

    public void withdrawAllBalance()
    {
        balance = 0;
    }
}
