package com.vijay.sidhu.external.service;

import com.vijay.sidhu.external.feignClient.InternalMessageSvcClient;
import com.vijay.sidhu.external.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ExternalMessageSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalMessageSvc.class);
    @Autowired
    private InternalMessageSvcClient internalMessageSvcClient;


    @Async
    public CompletableFuture<List<Message>> getAllMessages(){
        List<Message> messages = internalMessageSvcClient.getAllMessages();
        return CompletableFuture.completedFuture(messages);
    }

    @Async
    public CompletableFuture<Integer> saveMessage(Message message) {
        Message createdMessage = internalMessageSvcClient.createMessage(message);
        return CompletableFuture.completedFuture(createdMessage.getId());
    }

    @Async
    public CompletableFuture<ResponseEntity<Message>> getMessage(int id) {
        ResponseEntity<Message> responseEntity = internalMessageSvcClient.getMessageById(id);
        return CompletableFuture.completedFuture(responseEntity);
    }

    @Async
    public CompletableFuture<ResponseEntity> deleteMessage(int id) {
        ResponseEntity responseEntity = internalMessageSvcClient.deleteMessage(id);
        return CompletableFuture.completedFuture(responseEntity);
    }



}
