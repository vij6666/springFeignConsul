package com.vijay.sidhu.internal.service;

import com.vijay.sidhu.internal.dao.MessageDao;
import com.vijay.sidhu.internal.exceptions.MessageSvcException;
import com.vijay.sidhu.internal.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternalMessageSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalMessageSvc.class);
    @Autowired
    private final MessageDao messageDao;

    public InternalMessageSvc(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public List<Message> getAllMessages(){
        return messageDao.getAllMessages();
    }

    public Message getMessage(int id) throws MessageSvcException {

        Message message = messageDao.getMessage(id);
        if (message != null) {
            return message;
        } else {
            throw new MessageSvcException("Message does not exist: " + id);
        }
    }

    public int addMessage(String message){
        return messageDao.insertMessage(message);
    }

    public boolean deleteMessage(int id){
        return messageDao.deleteMessage(id);
    }

}
