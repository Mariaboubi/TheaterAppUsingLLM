package com.example.theaterapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theaterapp.ChatBox.ChatActivity;
import com.example.theaterapp.dao.TheaterDAO;
import com.example.theaterapp.domain.Theater;
import com.example.theaterapp.memoryDAO.TheaterDAOmemory;


public class InformationActivity extends AppCompatActivity
{
    private final TheaterDAO theaterDAO = new TheaterDAOmemory();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_theatre_information);

        Theater theater = theaterDAO.getTheater();

        TextView nameTextView = findViewById(R.id.theaterName);
        nameTextView.setText(theater.getName());

        TextView addressTextView = findViewById(R.id.addressTextView);
        addressTextView.setText(theater.getLocation());


        TextView phoneNumberTextView = findViewById(R.id.PhoneTextView);
        phoneNumberTextView.setText(theater.getPhoneNumber());


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
                Intent intent = new Intent(InformationActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });


        phoneNumberTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String phoneNumber = phoneNumberTextView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        TextView emailTextView = findViewById(R.id.EmailTextView);
        emailTextView.setText(theater.getEmail());
        emailTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = emailTextView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + email));
                startActivity(Intent.createChooser(intent, "Send email via"));
            }
        });


        addressTextView.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=Alkminis 8-12, Athina 118 54");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });
    }
}