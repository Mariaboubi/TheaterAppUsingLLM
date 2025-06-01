package com.example.theaterapp.memoryDAO;

import com.example.theaterapp.dao.PerformanceDAO;
import com.example.theaterapp.domain.Performance;

import java.util.ArrayList;
import java.util.List;

public class PerformanceDAOmemory implements PerformanceDAO {

    private static List<Performance> entities= new ArrayList<>();
    @Override
    public void deleteAll() {
        entities.clear();
    }

    @Override
    public void save(Performance entity) {
        entities.add(entity);
    }

    @Override
    public List<Performance> getAllPerformances() {
        return entities;
    }

    @Override
    public Performance findByIndex(int index) {
        return entities.get(index);
    }

    @Override
    public Performance findByName(String name) {
        for(Performance performance: entities){
            if(name.equals(performance.getName())){
                return performance;
            }
        }
        return null;
    }


}
