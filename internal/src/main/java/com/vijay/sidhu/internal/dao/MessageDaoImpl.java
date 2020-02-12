package com.vijay.sidhu.internal.dao;

import com.vijay.sidhu.internal.model.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MessageDaoImpl implements MessageDao {

    private AtomicInteger id = new AtomicInteger(0);
    private ConcurrentHashMap<Integer, Message> messageConcurrentHashMap;

    public MessageDaoImpl() {
        messageConcurrentHashMap = new ConcurrentHashMap<Integer, Message>();
    }

    @Override
    public int insertMessage(String message){
        int messageId = id.incrementAndGet();
        Message messageToCreate = new Message(messageId, message);
        messageConcurrentHashMap.put(messageId, messageToCreate);

        return messageId;
    }

    @Override
    public Message getMessage(int id){
        return messageConcurrentHashMap.get(id);
    }

    @Override
    public List<Message> getAllMessages(){
        return new ArrayList<>(messageConcurrentHashMap.values());
    }

    @Override
    public boolean deleteMessage(int id){
        if (messageConcurrentHashMap.containsKey(id)){
            messageConcurrentHashMap.remove(id);
            return true;
        } else {
            return false;
        }
    }

}
