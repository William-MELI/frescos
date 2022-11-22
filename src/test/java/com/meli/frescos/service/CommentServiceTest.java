package com.meli.frescos.service;

import com.meli.frescos.exception.InvalidCommentException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.CommentModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.repository.BatchStockRepository;
import com.meli.frescos.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BatchStockRepository batchStockRepository;

    @Mock
    IProductService iProductService;

    @Test
    @DisplayName("Create a new Comment successfully")
    void save_returnsCreatedComment_whenSuccess() throws InvalidCommentException {

        ProductModel productModel = new ProductModel();
        productModel.setId(1L);

        BuyerModel buyer = new BuyerModel();
        buyer.setId(1L);

        CommentModel commentModel = new CommentModel();
        commentModel.setComment("test");
        commentModel.setProduct(productModel);
        commentModel.setBuyer(buyer);
        commentModel.setId(1L);

        BDDMockito.when(commentRepository.save(ArgumentMatchers.any(CommentModel.class)))
                .thenReturn(commentModel);
        BDDMockito.when(commentRepository.findByBuyerIdAndProductId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(null);
        BDDMockito.when(iProductService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(productModel);
        BDDMockito.when(batchStockRepository.findByBuyerAndProduct(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(BigInteger.valueOf(1L));

        CommentModel newComment = commentService.save(commentModel);

        assertThat(newComment).isNotNull();
        assertThat(newComment.getId()).isPositive();
        assertEquals(commentModel.getComment(), newComment.getComment());
        assertEquals(commentModel.getBuyer().getId(), newComment.getBuyer().getId());
        assertEquals(commentModel.getProduct().getId(), newComment.getProduct().getId());

    }

    @Test
    @DisplayName("Throws a InvalidCommentException when User did not bought the Product")
    void save_throwInvalidCommentException_whenBuyerDidNotBoughtProduct() {

        ProductModel productModel = new ProductModel();
        productModel.setId(1L);

        BuyerModel buyer = new BuyerModel();
        buyer.setId(1L);

        CommentModel commentModel = new CommentModel();
        commentModel.setComment("test");
        commentModel.setProduct(productModel);
        commentModel.setBuyer(buyer);
        commentModel.setId(1L);

        BDDMockito.when(iProductService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(productModel);
        BDDMockito.when(batchStockRepository.findByBuyerAndProduct(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(BigInteger.valueOf(0L));

        assertThrows(
                InvalidCommentException.class,
                () -> {
                    commentService.save(commentModel);
                }
        );
    }

    @Test
    @DisplayName("Throws a InvalidCommentException when User did not bought the Product")
    void save_throwInvalidCommentException_whenBuyerAlreadyCommented() {

        ProductModel productModel = new ProductModel();
        productModel.setId(1L);

        BuyerModel buyer = new BuyerModel();
        buyer.setId(1L);

        CommentModel commentModel = new CommentModel();
        commentModel.setComment("test");
        commentModel.setProduct(productModel);
        commentModel.setBuyer(buyer);
        commentModel.setId(1L);

        BDDMockito.when(iProductService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(productModel);
        BDDMockito.when(batchStockRepository.findByBuyerAndProduct(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(BigInteger.valueOf(1L));
        BDDMockito.when(commentRepository.findByBuyerIdAndProductId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(commentModel);

        assertThrows(
                InvalidCommentException.class,
                () -> {
                    commentService.save(commentModel);
                }
        );
    }


    @Test
    @DisplayName("Return a list of  Comment successfully")
    void getRecentComments_returnListOfComment_whenSuccess() {

        ProductModel productModel = new ProductModel();
        productModel.setId(1L);

        BuyerModel buyer = new BuyerModel();
        buyer.setId(1L);

        CommentModel commentModel = new CommentModel();
        commentModel.setComment("test");
        commentModel.setProduct(productModel);
        commentModel.setBuyer(buyer);
        commentModel.setId(1L);

        List<CommentModel> commentModelList = new ArrayList<>();
        commentModelList.add(commentModel);

        BDDMockito.when(commentRepository.findByProductIdOrderByCreatedAtDesc(ArgumentMatchers.anyLong()))
                .thenReturn(commentModelList);

        List<CommentModel> newCommentModelList = commentService.getRecentComments(commentModel.getId());

        assertEquals(commentModelList.size(), newCommentModelList.size());
        assertEquals(commentModelList.get(0).getId(), newCommentModelList.get(0).getId());

    }

}