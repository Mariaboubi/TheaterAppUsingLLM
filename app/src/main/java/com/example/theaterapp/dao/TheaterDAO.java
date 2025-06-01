package com.example.theaterapp.dao;

import com.example.theaterapp.domain.Theater;

public interface TheaterDAO {

    void save(Theater entity);

    void delete();

     Theater getTheater();
}
