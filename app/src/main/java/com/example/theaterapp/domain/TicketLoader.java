package com.example.theaterapp.domain;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TicketLoader {
    public static List<Ticket> loadTickets(Context context)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File file = new File(context.getFilesDir(), "savedTickets.json");

        try {
            if (file.exists()) {
                // load from internal storage if it exists
                return mapper.readValue(file, new TypeReference<List<Ticket>>() {});
            } else {
                // first time: load from assets and copy to internal storage
                InputStream is = context.getAssets().open("databaseTickets.json");
                List<Ticket> tickets = mapper.readValue(is, new TypeReference<List<Ticket>>() {});

                // Save to internal for future use
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, tickets);

                return tickets;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveTickets(Context context, List<Ticket> tickets) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File file = new File(context.getFilesDir(), "savedTickets.json");

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, tickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
