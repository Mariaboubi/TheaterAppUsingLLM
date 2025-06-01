package com.example.theaterapp.ChatBox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theaterapp.R;
import com.example.theaterapp.domain.ChatMessage;

import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder>{
    private List<ChatMessage> messageList;

    public ChatRecyclerViewAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView userMessage, botMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            userMessage = itemView.findViewById(R.id.userMessage);
            botMessage = itemView.findViewById(R.id.botMessage);
        }

        public void bind(ChatMessage message) {
            if (message.isUser()) {
                userMessage.setText(message.getMessage());
                userMessage.setVisibility(View.VISIBLE);
                botMessage.setVisibility(View.GONE);
            } else {
                botMessage.setText(message.getMessage());
                botMessage.setVisibility(View.VISIBLE);
                userMessage.setVisibility(View.GONE);
            }
        }
    }
}
