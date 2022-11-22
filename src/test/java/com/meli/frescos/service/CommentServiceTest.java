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
        BDDMockito.when(commentRepository.findByBuyerIdAnAndProductId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
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
                .thenReturn(BigInteger.valueOf(0L));
        BDDMockito.when(commentRepository.findByBuyerIdAnAndProductId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(commentModel);
        BDDMockito.when(batchStockRepository.findByBuyerAndProduct(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(BigInteger.valueOf(1L));

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

        BDDMockito.when(commentRepository.findByProductIdOrderByCreatedAt(ArgumentMatchers.anyLong()))
                .thenReturn(commentModelList);

        List<CommentModel> newCommentModelList = commentService.getRecentComments(commentModel.getId());

        assertEquals(commentModelList.size(), newCommentModelList.size());
        assertEquals(commentModelList.get(0).getId(), newCommentModelList.get(0).getId());

    }


    //    @Test
//    @DisplayName("Return all Buyer")
//    void findAll_returnAllSeller_whenSuccess() {
//        List<BuyerModel> buyerModelList = new ArrayList<>();
//        buyerModelList.add(new BuyerModel("Buyer", "12345678900"));
//
//        BDDMockito.when(repository.findAll())
//                .thenReturn(buyerModelList);
//
//        List<BuyerModel> buyerList = service.getAll();
//
//        assertThat(buyerList).isNotNull();
//        assertThat(buyerList).isEqualTo(buyerModelList);
//    }
//
//    @Test
//    @DisplayName("Return a Buyer by id")
//    void findById_returnBuyer_whenSucess() throws BuyerNotFoundException {
//        Long id = 1L;
//        String name = "Buyer";
//        String cpf = "12345678900";
//        BuyerModel buyer = new BuyerModel(id, name, cpf);
//
//        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
//                .thenReturn(Optional.of(buyer));
//
//        BuyerModel buyerTest = service.getById(id);
//
//        assertThat(buyerTest).isNotNull();
//        assertThat(buyerTest).isEqualTo(buyer);
//    }
//
//    @Test
//    @DisplayName("Throw exception when ID is not found.")
//    void findByIdSeller_returnBuyerByIdNotFoundException_whenInvalidId() {
//        assertThrows(BuyerNotFoundException.class, () -> {
//            BuyerModel buyerModel = service.getById(ArgumentMatchers.anyLong());
//        });
//    }
//
//    @Test
//    @DisplayName("Return updated Buyer")
//    void updateSeller_returnBuyerUpdated_whenSuccess() throws BuyerNotFoundException {
//        Long id = 1L;
//        String name = "Buyer";
//        String cpf = "12345678900";
//
//        BuyerModel buyer = new BuyerModel(id, name, cpf);
//
//        BDDMockito.when(repository.save(ArgumentMatchers.any(BuyerModel.class)))
//                .thenReturn(buyer);
//
//        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
//                .thenReturn(Optional.of(buyer));
//
//        String newName = "Vendedor teste";
//        BuyerModel buyerTest = new BuyerModel(id, newName, cpf);
//
//        service.update(buyerTest, id);
//
//        assertThat(buyerTest).isNotNull();
//        assertEquals(id, buyerTest.getId());
//        assertEquals(newName, buyerTest.getName());
//        assertEquals(cpf, buyerTest.getCpf());
//    }
//
////    @Test
////    @DisplayName("Delete Buyer")
////    void deleteById_notReturn_whenSuccess() {
////        Long id = 1L;
////        String name = "Buyer";
////        String cpf = "12345678900";
////        BuyerModel buyer = new BuyerModel(id, name, cpf);
////
////        BDDMockito.when(repository.save(ArgumentMatchers.any(BuyerModel.class)))
////                .thenReturn(buyer);
////
////        BuyerModel buyerTest = service.save(buyer);
////
////        service.deleteById(buyerTest.getId());
////
////        assertThrows(BuyerNotFoundException.class, () -> {
////            BuyerModel buyerModel = service.findById(id);
////        });
////
////    }
//
//    @Test
//    @DisplayName("Return a Buyer by cpf")
//    void findByCpf_returnBuyer_whenSuccess() {
//        Long id = 1L;
//        String name = "Buyer";
//        String cpf = "12345678900";
//        BuyerModel buyer = new BuyerModel(id, name, cpf);
//
//        BDDMockito.when(repository.findByCpf(ArgumentMatchers.anyString()))
//                .thenReturn(Optional.of(buyer));
//
//        Optional<BuyerModel> buyerTest = service.getByCpf(cpf);
//
//        assertThat(buyerTest).isNotNull();
//        assertThat(buyerTest.get()).isEqualTo(buyer);
//    }
}