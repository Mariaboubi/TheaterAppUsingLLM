package com.example.theaterapp.domain;

import com.example.theaterapp.domain.Portfolio;

import java.util.HashSet;

public class Person {
    private String name;
    private Portfolio portfolio;
    private HashSet<Ticket> tickets;

    public Person() {}

    public Person(String name, Portfolio portfolio) {
        this.name = name;
        this.portfolio = portfolio;
        this.tickets = new HashSet<Ticket>();
    }

    public String getName() {
        return name;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public HashSet<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }


    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    

}
