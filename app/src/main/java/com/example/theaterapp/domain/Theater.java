package com.example.theaterapp.domain;

public class Theater
{
    private String name;
    private Performance room1;
    private Performance room2;
    private String location;
    private String phoneNumber;
    private String email;


    public Theater(String name, Performance room1, Performance room2, String location, String phoneNumber, String email)
    {
        this.name = name;
        this.room1 = room1;
        this.room2 = room2;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public Performance getRoom1()
    {
        return room1;
    }

    public Performance getRoom2()
    {
        return room2;
    }  
    
    public String getLocation()
    {
        return location;
    }
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public String getEmail()
    {
        return email;
    }
}
