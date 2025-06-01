package com.example.theaterapp.MyTickets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theaterapp.ChatBox.ChatActivity;
import com.example.theaterapp.R;
import com.example.theaterapp.dao.PortofolioDAO;
import com.example.theaterapp.dao.TicketDAO;
import com.example.theaterapp.domain.Portfolio;
import com.example.theaterapp.domain.Ticket;
import com.example.theaterapp.domain.WalletManager;
import com.example.theaterapp.memoryDAO.PortofolioDAOmemory;
import com.example.theaterapp.memoryDAO.TicketsDAOmemory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyTicketsActivity extends AppCompatActivity implements MyTicketsView, MyTicketsRecyclerViewAdapter.TicketSelectionListener
{
    private MyTicketsViewModel viewModel;
    private RecyclerView recyclerView;

    private TextView emptyView;

    private final TicketDAO ticketDAO = new TicketsDAOmemory();

    private final PortofolioDAO portofolioDAO = new PortofolioDAOmemory();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_tickets);

        viewModel = new ViewModelProvider(this).get(MyTicketsViewModel.class);
        viewModel.getPresenter().setView(this);

        viewModel.getPresenter().setTickets();
        recyclerView = findViewById(R.id.ShowTicketsRecyclerView);
        emptyView = findViewById(R.id.NoTickets);
        viewModel.getPresenter().onChangeLayout();

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        ImageButton chatbotButton = findViewById(R.id.chatbotButton);
        chatbotButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MyTicketsActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void ShowNoTickets() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void ShowTickets() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyTicketsRecyclerViewAdapter(viewModel.getPresenter().getTicketList(), this));
    }

    @Override
    public void cancelTickets(Ticket ticket)
    {
        System.out.println("Cancel TicketId: "+ticket.getTicketID());
        Portfolio wallet = portofolioDAO.getWallet();
        wallet.deposit(20);
        ticketDAO.delete(ticket);
        saveTicketsToJson(ticketDAO.getAllTickets());
        WalletManager.saveBalance(getApplicationContext(), wallet.getBalance());
        recreate();

    }

    private void saveTicketsToJson(List<Ticket> tickets) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File file = new File(getFilesDir(), "savedTickets.json");

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, tickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}