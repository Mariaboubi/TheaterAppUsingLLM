package com.example.theaterapp.MyTickets;

import com.example.theaterapp.dao.TicketDAO;
import com.example.theaterapp.domain.Ticket;

import java.util.ArrayList;
import java.util.List;

public class MyTicketsPresenter {

    private MyTicketsView view;
    private TicketDAO ticketDAO;

    private List<Ticket> tickets;

    public MyTicketsPresenter(TicketDAO ticketDAO){
        this.ticketDAO=ticketDAO;
        this.tickets = new ArrayList<>();
    }

    public void setView(MyTicketsView view) {
        this.view = view;
    }

    public MyTicketsView getView() {
        return view;
    }


    public void setTickets() {
        tickets = (ArrayList<Ticket>) ticketDAO.getAllTickets();
    }

    public void onChangeLayout() {
        if (tickets.isEmpty()) {
            view.ShowNoTickets();
        }
        else {
            view.ShowTickets();
        }
    }
    public ArrayList<Ticket> getTicketList() {
        return (ArrayList<Ticket>)tickets;
    }
}
