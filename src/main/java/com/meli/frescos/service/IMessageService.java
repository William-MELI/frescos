package com.meli.frescos.service;

import com.meli.frescos.controller.dto.MessageRequest;
import com.meli.frescos.controller.dto.MyMessagesResponse;
import com.meli.frescos.exception.GetByIdMessageNotFoundException;
import com.meli.frescos.model.UserProfileEnum;

import java.util.List;

public interface IMessageService {

   void save(MessageRequest message);

   List<MyMessagesResponse> getMyMessages(long receiverId, UserProfileEnum receiverProfile);

   void setMessageAsReaded(Long messageId) throws GetByIdMessageNotFoundException;

   void delete(long message_id) throws GetByIdMessageNotFoundException;
}
