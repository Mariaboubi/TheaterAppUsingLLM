package com.example.theaterapp.memoryDAO;

import com.example.theaterapp.dao.TicketDAO;
import com.example.theaterapp.domain.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketsDAOmemory implements TicketDAO {

    protected static List<Ticket> entities = new ArrayList<>();
    @Override
    public void deleteAll() {
        entities.clear();
    }

    @Override
    public void delete(Ticket ticket) {
        entities.remove(ticket);
    }

    @Override
    public void save(Ticket entity) {
        entities.add(entity);
    }

    @Override
    public Ticket findById(int id) {
        for(Ticket ticket: entities){
            if(id== ticket.getTicketID()){
                return ticket;
            }
        }
        return null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return entities;
    }

    @Override
    public void saveAllTickets(List<Ticket> tickets) {
        entities=tickets;
    }

    @Override
    public int size() {
        return entities.size();
    }
}
