package com.example.theaterapp.Program;

import androidx.lifecycle.ViewModel;

import com.example.theaterapp.memoryDAO.PerformanceDAOmemory;

public class ProgramViewModel extends ViewModel {
    private final ProgramPresenter presenter;

    public ProgramViewModel()
    {
        presenter= new ProgramPresenter( new PerformanceDAOmemory());
    }

    public ProgramPresenter getPresenter()
    {
        return presenter;
    }
}
