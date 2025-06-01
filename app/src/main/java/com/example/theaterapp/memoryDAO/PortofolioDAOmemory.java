package com.example.theaterapp.memoryDAO;

import com.example.theaterapp.dao.PortofolioDAO;
import com.example.theaterapp.domain.Portfolio;

public class PortofolioDAOmemory implements PortofolioDAO {

    private static Portfolio wallet;
    @Override
    public void save(Portfolio entity) {
        wallet=entity;
    }

    @Override
    public void delete() {
        wallet=null;
    }

    @Override
    public Portfolio getWallet() {
        return wallet;
    }
}
