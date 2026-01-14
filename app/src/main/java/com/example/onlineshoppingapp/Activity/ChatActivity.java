package com.example.onlineshoppingapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingapp.databinding.ActivityChatBinding;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;

// Correct Imports
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private GenerativeModelFutures model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Initialize the Gemini AI Model
        // IMPORTANT: Replace the key below with your actual API key
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", "AIzaSyAqxasYjXaQBUS0SAgQeNGMHcMa0EFhqOM");
        model = GenerativeModelFutures.from(gm);

        binding.backBtn.setOnClickListener(v -> finish());

        binding.sendBtn.setOnClickListener(v -> {
            String message = binding.messageInput.getText().toString();
            if (!message.isEmpty()) {
                callGeminiAI(message);
            }
        });
    }

    private void callGeminiAI(String userMessage) {
        binding.chatHistory.append("\nYou: " + userMessage);
        binding.messageInput.setText("");
        binding.chatHistory.append("\nHI , What can i help you with");

        Content content = new Content.Builder()
                .addText(userMessage)
                .build();

        Executor executor = Executors.newSingleThreadExecutor();

        // FIXED: Using the standard ListenableFuture
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        // FIXED: Using the standard FutureCallback
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String aiResponse = result.getText();

                runOnUiThread(() -> {
                    String currentChat = binding.chatHistory.getText().toString();
                    if (currentChat.endsWith("\nAI is typing...")) {
                        currentChat = currentChat.substring(0, currentChat.length() - 16);
                        binding.chatHistory.setText(currentChat);
                    }
                    binding.chatHistory.append("\nAI: " + aiResponse + "\n");
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }, executor);
    }
}
