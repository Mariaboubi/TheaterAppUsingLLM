package com.example.theaterapp.dao;
import com.example.theaterapp.domain.Performance;

import java.util.List;

public interface PerformanceDAO {

    void deleteAll();

    void save(Performance entity);

    List<Performance> getAllPerformances();

    Performance findByIndex(int index);

    Performance findByName(String name);
}
