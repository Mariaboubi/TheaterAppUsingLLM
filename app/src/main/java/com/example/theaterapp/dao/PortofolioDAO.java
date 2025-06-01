package com.example.theaterapp.dao;

import com.example.theaterapp.domain.Portfolio;

public interface PortofolioDAO{
    void save(Portfolio entity);

    void delete();


    Portfolio getWallet();
}
