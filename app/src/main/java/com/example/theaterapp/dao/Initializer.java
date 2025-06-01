package com.example.theaterapp.dao;

import com.example.theaterapp.domain.Performance;
import com.example.theaterapp.domain.Portfolio;
import com.example.theaterapp.domain.Theater;

import java.time.LocalDate;
import java.util.HashSet;

public abstract class Initializer {

    public void eraseAll() {
        TheaterDAO theaterDAO = getTheaterDAO();
        theaterDAO.delete();

        PerformanceDAO performanceDAO = getPerformanceDAO();
        performanceDAO.deleteAll();

        PortofolioDAO portofolioDAO = getPortofolioDAO();
        portofolioDAO.delete();

        TicketDAO ticketDAO = getTicketDAO();
        ticketDAO.deleteAll();
    }


    public void prepareData() {
        eraseAll();

        LocalDate date = LocalDate.now();
        HashSet<String> performancesDaysPer1 = new HashSet<String>();
        performancesDaysPer1.add("Sunday");
        performancesDaysPer1.add("Tuesday");

        HashSet<String> performancesDaysPer2 = new HashSet<String>();
        performancesDaysPer2.add("Thursday");
        performancesDaysPer2.add("Saturday");

        Performance p1 = new Performance("Witness of the Prosecution", 100, performancesDaysPer1, "Drama", "120 min",
                "Leonard Vole is accused of murder, but his lawyer questions his guilt. As the trial unfolds, revelations and twists build up to an unpredictable finale in Agatha Christie's classic thriller.", 20);
        Performance p2 = new Performance("12 Angry Men", 100, performancesDaysPer2, "Drama", "105 min",
                "In 1957 New York, a 16-year-old foreign boy is accused of killing his father. Twelve jurors must reach a unanimous verdict to determine his guilt — a guilty verdict means a mandatory death sentence.", 20);

        Theater theater = new Theater("Alkminis Theater", p2, p1, "Alkminis 8-12, Athina 118 54", "210 0000 000", "email@gmail.com");


        Portfolio portfolio = new Portfolio("GROOOOO");

        PerformanceDAO performanceDAO = getPerformanceDAO();
        performanceDAO.save(p1);
        performanceDAO.save(p2);

        TheaterDAO theaterDAO = getTheaterDAO();
        theaterDAO.save(theater);

        PortofolioDAO portofolioDAO =getPortofolioDAO();
        portofolioDAO.save(portfolio);

    }

    public abstract PerformanceDAO getPerformanceDAO();

    public abstract TheaterDAO getTheaterDAO();
    public abstract PortofolioDAO getPortofolioDAO() ;

    public abstract TicketDAO getTicketDAO() ;
}
