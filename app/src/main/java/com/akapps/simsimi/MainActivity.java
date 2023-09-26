package com.akapps.simsimi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.akapps.simsimi.Models.Message;
import com.akapps.simsimi.Models.ResponseMessage;
import com.akapps.simsimi.databinding.ActivityMainBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ChatsAdapter adapter;
    private ArrayList<Message> chatListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRecyclerView();

        binding.sendBtn.setOnClickListener(v -> {
            if(binding.msg2Send.length()!=0){
                sendMessage(binding.msg2Send.getText().toString());
                binding.msg2Send.setText("");
            } else {
                binding.msg2Send.setError("Type message here");
            }
        });
    }

    private void setRecyclerView() {
        binding.listChats.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatsAdapter(this, chatListArray);
        binding.listChats.setAdapter(adapter);
    }

    private void sendMessage(String message){

        updateChats(message, ChatsAdapter.TYPE_SENT);

        String url = "https://api.simsimi.vn/v1/simtalk";
        String languageCode = "en";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                        // 'response' contains the API response
                        Gson gson = new Gson();
                        ResponseMessage result = gson.fromJson(response, ResponseMessage.class);
                        String messageRcvd = result.getMessage();
                        updateChats(messageRcvd, ChatsAdapter.TYPE_RECEIVED);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(binding.msg2Send,error.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("text", message);
                params.put("lc", languageCode);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void updateChats(String message, int TYPE) {
        Message newMessage = new Message(TYPE, message);
        chatListArray.add(newMessage);
        adapter.notifyItemInserted(chatListArray.size()-1);
        binding.listChats.scrollToPosition(chatListArray.size()-1);
    }
}