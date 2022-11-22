package com.meli.frescos.service;

import com.meli.frescos.exception.InvalidCommentException;
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

    public CommentModel save(CommentModel commentModel) throws InvalidCommentException {
        ProductModel productModel = iProductService.getById(commentModel.getProduct().getId());
        Long buyerId = commentModel.getBuyer().getId();
        Long productId = productModel.getId();
        boolean productBoughtByUser = batchStockRepository.productBoughtByUser(commentModel.getBuyer(), productModel);
        if (!productBoughtByUser) {
            throw new InvalidCommentException(String.format("Comprador de ID %d não possui compra fechada do produto de ID %d", buyerId, productId));
        }
        commentModel.setProduct(productModel);
        return commentRepository.save(commentModel);
    }

    @Override
    public List<CommentModel> getRecentComments(CommentModel commentModel) {
        return commentRepository.findByProductIdAndBuyerIdOrderByCreatedAt(commentModel.getProduct().getId(), commentModel.getBuyer().getId());
    }
}
