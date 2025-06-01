package com.example.theaterapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Ticket
{
//    private Performance  performance;
    private int ticketID;
    private static int staticTicketId = 0;
    private String showtime;
    private LocalDate date;

    private String perName;

    private String seatCode;


    public Ticket() {}

    @JsonCreator
    public Ticket(@JsonProperty("performance") String perName, @JsonProperty("date") LocalDate date, @JsonProperty("showtime") String showtime, @JsonProperty("seat") String seat )
    {
        this.date = date;
        this.ticketID = staticTicketId;
        staticTicketId++;
        this.perName=perName;
        this.showtime = showtime;
        this.seatCode = seat;
    }
    public String getSeatCode(){return seatCode;}
    public int getTicketID()
    {
        return ticketID;
    }


    public String getPerName() {
        return perName;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public String getShowtime()
    {
        return showtime;
    }


    public String InfoToString(){
        return perName + "\n"  + seatCode + "\n" + date +", " + showtime;
    }

    public void setSeatCode(String s) {
        this.seatCode=s;
    }
}
