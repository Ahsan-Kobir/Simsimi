package com.akapps.simsimi.CallBacks;

import com.akapps.simsimi.Models.Message;

public interface OnNewChatCallBack {
    void onMessageReceived(Message message);
    void onError(String error);
}
