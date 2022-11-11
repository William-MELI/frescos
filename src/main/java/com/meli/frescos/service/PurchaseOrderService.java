package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final BuyerService buyerService;

    private final OrderProductService orderProductService;

    private final BatchStockService batchStockService;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, BuyerService buyerService, OrderProductService orderProductService,
                                BatchStockService batchStockService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.buyerService = buyerService;
        this.orderProductService = orderProductService;
        this.batchStockService = batchStockService;
    }

    @Override
    public PurchaseOrderModel save(PurchaseOrderRequest purchaseOrderRequest) {
        BuyerModel finBuyer = buyerService.getById(purchaseOrderRequest.getBuyer());
        PurchaseOrderModel purchase = new PurchaseOrderModel();
        purchase.setBuyer(finBuyer);
        purchase.setOrderStatus(purchaseOrderRequest.getOrderStatus());
        purchase.setDate(purchaseOrderRequest.getDate());

        return purchaseOrderRepository.save(purchase);
    }

    private boolean stockAvailable(Long productId, int desiredQuantity) {

        LocalDate dateRequirement = LocalDate.now().plusWeeks(3);

        List<BatchStockModel> batchStockList = this.batchStockService.findValidProductsByDueDate(productId, dateRequirement);

        int availableQuantity = batchStockList.stream().mapToInt(BatchStockModel::getQuantity).sum();

        return desiredQuantity <= availableQuantity;
    }

    public BigDecimal savePurchaseGetPrice(PurchaseOrderRequest purchaseOrderRequest) {
        PurchaseOrderModel purchaseOrderModel = save(purchaseOrderRequest);

        List<OrderProductsModel> orderProductsModels = new ArrayList<>();

        BigDecimal totalPrice = new BigDecimal(0);

        purchaseOrderRequest.getProducts().forEach(p -> orderProductsModels.add(orderProductService.save(new OrderProductsRequest(
                p.getProductModel(),
                p.getQuantity(),
                purchaseOrderModel.getId()
        ))));
        for (OrderProductsModel orderProductsModel : orderProductsModels) {
            totalPrice = totalPrice.add(orderProductsModel.getProductModel().getPrice().multiply(BigDecimal.valueOf(orderProductsModel.getQuantity())));
        }
        return totalPrice;
    }

    @Override
    public List<PurchaseOrderModel> getAll() {
        return null;
    }

    @Override
    public void updateStatus(Long id, String orderStatus) throws Exception {
        PurchaseOrderModel findbyIdPurchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new Exception("Purchase_id not found"));

        findbyIdPurchaseOrder.setOrderStatus(orderStatus);
        purchaseOrderRepository.save(findbyIdPurchaseOrder);
    }
}
