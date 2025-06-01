package com.example.theaterapp.MyTickets;

import androidx.lifecycle.ViewModel;

import com.example.theaterapp.memoryDAO.TicketsDAOmemory;

public class MyTicketsViewModel extends ViewModel {

    private final MyTicketsPresenter presenter;

    public MyTicketsViewModel()
    {
        presenter= new MyTicketsPresenter( new TicketsDAOmemory());
    }

    public MyTicketsPresenter getPresenter()
    {
        return presenter;
    }
}
