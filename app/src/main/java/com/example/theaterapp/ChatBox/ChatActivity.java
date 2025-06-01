package com.example.theaterapp.ChatBox;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theaterapp.R;
import com.example.theaterapp.dao.PerformanceDAO;
import com.example.theaterapp.dao.PortofolioDAO;
import com.example.theaterapp.dao.TheaterDAO;
import com.example.theaterapp.dao.TicketDAO;
import com.example.theaterapp.domain.ChatMessage;
import com.example.theaterapp.domain.Performance;
import com.example.theaterapp.domain.Theater;
import com.example.theaterapp.domain.Ticket;
import com.example.theaterapp.domain.TicketLoader;
import com.example.theaterapp.domain.WalletManager;
import com.example.theaterapp.memoryDAO.PerformanceDAOmemory;
import com.example.theaterapp.memoryDAO.PortofolioDAOmemory;
import com.example.theaterapp.memoryDAO.TheaterDAOmemory;
import com.example.theaterapp.memoryDAO.TicketsDAOmemory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity
{
    private Theater currentTheater;
    private List<Map<String, String>> conversationHistory = new ArrayList<>();
    private boolean isFirstMessage = true;
    private String theaterContext;
    private RecyclerView recyclerView;
    private EditText inputMessage;
    private Button sendButton;
    private ImageButton backButton;
    private List<ChatMessage> messages;
    private ChatRecyclerViewAdapter adapter;
    private static int newBalance = -1;
    private PerformanceDAO performanceDAO = new PerformanceDAOmemory();
    private TicketDAO ticketDAO = new TicketsDAOmemory();
    private PortofolioDAO portfolioDAO = new PortofolioDAOmemory();
    private final OkHttpClient client = new OkHttpClient();
    private final String TAG = "LLM7";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TheaterDAO theaterDAO = new TheaterDAOmemory();
        currentTheater = theaterDAO.getTheater();
        theaterContext = buildTheaterContext(currentTheater);

        recyclerView = findViewById(R.id.chatRecyclerView);
        inputMessage = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.sendButton);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        messages = new ArrayList<>();
        adapter = new ChatRecyclerViewAdapter(messages);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.setAdapter(adapter);

        // Send initial bot message
        String welcomeMessage = "Hello, how can I help you?";
        addMessage(welcomeMessage, false);

        sendButton.setOnClickListener(v -> {
            String msg = inputMessage.getText().toString().trim();
            if (!msg.isEmpty())
            {
                addMessage(msg, true);
                getBotResponse(msg);
                inputMessage.setText("");
            }
        });
    }
    private String buildTheaterContext(Theater theater)
    {
        PerformanceDAO performanceDAO = new PerformanceDAOmemory();
        List<Performance> performanceList = performanceDAO.getAllPerformances();
        StringBuilder context = new StringBuilder();

        context.append("You are a virtual assistant for a theater.\n");
        context.append("Keep in mind that the current date is" + LocalDate.now());
        context.append("Theater name: ").append(theater.getName()).append("\n");
        context.append("Address: ").append(theater.getLocation()).append("\n");
        context.append("Contact: ").append(theater.getPhoneNumber()).append(" | ").append(theater.getEmail()).append("\n");
        context.append("Performances today include:\n");
        context.append("Take the DAO of the tickets: ").append(new TicketsDAOmemory().getAllTickets());

        newBalance = WalletManager.loadBalance(this);
        portfolioDAO.getWallet().setBalance(newBalance);
        int balance = portfolioDAO.getWallet().getBalance();
        context.append("Take the available money that the user has: ").append(balance);
        System.out.println("Balance: "+ balance);

        for (Performance p : performanceList)
        {
            context.append("- Title: ").append(p.getName()).append("\n");
            context.append("  Genre: ").append(p.getPerformanceType()).append("\n");
            context.append("  Duration: ").append(p.getPerformanceTime()).append("\n");
            context.append("  Description: ").append(p.getPerformancePlot()).append("\n");
            context.append("  Days: ").append(p.getPerformanceDays()).append("\n\n");
            context.append("  Room: ").append(p.getRoomID()).append("\n\n");
            context.append("  Ticket cost: ").append(p.getPrice()).append("\n\n");
            context.append("  Performance times: 6:00 PM and 9:00 PM").append("\n\n");
            context.append("Each performances plays every week in theirs scheduled days.");
        }

        // Response instructions
        context.append("\n## Response Rules\n");
        context.append("1. **Always** maintain polite theater-appropriate language\n");
        context.append("2. **Never** invent information - say 'I don't know' if unsure\n");
        context.append("3. For bookings/cancellations:\n");
        context.append("   a. Verify dates/times against performance schedule\n");
        context.append("   b. Flag conflicts immediately (e.g. 'No 9PM show on Sundays')\n");
        context.append("4. If they ask to communicate to someone or there is a problem with the understatement, give them the email and the phone of the theater\n");
        context.append("5. NEVER said wait or hold  to do the procedure, answer immediately \n");
        context.append("6. ALWAYS write ONLY ONE confirmation message for a procedure  \n");
        context.append("\nResponse Instructions:\n");


        // CANCEL TICKETS
        context.append("If the user wants to cancel one ticket," +
                       " respond to the user politely and accordingly. Communicate with the user and collect the ticket id." );
        context.append("- Examples:\n");
        context.append("  - 'I want to cancel a ticket with id 1'\n");
        context.append("  - 'I want refund'\n");
        context.append("- Required Fields:\n");
        context.append("  Ticket id (integer) \n\n");
        context.append("If the user has given all the necessary fields and the ticket id exists in the ticket DAO." );
        context.append(" 1. Write a confirmation message to the user explain to them that the money with deposit on their virtual wallet." +
                       " Wait for the positive or negative user's answer");
        context.append("- Response: Show summary + total price, await confirmation\n\n");
        context.append("2.If the user's answer is POSITIVE,  create a json");
        context.append("3. CREATE and SEND ALWAYS a JSON IN A the same MESSAGE with these FIELDS:\n");
        context.append("{\n");
        context.append("  \"classification\": \"CANCEL_TICKET\",\n");
        context.append("  \"ticket id\": \"number\",\n");
        context.append("}\n");
        context.append("4. The JSON must be the last part of your response\n");


        // INFORMATION
        context.append("If the user asks for information respond to the user politely and accordingly.");
        context.append("- Examples:\n");
        context.append("  - 'What shows are playing today?'\n");
        context.append("  - 'Where are you located?'\n");
        context.append("  - 'What's the duration of [performance]?'\n");
        context.append("- Response: Answer directly using theater/performance data\n\n");

        // BUY TICKETS
        context.append("If the user wants to buy one or more tickets," +
                       " respond to the user politely and accordingly. Communicate with the user and collect the following fields:" +
                       "name of the performance, date of the performance (yyyy-mm-dd), time of the performance and the number of wanted tickets");
        context.append("- Examples:\n");
        context.append("  - 'I want 2 tickets for tonight'\n");
        context.append("  - 'Book seats for Hamilton on 2023-12-25'\n");
        context.append("- Required Fields:\n");
        context.append("  1. Performance name\n  2. Date (YYYY-MM-DD)\n  3. Time (6:00 PM/9:00 PM)\n  4. Ticket quantity\n\n");

        context.append("If the user has given all the necessary fields." );
        context.append(" 1. Write ONLY ONE confirmation message to the user containing the total cost and that amount will withdraw from their virtual wallet." +
                        " Wait for the positive or negative user's answer");
        context.append("See if the user's balance are enough to buy the tickets and write a properly answer"+
                "if the amount are smaller, inform that the booking is canceled and appear the user balance");


        context.append("- Response: Show summary + total price, await confirmation\n\n");
        context.append("2.If the user's answer is POSITIVE,  create a json");
        context.append("3. CREATE and SEND ALWAYS a JSON IN A the same MESSAGE with these FIELDS:\n");
        context.append("{\n");
        context.append("  \"classification\": \"BUY_TICKET\",\n");
        context.append("  \"show\": \"name of performance\",\n");
        context.append("  \"date\": \"yyyy-mm-dd\",\n");
        context.append("  \"ticketCount\": number of tickets,\n");
        context.append("  \"showTime\": \"time \"\n");
        context.append("}\n");
        context.append("4. The JSON must be the last part of your response\n");

        // COMPLAIN
        context.append("If the user COMPLAINS about something respond to the user politely. If you can solve the problem help him otherwise provide contact information of the theatre.");
        context.append("- Examples:\n");
        context.append("  - 'I can't buy a ticket'\n");
        context.append("  - 'I can't find the email of the theater'\n");
        context.append("  - 'The theater staff are rude'\n");

        return context.toString();
    }


    private void addMessage(String message, boolean isUser)
    {
        messages.add(new ChatMessage(message, isUser));
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerView.scrollToPosition(messages.size() - 1);
    }

    private void getBotResponse(String userInput)
    {

        sendLLM7Request(userInput, new LLMResponseCallback()
        {
            @Override
            public void onResponse(String response)
            {
                // This runs when we actually get the response
                addMessage(response, false);
            }
        });
    }

    public static void setNewBalance(int balance)
    {
        newBalance = balance;
    }

    public static int getNewBalance()
    {
        return newBalance;
    }

    private void sendLLM7Request(String userPrompt, final LLMResponseCallback callback)
    {
        try {
            // Prepare user message
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", userPrompt);

            if (isFirstMessage)
            {
                // Add system message with theater context first
                Map<String, String> systemMessage = new HashMap<>();
                systemMessage.put("role", "system");
                systemMessage.put("content", buildTheaterContext(currentTheater));
                conversationHistory.add(systemMessage);
                isFirstMessage = false;
            }

            // Add user message to conversation history
            conversationHistory.add(userMessage);

            // Construct the request payload
            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("model", "gpt-4.1-nano");
            requestBodyMap.put("messages", conversationHistory);

            // Convert to JSON
            String jsonBody = objectMapper.writeValueAsString(requestBodyMap);
            RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));

            Request request = new Request.Builder()
                    .url("https://api.llm7.io/v1/chat/completions")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        Log.e(TAG, "Request failed", e);
                        callback.onResponse("Sorry, I'm having trouble connecting. Please repeat the question");
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    if (!response.isSuccessful())
                    {
                        runOnUiThread(() -> {
                            Log.e(TAG, "API error: " + response.code());
                            callback.onResponse("Sorry, there was an API error.");
                        });
                        return;
                    }

                    try {
                        String responseData = response.body().string();


                        JsonNode rootNode = objectMapper.readTree(responseData);
                        String content = rootNode.path("choices")
                                .get(0)
                                .path("message")
                                .path("content")
                                .asText();

                        String new_message= separateJsonAndText(content);
                        System.out.println(new_message);


                        // Add assistant response to conversation history
                        Map<String, String> assistantMessage = new HashMap<>();
                        assistantMessage.put("role", "assistant");
                        assistantMessage.put("content", content);
                        conversationHistory.add(assistantMessage);

                        runOnUiThread(() -> {
                            // Show the normal message to user
                            addMessage(new_message, false);
                        });

                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            Log.e(TAG, "Parsing error", e);
                            callback.onResponse("Sorry, I couldn't understand the response.");
                        });
                    }
                }
            });

        } catch (Exception e) {
            runOnUiThread(() -> {
                Log.e(TAG, "Request creation failed", e);
                callback.onResponse("Sorry, there was an error creating the request.");
            });
        }
    }

    public String separateJsonAndText(String input) throws JsonProcessingException {

        input = input.replace("Here is the confirmation in JSON format:", "").trim();
        input = input.replace("```json", "").trim();
        int jsonStart = input.indexOf('{');
        int jsonEnd = input.lastIndexOf('}');
        if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
            String simpleText = input.substring(0, jsonStart).trim();
            String json = input.substring(jsonStart, jsonEnd + 1).trim();

            // Output results
            System.out.println("Simple Text:\n" + simpleText);
            System.out.println("\nJSON:\n" + json);
            jsonProcessor(json);
            return simpleText;
        } else {
            System.out.println("Could not find a valid JSON block.");
            return input;
        }
    }

    private void jsonProcessor(String json) throws JsonProcessingException
    {
        JsonNode rootNode = objectMapper.readTree(json);

            // Verify classification
            String classification = rootNode.path("classification").asText();

            if ("BUY_TICKET".equals(classification))
            {
                // Extract purchase details from JSON
                String show = rootNode.path("show").asText();
                String date = rootNode.path("date").asText();
                int ticketCount = rootNode.path("ticketCount").asInt();
                String showTime = rootNode.path("showTime").asText();

                Log.d(TAG, "Extracted Details - Show: " + show + ", Date: " + date +
                        ", Tickets: " + ticketCount + ", Time: " + showTime + ", Name: " );

                // Fetch performance details
                Performance performance = performanceDAO.findByName(show);
                if (performance == null)
                {
                    Log.d(TAG, "Performance not found: " + show);
                    return;
                }

                // Calculate total cost
                int price = performance.getPrice();
                int totalCost = price * ticketCount;

                // Check and update balance
                newBalance = WalletManager.loadBalance(this);
                portfolioDAO.getWallet().setBalance(newBalance);
                if (portfolioDAO.getWallet().getBalance() < totalCost)
                {
                    Log.d(TAG, "Insufficient balance. Current: " + newBalance + ", Required: " + totalCost);
                    return;
                }

                portfolioDAO.getWallet().withdraw(totalCost);
                newBalance = portfolioDAO.getWallet().getBalance();
                WalletManager.saveBalance(this, newBalance);
                Log.d(TAG, "Purchase successful. New balance: " + newBalance);

                for (int i = 0; i < ticketCount; i++)
                {
                    Ticket ticket = new Ticket(performance.getName(), LocalDate.parse(date), showTime, generateSeat());
                    ticketDAO.save(ticket);
                }
                TicketLoader.saveTickets(ChatActivity.this, ticketDAO.getAllTickets());


            } else if ("CANCEL_TICKET".equals(classification)) {
                String id = rootNode.path("ticket id").asText();
                Ticket ticket = new TicketsDAOmemory().findById(Integer.parseInt(id));
                ticketDAO.getAllTickets().remove(ticket);

                newBalance = WalletManager.loadBalance(this);
                portfolioDAO.getWallet().setBalance(newBalance);
                portfolioDAO.getWallet().deposit(20);
                newBalance = portfolioDAO.getWallet().getBalance();
                WalletManager.saveBalance(this, newBalance);

                TicketLoader.saveTickets(ChatActivity.this, ticketDAO.getAllTickets());

            }
    }

    private String generateSeat()
    {
        char row = (char) ('A' + (int)(Math.random() * 6));
        int pos = 1 + (int)(Math.random() * 10);
        return "Row" + row + " Pos" + pos;
    }

    private interface LLMResponseCallback
    {
        void onResponse(String response);
    }
}