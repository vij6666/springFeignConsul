package com.vijay.sidhu.internal.dao;

import com.vijay.sidhu.internal.model.Message;

import java.util.List;

public interface MessageDao {

    int insertMessage(String message);

    Message getMessage(int id);

    List<Message> getAllMessages();

    boolean deleteMessage(int id);
}
