package com.akapps.simsimi.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.akapps.simsimi.Repository.MainRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private MainRepository repository;

    public ViewModelFactory(MainRepository repository) {
        this.repository = repository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
       if(modelClass.isAssignableFrom(ChatViewModel.class)){
           return (T) new ChatViewModel(repository);
       }
       throw new IllegalArgumentException("Unknown class except or invalid argument");
    }
}
