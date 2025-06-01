package com.example.theaterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.theaterapp.ChatBox.ChatActivity;
import com.example.theaterapp.MyTickets.MyTicketsActivity;
import com.example.theaterapp.Program.TheaterProgramActivity;
import com.example.theaterapp.dao.Initializer;
import com.example.theaterapp.dao.TicketDAO;
import com.example.theaterapp.domain.Ticket;
import com.example.theaterapp.domain.TicketLoader;
import com.example.theaterapp.memoryDAO.MemoryInitializer;
import com.example.theaterapp.memoryDAO.TicketsDAOmemory;

import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final TicketDAO ticketDAO = new TicketsDAOmemory();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Initializer dataHelper = new MemoryInitializer();
        dataHelper.prepareData();

        List<Ticket> tickets = TicketLoader.loadTickets(this);
        ticketDAO.saveAllTickets(tickets);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageButton myTickets = findViewById(R.id.MyTicket);
        myTickets.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, MyTicketsActivity.class);
                startActivity(intent);
            }
        });

        Button infoButton = findViewById(R.id.information);
        infoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });

        Button programButton = findViewById(R.id.program);
        programButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, TheaterProgramActivity.class);
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton walletButton = findViewById(R.id.Wallet);
        walletButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, WalletActivity.class);
                startActivity(intent);
            }
        });

        Button bookButton = findViewById(R.id.bookTicket);
        bookButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}