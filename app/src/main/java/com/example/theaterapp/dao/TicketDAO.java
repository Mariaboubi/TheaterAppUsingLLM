package com.example.theaterapp.dao;


import com.example.theaterapp.domain.Ticket;

import java.util.List;

public interface TicketDAO {

    void deleteAll();

    void delete(Ticket ticket);

    void save(Ticket entity);


    Ticket findById(int id);

    List<Ticket> getAllTickets();

    void saveAllTickets(List<Ticket> tickets);

    int size();

}
