package com.akapps.simsimi.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.akapps.simsimi.Models.Message;
import com.akapps.simsimi.Repository.MainRepository;


import java.util.List;

public class ChatViewModel extends ViewModel {
    private MainRepository repository;
    public ChatViewModel(MainRepository mainRepository){
        this.repository  = mainRepository;
    }

    public LiveData<List<Message>> getChats(){
        return repository.getChats();
    }

    public void sendMessage(String message){
        repository.sendMessage(message);
    }

}
