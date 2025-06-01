package com.example.theaterapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theaterapp.dao.PortofolioDAO;
import com.example.theaterapp.domain.Portfolio;
import com.example.theaterapp.domain.WalletManager;
import com.example.theaterapp.memoryDAO.PortofolioDAOmemory;
import com.google.android.material.textfield.TextInputEditText;

public class WalletActivity extends AppCompatActivity {

    private Portfolio portfolio;

    private final PortofolioDAO portofolioDAO = new PortofolioDAOmemory();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wallet);

        portfolio = portofolioDAO.getWallet();
        portfolio.setBalance(WalletManager.loadBalance(this));
        portofolioDAO.save(portfolio);

        TextView balance = findViewById(R.id.Balance);
        balance.setText("Balance: "+ portfolio.getBalance() +"€");

        TextView iban = findViewById(R.id.Iban);
        iban.setText("IBAN: "+portfolio.getIBAN());

        Button depositbtn = findViewById(R.id.ButtonDeposit);
        depositbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int amount = extractDeposit();
                if (amount != -1)
                {
                    portfolio.deposit(amount);
                    TextInputEditText dipositEditText = findViewById(R.id.DepositText);
                    WalletManager.saveBalance(getApplicationContext(), portfolio.getBalance());
                    Toast.makeText(WalletActivity.this, "You successfully deposited " + amount + " € in your wallet!" , Toast.LENGTH_LONG).show();
                    dipositEditText.setText("");
                    recreate();
                }
            }
        });

        Button withdraw = findViewById(R.id.ButtonWithdraw);
        withdraw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int amount = extractWithdraw();
                if (amount != -1)
                {
                    portfolio.withdraw(amount);
                    TextInputEditText withdrawEditText = findViewById(R.id.withdrawText);
                    WalletManager.saveBalance(getApplicationContext(), portfolio.getBalance());
                    Toast.makeText(WalletActivity.this, "You successfully withdrawed " + amount + " € from your wallet!" , Toast.LENGTH_LONG).show();
                    withdrawEditText.setText("");
                    recreate();
                }
            }
        });

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private int extractDeposit()
    {
        if ((((EditText) findViewById(R.id.DepositText)).getText().toString().trim()).isEmpty())
        {
            return -1;
        }

        return Integer.parseInt(((EditText) findViewById(R.id.DepositText)).getText().toString().trim());
    }

    private int extractWithdraw()
    {
        if ((((EditText) findViewById(R.id.withdrawText)).getText().toString().trim()).isEmpty())
        {
            return -1;
        }

        return Integer.parseInt(((EditText) findViewById(R.id.withdrawText)).getText().toString().trim());
    }
}