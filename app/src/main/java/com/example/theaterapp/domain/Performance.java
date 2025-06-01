package com.example.theaterapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;

public class Performance
{
    private String name;
    @JsonProperty("roomID") private int roomID;
    private static int id = 0;
    private int capacity;
    private int price;
    private HashSet<String> performanceDays = new HashSet<String>();
    private String performanceType;
    private String performanceTime;
    private String performancePlot;

    @JsonCreator
    public Performance(@JsonProperty("name") String name, @JsonProperty("roomID") int roomID, @JsonProperty("price") int price, @JsonProperty("performanceDays") HashSet<String> performanceDays, @JsonProperty("performanceType") String performanceType, @JsonProperty("performanceTime") String performanceTime, @JsonProperty("performancePlot") String performancePlot)
    {
        this.name = name;
        this.roomID = roomID;
        this.price = price;
        this.performanceDays = performanceDays;
        this.performanceType = performanceType;
        this.performanceTime = performanceTime;
        this.performancePlot = performancePlot;
    }

    public Performance(String name, int capacity, HashSet<String> performanceDays, String performanceType, String performanceTime, String performancePlot, int price)
    {
        this.name = name;
        this.roomID = id;
        ++id;
        this.price = price;
        this.capacity = capacity;
        this.performanceDays = performanceDays;
        this.performanceType = performanceType;
        this.performanceTime = performanceTime;
        this.performancePlot = performancePlot;
    }

    public String getName()
    {
        return name;
    }

    @JsonProperty("roomID") public int getRoomID()
    {
        return roomID;
    }

    public int getPrice()
    {
        return price;
    }

    public HashSet<String> getPerformanceDays()
    {
        return performanceDays;
    }

    public String getPerformanceType()
    {
        return performanceType;
    }

    public String getPerformanceTime()
    {
        return performanceTime;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @JsonProperty("roomID") public void setRoomID(int theaterRoomID)
    {
        this.roomID = theaterRoomID;
    }

    public static void setId(int id)
    {
        Performance.id = id;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getPerformancePlot()
    {
        return performancePlot;
    }
}