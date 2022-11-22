package com.meli.frescos.service;

import com.meli.frescos.exception.InvalidCommentException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.CommentModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.repository.BatchStockRepository;
import com.meli.frescos.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BatchStockRepository batchStockRepository;

    @Autowired
    IProductService iProductService;

    @Autowired
    IBuyerService iBuyerService;

    public CommentModel save(CommentModel commentModel) throws InvalidCommentException {
        ProductModel productModel = iProductService.getById(commentModel.getProduct().getId());
        BuyerModel buyerModel = iBuyerService.getById(commentModel.getBuyer().getId());
        Long buyerId = buyerModel.getId();
        Long productId = productModel.getId();
        boolean productBoughtByUser = batchStockRepository.findByBuyerAndProduct(buyerModel.getId(), productModel.getId()).intValue() > 0;
        if (!productBoughtByUser) {
            throw new InvalidCommentException(String.format("Comprador de ID %d não possui compra fechada do produto de ID %d", buyerId, productId));
        }

        CommentModel commentExists = commentRepository.findByBuyerIdAndProductId(buyerId, productId);

        if (commentExists != null) {
            throw new InvalidCommentException(String.format("Comprador de ID %d já comentou no produto de ID %d", buyerId, productId));
        }

        commentModel.setProduct(productModel);
        return commentRepository.save(commentModel);
    }

    @Override
    public List<CommentModel> getRecentComments(Long productId) {
        iProductService.getById(productId);
        return commentRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
}
