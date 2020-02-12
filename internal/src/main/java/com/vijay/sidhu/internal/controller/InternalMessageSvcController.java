package com.vijay.sidhu.internal.controller;

import com.vijay.sidhu.internal.exceptions.MessageSvcException;
import com.vijay.sidhu.internal.model.Message;
import com.vijay.sidhu.internal.service.InternalMessageSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController

public class InternalMessageSvcController {


    private static final Logger LOGGER = LoggerFactory.getLogger(InternalMessageSvcController.class);

    @Autowired
    private InternalMessageSvc internalMessageSvc;

    /**
     *  Get all messages list.
     */
    @RequestMapping(value = "/message", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<List<Message>> getAllMessages() {
                return new ResponseEntity<>(internalMessageSvc.getAllMessages(), HttpStatus.OK);
        }

    /**
     * Get message by id.
     *
     * @param id the message id
     * @return the message by id
     * @throws MessageSvcException
     */
    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int id) {
        ResponseEntity responseEntity;
        try {
            Message retrievedMessage = internalMessageSvc.getMessage(id);
            responseEntity = new ResponseEntity(retrievedMessage, HttpStatus.OK);
        } catch (MessageSvcException e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /**
     * Create new message.
     *
     * @param message the message
     * @return the message
     */
    @RequestMapping(value = "/message", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Map> createMessage(@RequestBody Message message) {

        int messageId = internalMessageSvc.addMessage(message.getmessage());
        return new ResponseEntity<>(Collections.singletonMap("id", messageId), HttpStatus.OK);
    }

    /**
     * Delete message.
     *
     * @param id the message id
     * @return whether operation is successful
     * @throws Exception the exception
     */
    @RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deleteMessage(@PathVariable int id) throws Exception {

        boolean deleted = internalMessageSvc.deleteMessage(id);
        if(deleted) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
