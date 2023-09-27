package com.akapps.simsimi.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.akapps.simsimi.Adapters.ChatsAdapter;
import com.akapps.simsimi.Models.Message;
import com.akapps.simsimi.Models.ResponseMessage;
import com.akapps.simsimi.Repository.MainRepository;
import com.akapps.simsimi.Repository.RoomDB;
import com.akapps.simsimi.Repository.SimsimiApi;
import com.akapps.simsimi.ViewModel.ChatViewModel;
import com.akapps.simsimi.ViewModel.ViewModelFactory;
import com.akapps.simsimi.databinding.ActivityMainBinding;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ChatsAdapter adapter;
    private ArrayList<Message> chatListArray = new ArrayList<>();
    private ChatViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRecyclerView();
        setViewModel();

        binding.sendBtn.setOnClickListener(v -> {
            if(binding.msg2Send.length()!=0){
                viewModel.sendMessage(binding.msg2Send.getText().toString());
                binding.msg2Send.setText("");
            } else {
                binding.msg2Send.setError("Type message here");
            }
        });
    }

    private void setViewModel() {

        RoomDB roomDB = RoomDB.getDBInstance(getApplicationContext());
        SimsimiApi simsimiApi = new SimsimiApi(getApplicationContext());

        MainRepository mainRepo = new MainRepository(roomDB, simsimiApi);
        ViewModelFactory  factory = new ViewModelFactory(mainRepo);
        viewModel = new ViewModelProvider(this, factory).get(ChatViewModel.class);

        //Check if we have data or not in db
        if(viewModel.getChats().getValue()!=null){
            chatListArray = (ArrayList<Message>) viewModel.getChats().getValue();
            updateChats(chatListArray);
        }

        viewModel.getChats().observe(this, messages -> {
            updateChats(messages);
        });
    }

    private void setRecyclerView() {
        binding.listChats.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatsAdapter(this, chatListArray);
        binding.listChats.setAdapter(adapter);
    }

    private void updateChats(List<Message> messageList) {
        adapter.setItems(messageList);
        adapter.notifyItemInserted(messageList.size()-1);
        binding.listChats.scrollToPosition(messageList.size()-1);
    }
}