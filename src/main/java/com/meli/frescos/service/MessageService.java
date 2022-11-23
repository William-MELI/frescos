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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all Message related functions
 * Using @Service from spring
 */
@Service
public class MessageService implements IMessageService {

    private final MessageRepository repository;

    private final ISellerService sellerService;

    private final IBuyerService buyerService;

    private final BuyerRepository buyerRepository;

    private final SellerRepository sellerRepository;

    public MessageService(MessageRepository messageRepository, ISellerService sellerService, IBuyerService buyerService, BuyerRepository buyerRepository, SellerRepository sellerRepository) {
        this.repository = messageRepository;
        this.sellerService = sellerService;
        this.buyerService = buyerService;
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
    }

    /**
     * Save a new Message at storage
     *
     * @param message the new Message to store
     * @return the new created client
     */
    @Override
    public void save(MessageRequest message) {
        validateRequest(message);

        MessageModel model = MessageModel.builder()
                .sender_id(message.getSender_id())
                .senderProfileEnum(message.getSenderProfileEnum())
                .receiver_id(message.getReceiver_id())
                .receiverProfileEnum(message.getReceiverProfileEnum())
                .message(message.getMessage())
                .messageReaded(message.isMessageReaded())
                .created_at(LocalDateTime.now())
                .build();
        repository.save(model);
    }

    /**
     * Validate if request Ids are valid
     * @param message
     */
    private void validateRequest(MessageRequest message) {
        if (message.getReceiverProfileEnum() == UserProfileEnum.Buyer &&
                !buyerRepository.existsById(message.getReceiver_id())) {
            throw new BuyerNotFoundException(String.format("Comprador com ID %d não encontrado", message.getReceiver_id()));
        } else if (message.getReceiverProfileEnum() == UserProfileEnum.Seller &&
                !sellerRepository.existsById(message.getReceiver_id())) {
            throw new SellerByIdNotFoundException(message.getReceiver_id());
        } else if (message.getSenderProfileEnum() == UserProfileEnum.Buyer &&
                !buyerRepository.existsById(message.getSender_id())) {
            throw new BuyerNotFoundException(String.format("Comprador com ID %d não encontrado", message.getReceiver_id()));
        } else if (!sellerRepository.existsById(message.getSender_id())) {
            throw new SellerByIdNotFoundException(message.getReceiver_id());
        }
    }

    /**
     * Return all Message given receiverId
     *
     * @param receiverId the Message receiverId
     * @param receiverProfile the Message ReceiverProfile
     * @return List of Messages
     */
    @Override
    public List<MyMessagesResponse> getMyMessages(long receiverId, UserProfileEnum receiverProfile) {
        List<MessageModel> myMessagesList = repository.findAll().stream().filter(x -> x.getReceiver_id() == receiverId && x.getReceiverProfileEnum() == receiverProfile).toList();
        List<MyMessagesResponse> response = new ArrayList<>();

        for (MessageModel model : myMessagesList) {
            if (model.getSenderProfileEnum() == UserProfileEnum.Buyer) {
                BuyerModel buyer = buyerService.getById(model.getSender_id());
                response.add(MyMessagesResponse.builder()
                        .message_id(model.getId())
                        .sender_name(buyer.getName())
                        .message(model.getMessage())
                        .messageReaded(model.isMessageReaded())
                        .received_at(model.getCreated_at())
                        .build());
            } else if (model.getSenderProfileEnum() == UserProfileEnum.Seller) {
                SellerModel seller = sellerService.getById(model.getSender_id());
                response.add(MyMessagesResponse.builder()
                        .message_id(model.getId())
                        .sender_name(seller.getName())
                        .message(model.getMessage())
                        .messageReaded(model.isMessageReaded())
                        .received_at(model.getCreated_at())
                        .build());
            }
        }
        ordenateListByMostRecentMessages(response);
        return response;
    }

    /**
     * Return list sorted by most recent date
     * @param response
     */
    private static void ordenateListByMostRecentMessages(List<MyMessagesResponse> response) {
        Collections.sort(response, (o1, o2) -> {
            if (o1.getReceived_at() == null || o2.getReceived_at() == null)
                return 0;
            return o2.getReceived_at().compareTo(o1.getReceived_at());
        });
    }

    /**
     * This method update MessageReaded given messageId
     *
     * @param messageId Long related an Message
     * @throws GetByIdMessageNotFoundException Throws in case Message does not exist
     */
    @Override
    public void setMessageAsReaded(Long messageId) throws GetByIdMessageNotFoundException {
        Optional<MessageModel> message = repository.findById(messageId);

        if (message.isEmpty()) {
            throw new GetByIdMessageNotFoundException(messageId);
        }
        message.get().setMessageReaded(true);
        repository.save(message.get());
    }

    /**
     * This method delete MessageReaded given messageId
     *
     * @param messageId Long related an Message
     * @throws GetByIdMessageNotFoundException Throws in case Message does not exist
     */
    @Override
    public void delete(long messageId) throws GetByIdMessageNotFoundException {
        Optional<MessageModel> message = repository.findById(messageId);
        if (message.isEmpty()) {
            throw new GetByIdMessageNotFoundException(messageId);
        }
        repository.deleteById(messageId);
    }
}
