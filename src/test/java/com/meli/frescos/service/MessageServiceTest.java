package com.meli.frescos.service;

import com.meli.frescos.controller.dto.MessageRequest;
import com.meli.frescos.controller.dto.MyMessagesResponse;
import com.meli.frescos.exception.BuyerNotFoundException;
import com.meli.frescos.exception.GetByIdMessageNotFoundException;
import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.MessageModel;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.model.UserProfileEnum;
import com.meli.frescos.repository.BuyerRepository;
import com.meli.frescos.repository.MessageRepository;
import com.meli.frescos.repository.SellerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    MessageService messageService;

    @Mock
    MessageRepository messageRepository;

    @Mock
    SellerRepository mockSellerRepository;

    @Mock
    BuyerRepository mockBuyerRepository;

    @Mock
    BuyerService mockBuyerService;

    @Mock
    SellerService mockSellerService;


    @Test
    @DisplayName("Create a new Message successfully")
    void save_whenSuccess() {

        MessageRequest request = generateMessageRequest(UserProfileEnum.Seller, UserProfileEnum.Buyer);

        MessageModel model = generateMessageModel(UserProfileEnum.Seller, UserProfileEnum.Buyer);

        Mockito.when(mockSellerRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        Mockito.when(mockBuyerRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        Mockito.when(messageRepository.save(ArgumentMatchers.any())).thenReturn(model);

        messageService.save(request);
    }

    @Test
    @DisplayName("Create a new Message Throws Exception BuyerNotFound when reciverProfile is Buyer and id invalid")
    void save_throwsException_whenBuyerIdIsInvalidAndReceiverProfileEnumIsBuyer() {

        MessageRequest request = generateMessageRequest(UserProfileEnum.Seller, UserProfileEnum.Buyer);

        Mockito.when(mockBuyerRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

        assertThrows(BuyerNotFoundException.class, () -> messageService.save(request));
    }

    @Test
    @DisplayName("Create a new Message Throws Exception SellerNotFound when reciverProfile is Seller and id invalid")
    void save_throwsException_whenSellerIdIsInvalidAndReceiverProfileEnumIsSeller() {

        MessageRequest request = generateMessageRequest(UserProfileEnum.Buyer, UserProfileEnum.Seller);

        Mockito.when(mockSellerRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

        assertThrows(SellerByIdNotFoundException.class, () -> messageService.save(request));
    }

    @Test
    @DisplayName("Create a new Message Throws Exception BuyerNotFound when senderProfile is Buyer and id invalid")
    void save_throwsException_whenBuyerIdIsInvalidAndSenderProfileEnumIsBuyer() {

        MessageRequest request = generateMessageRequest(UserProfileEnum.Buyer, UserProfileEnum.Seller);

        Mockito.when(mockSellerRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        Mockito.when(mockBuyerRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

        assertThrows(BuyerNotFoundException.class, () -> messageService.save(request));
    }

    @Test
    @DisplayName("Create a new Message Throws Exception SellerNotFound when senderProfile is Seller and id invalid")
    void save_throwsException_whenSellerIdIsInvalidAndSenderProfileEnumIsSeller() {

        MessageRequest request = generateMessageRequest(UserProfileEnum.Seller, UserProfileEnum.Buyer);

        Mockito.when(mockBuyerRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        Mockito.when(mockSellerRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

        assertThrows(SellerByIdNotFoundException.class, () -> messageService.save(request));
    }

    @Test
    @DisplayName("Get my Messages when receiverProfile is Buyer should return success")
    void getMyMessages_whenReceiverProfileIsBuyer_shouldReturnSuccess() {
        List<MessageModel> messageModelList = new ArrayList<>();
        messageModelList.add(generateMessageModel(UserProfileEnum.Buyer, UserProfileEnum.Buyer));
        messageModelList.add(generateMessageModel(UserProfileEnum.Buyer, UserProfileEnum.Buyer));
        Mockito.when(messageRepository.findAll()).thenReturn(messageModelList);

        Mockito.when(mockBuyerService.getById(1L)).thenReturn(BuyerModel.builder().name("foo").build());

        List<MyMessagesResponse> response = messageService.getMyMessages(1L, UserProfileEnum.Buyer);

        assertNotNull(response);
        assertTrue(response.size() == 2);
        assertEquals(messageModelList.get(0).getId(), response.get(0).getMessage_id());
        assertEquals("foo", response.get(0).getSender_name());
        assertEquals(messageModelList.get(0).getMessage(), response.get(0).getMessage());
        assertEquals(messageModelList.get(0).isMessageReaded(), response.get(0).isMessageReaded());
        assertEquals(messageModelList.get(0).getCreated_at(), response.get(1).getReceived_at());
    }

    @Test
    @DisplayName("Get my Messages when receiverProfile is Seller should return success")
    void getMyMessages_whenReceiverProfileIsSeller_shouldReturnSuccess() {
        List<MessageModel> messageModelList = new ArrayList<>();
        messageModelList.add(generateMessageModel(UserProfileEnum.Seller, UserProfileEnum.Seller));
        messageModelList.add(generateMessageModel(UserProfileEnum.Seller, UserProfileEnum.Seller));
        Mockito.when(messageRepository.findAll()).thenReturn(messageModelList);

        Mockito.when(mockSellerService.getById(ArgumentMatchers.anyLong())).thenReturn(SellerModel.builder().name("foo2").build());

        List<MyMessagesResponse> response = messageService.getMyMessages(1L, UserProfileEnum.Seller);

        assertNotNull(response);
        assertTrue(response.size() == 2);
        assertEquals(messageModelList.get(0).getId(), response.get(0).getMessage_id());
        assertEquals("foo2", response.get(0).getSender_name());
        assertEquals(messageModelList.get(0).getMessage(), response.get(0).getMessage());
        assertEquals(messageModelList.get(0).isMessageReaded(), response.get(0).isMessageReaded());
        assertEquals(messageModelList.get(0).getCreated_at(), response.get(1).getReceived_at());
    }

    @Test
    @DisplayName("Set Message as Readed when messageId is valid should return success")
    void setMessageAsReaded_whenMessageIdIsValid_shouldReturnSuccess() {

        Mockito.when(messageRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(generateMessageModel(UserProfileEnum.Buyer, UserProfileEnum.Seller)));

        messageService.setMessageAsReaded(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Set Message as Readed when messageId is invalid should Throw GetByIdMessageNotFoundException")
    void setMessageAsReaded_whenMessageIdIsInvalid_shouldThrowException() {

        assertThrows(GetByIdMessageNotFoundException.class, () -> messageService.setMessageAsReaded(ArgumentMatchers.anyLong()));
    }

    @Test
    @DisplayName("Delete Message when messageId is valid should return success")
    void delete_whenMessageIdIsValid_shouldReturnSuccess() {

        Mockito.when(messageRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(generateMessageModel(UserProfileEnum.Buyer, UserProfileEnum.Seller)));

        messageService.delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Delete Message when messageId is invalid should Throw GetByIdMessageNotFoundException")
    void delete_whenMessageIdIsInvalid_shouldThrowException() {
        assertThrows(GetByIdMessageNotFoundException.class, () -> messageService.delete(ArgumentMatchers.anyLong()));
    }

    private static MessageModel generateMessageModel(UserProfileEnum senderProfileEnum, UserProfileEnum receiverProfileEnum) {
        MessageModel model = MessageModel.builder()
                .sender_id(1L)
                .senderProfileEnum(senderProfileEnum)
                .receiver_id(1L)
                .receiverProfileEnum(receiverProfileEnum)
                .message("Olá! Gostaria de saber sobre o produto que comprei. Pode me ajudar?")
                .messageReaded(false)
                .created_at(LocalDateTime.now())
                .build();
        return model;
    }

    private static MessageRequest generateMessageRequest(UserProfileEnum senderProfileEnum, UserProfileEnum receiverProfileEnum) {
        MessageRequest request =MessageRequest.builder()
                .sender_id(1L)
                .senderProfileEnum(senderProfileEnum)
                .receiver_id(1L)
                .receiverProfileEnum(receiverProfileEnum)
                .message("Olá! Gostaria de saber sobre o produto que comprei. Pode me ajudar?")
                .messageReaded(false)
                .build();
        return request;
    }
}