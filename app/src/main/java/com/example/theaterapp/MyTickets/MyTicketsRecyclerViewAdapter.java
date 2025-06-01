package com.example.theaterapp.MyTickets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theaterapp.R;
import com.example.theaterapp.dao.PerformanceDAO;
import com.example.theaterapp.domain.Performance;
import com.example.theaterapp.domain.Ticket;
import com.example.theaterapp.memoryDAO.PerformanceDAOmemory;

import java.util.List;

public class MyTicketsRecyclerViewAdapter extends RecyclerView.Adapter<MyTicketsRecyclerViewAdapter.ViewHolder>{

    private final List<Ticket> tickets;
    private final TicketSelectionListener listener;

    private final PerformanceDAO performanceDAO = new PerformanceDAOmemory();

    public MyTicketsRecyclerViewAdapter(List<Ticket> tickets, TicketSelectionListener listener) {
        this.tickets = tickets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_my_tickets_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("Position"+ position);
        Ticket currentTickets = tickets.get(position);
        String ticket_id = "Ticket ID: "+ currentTickets.getTicketID();
        holder.ticketID.setText(ticket_id);
        if (currentTickets.getSeatCode()==null){
            currentTickets.setSeatCode(generateSeat());
        }

        Performance performance = performanceDAO.findByName(currentTickets.getPerName());
        System.out.println(currentTickets.getPerName());
        String ticket_info = currentTickets.InfoToString() +"\n"+" Room " +performance.getRoomID();
        holder.ticketInfo.setText(ticket_info);

        holder.cancel.setOnClickListener(view -> {

            listener.cancelTickets(currentTickets);
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    private String generateSeat()
    {
        char row = (char) ('A' + (int)(Math.random() * 6));
        int pos = 1 + (int)(Math.random() * 10);
        return "Row" + row + " Pos" + pos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView ticketID;
        public final TextView ticketInfo;

        public final ImageButton cancel;
        public ViewHolder(View v) {
            super(v);
            this.ticketID = (TextView) v.findViewById(R.id.ticketID);
            this.ticketInfo = (TextView) v.findViewById(R.id.ticketInfo);
            this.cancel= (ImageButton) v.findViewById(R.id.CancelButton);
        }
    }

    public interface TicketSelectionListener {
        void cancelTickets(Ticket ticket);
    }
}
