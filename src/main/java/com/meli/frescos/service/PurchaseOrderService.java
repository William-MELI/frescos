package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.repository.BatchStockRepository;
import com.meli.frescos.repository.BuyerRepository;
import com.meli.frescos.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final BuyerRepository buyerRepository;

    private final OrderProductService orderProductService;

    private final BatchStockRepository batchStockRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, BuyerRepository buyerRepository, OrderProductService orderProductService, BatchStockRepository batchStockRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.buyerRepository = buyerRepository;
        this.orderProductService = orderProductService;
        this.batchStockRepository = batchStockRepository;
    }

    @Override
    public PurchaseOrderModel save(PurchaseOrderRequest purchaseOrderRequest) {
        BuyerModel finBuyer = buyerRepository.getReferenceById(purchaseOrderRequest.getBuyer());

        PurchaseOrderModel purchase = new PurchaseOrderModel();

        purchase.setBuyer(finBuyer);
        purchase.setOrderStatus(purchaseOrderRequest.getOrderStatus());
        purchase.setDate(purchaseOrderRequest.getDate());

<<<<<<< HEAD

        purchaseOrderRequest.getProducts().stream().map(p -> {
            stockAvailable(p.getProductModel(), p.getQuantity());
//            return orderProductService.save(new OrderProductsRequest(
//                            p.getProductModel(),
//                            p.getQuantity()
//                    )
//            )
            return null;
        });

=======
        PurchaseOrderModel result = purchaseOrderRepository.save(purchase);

        purchaseOrderRequest.getProducts().stream().map(p -> orderProductService.save(new OrderProductsRequest(
                p.getProductModel(),
                p.getQuantity(),
                result.getId()
        )));
>>>>>>> 03f304a4b911ea032d78f8860c53a9927b91d93f

        return result;
    }

    private boolean stockAvailable(Long productId, int quantity) {
        LocalDate dateRequirement = LocalDate.now().minusWeeks(3);

        List<BatchStockModel> batchStockList = this.batchStockRepository.findBatchStockModelsByProductIdAndDueDateGreaterThanEqual(productId, dateRequirement);

        int availableQuantity = batchStockList.stream().mapToInt(BatchStockModel::getQuantity).sum();

        return availableQuantity <= quantity;
    }

    @Override
    public List<PurchaseOrderModel> findAll() {
        return null;
    }
}
