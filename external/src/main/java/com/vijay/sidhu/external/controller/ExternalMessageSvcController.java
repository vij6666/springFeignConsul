package com.vijay.sidhu.external.controller;

import com.vijay.sidhu.external.exceptions.ResourceNotFoundException;
import com.vijay.sidhu.external.feignClient.InternalMessageSvcClient;
import com.vijay.sidhu.external.model.Message;
import com.vijay.sidhu.external.service.ExternalMessageSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
@EnableFeignClients
public class ExternalMessageSvcController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalMessageSvcController.class);

    @Autowired
    private ExternalMessageSvc externalMessageSvc;

    @Autowired
    private InternalMessageSvcClient internalMessageSvc;

    /**
     *  Get all messages list.
     */
    @RequestMapping(value = "/message", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody CompletableFuture<ResponseEntity> getAllMessages() {

        return externalMessageSvc.getAllMessages().<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetMessageFailure);

    }

    /**
     * Get message by id.
     *
     * @param id the message id
     * @return the message by id
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int id) {
        ResponseEntity responseEntity;
        CompletableFuture<ResponseEntity<Message>> createdMesssageFuture = externalMessageSvc.getMessage(id);
        try {
            responseEntity = createdMesssageFuture.get();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
    public @ResponseBody ResponseEntity createMessage(@RequestBody Message message) {

        ResponseEntity<Integer> responseEntity;
        CompletableFuture<Integer> createdMesssageFuture = externalMessageSvc.saveMessage(message);
        try {
            responseEntity = new ResponseEntity(Collections.singletonMap("id", createdMesssageFuture.get()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    /**
     * Delete message.
     *
     * @param id the message id
     * @return whether operation is successful
     * @throws Exception the exception
     */
    @RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteUser(@PathVariable(value = "id") int id) throws Exception {
        ResponseEntity responseEntity;
        CompletableFuture<ResponseEntity> completableFuture = externalMessageSvc.deleteMessage(id);
        try {
            responseEntity = new ResponseEntity<>(completableFuture.get(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    private static Function<Throwable, ResponseEntity<? extends List<Message>>> handleGetMessageFailure = throwable ->{
        LOGGER.error("Failed to get Messages from Internal Message Service: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

}
