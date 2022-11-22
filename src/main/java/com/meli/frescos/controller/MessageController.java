package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.MessageRequest;
import com.meli.frescos.controller.dto.MyMessagesResponse;
import com.meli.frescos.exception.GetByIdMessageNotFoundException;
import com.meli.frescos.model.MessageModel;
import com.meli.frescos.model.UserProfileEnum;
import com.meli.frescos.service.IMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @RestController to Messages
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private final IMessageService serviceMessage;

    public MessageController(IMessageService serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

    /**
     * POST endpoint to store a {@link MessageModel}.
     * @param messageRequest
     * @return ResponseEntity<MessageModel> with status 201 created
     */
    @PostMapping
    public ResponseEntity<MessageModel> save(@Valid @RequestBody MessageRequest messageRequest) {
        serviceMessage.save(messageRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Return all Message given receiverId
     *
     * @param receiverId      the Message receiverId
     * @param receiverProfile the Message ReceiverProfile
     * @return List of Messages with status 200 ok
     */
    @GetMapping("/{receiverId}/{receiverProfile}")
    public ResponseEntity<List<MyMessagesResponse>> getMyMessages(@PathVariable long receiverId, @PathVariable UserProfileEnum receiverProfile) throws GetByIdMessageNotFoundException {
        List<MyMessagesResponse> getMessages = serviceMessage.getMyMessages(receiverId, receiverProfile);
        return new ResponseEntity<>(getMessages, HttpStatus.OK);
    }

    /**
     * This method update MessageReaded given messageId
     *
     * @param messageId Long related an Message
     * Return with status 200 ok
     * @throws GetByIdMessageNotFoundException Throws in case Message does not exist
     */
    @PatchMapping("/{messageId}")
    public ResponseEntity<Void> setMessageAsReaded(@PathVariable Long messageId) {
        serviceMessage.setMessageAsReaded(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method delete MessageReaded given messageId
     *
     * @param messageId Long related an Message
     * Return with status 200 ok
     * @throws GetByIdMessageNotFoundException Throws in case Message does not exist
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> delete(@PathVariable Long messageId) throws GetByIdMessageNotFoundException {
        serviceMessage.delete(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
