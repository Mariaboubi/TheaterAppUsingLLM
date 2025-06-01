package com.example.theaterapp.Program;

import com.example.theaterapp.dao.PerformanceDAO;
import com.example.theaterapp.domain.Performance;

import java.util.ArrayList;
import java.util.List;

public class ProgramPresenter {
    private ProgramView view;

    private PerformanceDAO performanceDAO;

    private List<Performance> performances;
    public ProgramPresenter(PerformanceDAO performanceDAO){
        this.performanceDAO=performanceDAO;
        this.performances=new ArrayList<>();
    }

    public ProgramView getView() {
        return view;
    }

    public void setView(ProgramView view) {
        this.view = view;
    }

    public void setPerformances() {
        performances = (ArrayList<Performance>) performanceDAO.getAllPerformances();
    }

    public ArrayList<Performance> getPerformanceList(){
        return (ArrayList<Performance>) performances;
    }

    public void onChangeLayout() {
        if (performances.isEmpty()) {
            view.ShowNoPerformances();
        }
        else {
            view.ShowPerformances();
        }
    }
}