package com.akapps.simsimi.Repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.akapps.simsimi.Models.Message;

@Database(entities = {Message.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    public abstract ChatDAO getChatDao();
    private static RoomDB instanceDatabase;
    public static synchronized RoomDB getDBInstance(Context context){
        if(instanceDatabase==null){
            instanceDatabase = Room.databaseBuilder(context, RoomDB.class, "chats").build();
        }
        return instanceDatabase;
    }
}
