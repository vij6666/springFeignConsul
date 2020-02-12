package com.vijay.sidhu.external.feignClient;

import com.vijay.sidhu.external.model.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "InternalMessageSvc")
public interface InternalMessageSvcClient {

    @RequestMapping(method = RequestMethod.GET, value = "/message")
    List<Message> getAllMessages();

    @RequestMapping(method = RequestMethod.GET, value = "/message/{id}")
    ResponseEntity<Message> getMessageById(@PathVariable int id);

    @RequestMapping(method = RequestMethod.POST, value = "/message")
    Message createMessage(Message message);

    @RequestMapping(method = RequestMethod.DELETE, value = "/message/{id}")
    ResponseEntity deleteMessage(@PathVariable int id);

}
