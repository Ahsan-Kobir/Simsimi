package com.akapps.simsimi.Repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.akapps.simsimi.Adapters.ChatsAdapter;
import com.akapps.simsimi.CallBacks.OnNewChatCallBack;
import com.akapps.simsimi.Models.Message;


import java.util.List;

public class MainRepository implements OnNewChatCallBack {
    private RoomDB roomDB; //Local database
    private SimsimiApi simsimiApi; //Remote data api

    public MainRepository(RoomDB roomDB, SimsimiApi simsimiApi) {
        this.roomDB = roomDB;
        this.simsimiApi = simsimiApi;
        simsimiApi.setCallBack(this);
    }

    public LiveData<List<Message>> getChats(){
        return roomDB.getChatDao().getChats();
    }

    public void sendMessage(String message){
        simsimiApi.sendMessage(message);
        Message sentMessage = new Message(ChatsAdapter.TYPE_SENT, message);
        saveMessageToRoom(sentMessage);
    }

    private void saveMessageToRoom(Message message){
        ChatDAO chatDAO = roomDB.getChatDao();
        AsyncTask.execute(()->{
            chatDAO.insertChat(message);
        });
    }


    @Override
    public void onMessageReceived(Message message) {
        saveMessageToRoom(message);
    }

    @Override
    public void onError(String error) {

    }
}
