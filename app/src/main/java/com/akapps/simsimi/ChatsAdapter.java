package com.akapps.simsimi;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akapps.simsimi.Models.Message;
import com.akapps.simsimi.databinding.ItemMessageReceivedBinding;
import com.akapps.simsimi.databinding.ItemMessageSentBinding;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Message> chatListArray;
    public static final int TYPE_SENT = 0;
    public static final int TYPE_RECEIVED = 1;


    public ChatsAdapter(Context context, ArrayList<Message> chatListArray){
        this.context = context;
        this.chatListArray = chatListArray;
    }
    @NonNull
    @Override
    public ChatsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                 .inflate(
                viewType==TYPE_SENT ? R.layout.item_message_sent : R.layout.item_message_received,
                parent,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.MyViewHolder holder, int position) {
        holder.updateData(chatListArray.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return chatListArray.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return chatListArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void updateData(Message message){
            if(message.getType()==TYPE_SENT){
                messageTv = itemView.findViewById(R.id.sentMsg);
            } else {
                messageTv = itemView.findViewById(R.id.rcvdMsg);
            }
            messageTv.setText(message.getMessage());
        }
    }
}
