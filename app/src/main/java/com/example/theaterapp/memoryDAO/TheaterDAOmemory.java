package com.example.theaterapp.memoryDAO;

import com.example.theaterapp.dao.TheaterDAO;
import com.example.theaterapp.domain.Theater;

public class TheaterDAOmemory implements TheaterDAO {

    private static Theater theater;
    @Override
    public void save(Theater entity) {
        theater=entity;
    }

    @Override
    public void delete() {
        theater=null;
    }

    public Theater getTheater() {
        return theater;
    }
}
