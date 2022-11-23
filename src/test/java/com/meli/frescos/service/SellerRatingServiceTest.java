package com.meli.frescos.service;

import com.meli.frescos.exception.SellerRatingAlreadyExist;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.SellerRatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SellerRatingServiceTest {

    @InjectMocks
    private SellerRatingService sellerRatingService;

    @Mock
    private SellerRatingRepository sellerRatingRepository;

    @Mock
    private ISellerService iSellerService;

    private SellerModel seller;
    private BuyerModel buyer;
    private PurchaseOrderModel purchaseOrder;
    private SellerRatingModel sellerRating;

    @BeforeEach
    void setup(){
        seller = new SellerModel(1L, "Antônio", "78945613-99", null);
        buyer = new BuyerModel(1L, "Katarina", "123456789-00");
        purchaseOrder = new PurchaseOrderModel(1L, LocalDate.now(), OrderStatusEnum.CLOSED, buyer);
        sellerRating = new SellerRatingModel(1L, seller, purchaseOrder, buyer, 5.00);
    }

    @Test
    @DisplayName("Create a new SellerRating successfully")
    void saveSellerRating_returnsCreatedSellerRating_whenSuccess() {

        List<SellerRatingModel> sellerRatingList = new ArrayList<>();
        sellerRatingList.add(sellerRating);

        BDDMockito.when(sellerRatingRepository.findBySellerAndBuyerAndPurchaseOrder(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(null);
        BDDMockito.when(sellerRatingRepository.save(ArgumentMatchers.any()))
                .thenReturn(sellerRating);
        BDDMockito.when(iSellerService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(seller);
        BDDMockito.when(sellerRatingRepository.findBySeller(ArgumentMatchers.any()))
                .thenReturn(sellerRatingList);
        BDDMockito.when(iSellerService.update(seller, seller.getId()))
                .thenReturn(seller);

        SellerRatingModel sellerRatingTest = sellerRatingService.save(sellerRating);

        assertThat(sellerRatingTest).isNotNull();
        assertThat(sellerRatingTest.getId()).isPositive();
        assertEquals(sellerRatingTest.getSeller(), sellerRating.getSeller());
        assertEquals(sellerRatingTest.getPurchaseOrder(), sellerRating.getPurchaseOrder());
        assertEquals(sellerRatingTest.getBuyer(), sellerRating.getBuyer());
    }

    @Test
    @DisplayName("Throw exception when the same record already exists.")
    void save_throwsSellerRatingAlreadyExist_whenSameRecordAlreadyExists() {
        BDDMockito.when(sellerRatingRepository.findBySellerAndBuyerAndPurchaseOrder(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(sellerRating);

        assertThrows(SellerRatingAlreadyExist.class, () -> {
            sellerRatingService.save(sellerRating);
        });
    }

    @Test
    @DisplayName("Returns a SellerRating when the record already exist")
    void getBySellerAndBuyerAndPurchaseOrder_returnSellerRating_whenAlreadyExist() {
        List<SellerRatingModel> sellerRatingList = new ArrayList<>();
        sellerRatingList.add(sellerRating);

        BDDMockito.when(sellerRatingRepository.findBySellerAndBuyerAndPurchaseOrder(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(sellerRating);

        SellerRatingModel sellerRatingTest = sellerRatingService.getBySellerAndBuyerAndPurchaseOrder(seller, buyer, purchaseOrder);

        assertThat(sellerRatingTest).isNotNull();
        assertThat(sellerRatingTest.getId()).isPositive();
        assertEquals(sellerRatingTest.getSeller(), sellerRating.getSeller());
        assertEquals(sellerRatingTest.getPurchaseOrder(), sellerRating.getPurchaseOrder());
        assertEquals(sellerRatingTest.getBuyer(), sellerRating.getBuyer());
    }

    @Test
    @DisplayName("Returns null when the record does not exist")
    void getBySellerAndBuyerAndPurchaseOrder_returnNull_whenDoesNotExist() {
        BDDMockito.when(sellerRatingRepository.findBySellerAndBuyerAndPurchaseOrder(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(null);

        SellerRatingModel sellerRatingTest = sellerRatingService.getBySellerAndBuyerAndPurchaseOrder(seller, buyer, purchaseOrder);

        assertThat(sellerRatingTest).isNull();
    }

    @Test
    @DisplayName("Return all storage SellerRating")
    void getAll_returnAllSellerRating_whenSuccess() {
        List<SellerRatingModel> sellerRatingList = new ArrayList<>();
        sellerRatingList.add(sellerRating);

        BDDMockito.when(sellerRatingRepository.findAll())
                .thenReturn(sellerRatingList);

        List<SellerRatingModel> sellerRatingTest = sellerRatingService.getAll();

        assertThat(sellerRatingTest).isNotNull();
        assertThat(sellerRatingTest).isEqualTo(sellerRatingList);
    }

    @Test
    @DisplayName("calculates the average correctly when successfully")
    void saveSellerRating_calculatesAverageCorrectly_whenSuccess() {
        Double rating1 = 4.3;
        Double rating2 = 3.2;
        Double ratingAvg = 3.75;

        sellerRating.setRating(rating1);
        SellerRatingModel sellerRating2 = new SellerRatingModel(2L, seller, purchaseOrder, buyer, rating2);
        List<SellerRatingModel> sellerRatingList = new ArrayList<>();
        sellerRatingList.add(sellerRating);
        sellerRatingList.add(sellerRating2);
        seller.setRating(ratingAvg);

        BDDMockito.when(sellerRatingRepository.findBySellerAndBuyerAndPurchaseOrder(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(null);
        BDDMockito.when(sellerRatingRepository.save(ArgumentMatchers.any()))
                .thenReturn(sellerRating);
        BDDMockito.when(iSellerService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(seller);
        BDDMockito.when(sellerRatingRepository.findBySeller(ArgumentMatchers.any()))
                .thenReturn(sellerRatingList);
        BDDMockito.when(iSellerService.update(seller, seller.getId()))
                .thenReturn(seller);

        SellerModel sellerTest = iSellerService.update(seller, seller.getId());
        SellerRatingModel sellerRatingTest = sellerRatingService.save(sellerRating);

        assertThat(sellerTest).isNotNull();
        assertEquals(sellerTest.getRating(), ratingAvg);
    }
}