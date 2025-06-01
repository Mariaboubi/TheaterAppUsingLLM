package com.example.theaterapp.Program;

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
import com.example.theaterapp.dao.PerformanceDAO;
import com.example.theaterapp.memoryDAO.PerformanceDAOmemory;

public class TheaterProgramActivity extends AppCompatActivity implements ProgramView,PerformanceRecyclerViewAdapter.PerformanceListener{
    private final PerformanceDAO performanceDAO = new PerformanceDAOmemory();

    private ProgramViewModel viewModel;

    private RecyclerView recyclerView;

    private  TextView emptyView;

    @Override
    public void ShowNoPerformances() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void ShowPerformances() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PerformanceRecyclerViewAdapter(viewModel.getPresenter().getPerformanceList(), this));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_theater_program);

        viewModel = new ViewModelProvider(this).get(ProgramViewModel.class);
        viewModel.getPresenter().setView(this);

        viewModel.getPresenter().setPerformances();
        recyclerView = findViewById(R.id.PerformancesRecyclerView);
        emptyView = findViewById(R.id.NoPerformance);
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
                Intent intent = new Intent(TheaterProgramActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });


    }
}