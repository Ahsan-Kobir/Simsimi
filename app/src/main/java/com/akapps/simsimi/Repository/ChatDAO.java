package com.akapps.simsimi.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.akapps.simsimi.Models.Message;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ChatDAO {
    @Query("SELECT * FROM chats")
    LiveData<List<Message>> getChats();

    @Insert
    void insertChat(Message message);
}
