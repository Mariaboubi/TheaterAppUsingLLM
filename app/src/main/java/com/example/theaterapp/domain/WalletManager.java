package com.example.theaterapp.domain;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WalletManager
{
    private static final String fileName = "walletBalance.txt";

    public static void saveBalance(Context context, int balance)
    {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE))
        {
            fos.write(String.valueOf(balance).getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static int loadBalance(Context context)
    {
        try (FileInputStream fis = context.openFileInput(fileName); BufferedReader reader = new BufferedReader(new InputStreamReader(fis)))
        {
            String line = reader.readLine();
            if (line != null && !line.isEmpty())
            {
                return Integer.parseInt(line);
            }
        }
        catch (IOException | NumberFormatException e)
        {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
}
