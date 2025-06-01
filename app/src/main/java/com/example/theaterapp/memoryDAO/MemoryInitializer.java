package com.example.theaterapp.memoryDAO;

import com.example.theaterapp.dao.Initializer;
import com.example.theaterapp.dao.PerformanceDAO;
import com.example.theaterapp.dao.PortofolioDAO;
import com.example.theaterapp.dao.TheaterDAO;
import com.example.theaterapp.dao.TicketDAO;

public class MemoryInitializer  extends Initializer {
    public TheaterDAO getTheaterDAO() {return new TheaterDAOmemory();}

    @Override
    public PortofolioDAO getPortofolioDAO() {
        return new PortofolioDAOmemory();
    }

    @Override
    public PerformanceDAO getPerformanceDAO() {
        return new PerformanceDAOmemory();
    }

    public TicketDAO getTicketDAO() {
        return new TicketsDAOmemory();
    }
}
