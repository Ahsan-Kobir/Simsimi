package com.akapps.simsimi.Repository;

import android.content.Context;
import com.akapps.simsimi.Adapters.ChatsAdapter;
import com.akapps.simsimi.CallBacks.OnNewChatCallBack;
import com.akapps.simsimi.Models.Message;
import com.akapps.simsimi.Models.ResponseMessage;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SimsimiApi {
    private Context context;
    OnNewChatCallBack onNewChatCallBack;
    public SimsimiApi(Context context){
        this.context = context;
    }

    public void setCallBack(OnNewChatCallBack callBack){
        this.onNewChatCallBack = callBack;
    }
    public void sendMessage(String message){
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
                        Message newMessage = new Message(ChatsAdapter.TYPE_RECEIVED, messageRcvd);
                        onNewChatCallBack.onMessageReceived(newMessage);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onNewChatCallBack.onError(error.getMessage());
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
        VolleyManager.getInstance(context).addToRequestQueue(stringRequest);
    }
}
