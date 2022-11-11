package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.repository.BuyerRepository;
import com.meli.frescos.repository.PurchaseOrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final BuyerService buyerService;

    private final OrderProductService orderProductService;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, BuyerService buyerService, OrderProductService orderProductService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.buyerService = buyerService;
        this.orderProductService = orderProductService;
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
    public PurchaseOrderModel updateStatus(Long id, String orderStatus) {
        Optional<PurchaseOrderModel> findbyIdPurchaceOrder = purchaseOrderRepository.findById(id);

        if (findbyIdPurchaceOrder.isEmpty()) {
            throw new NullPointerException("PurchaceOrder_id not found");
        }
        findbyIdPurchaceOrder.get().setOrderStatus(orderStatus);

        PurchaseOrderModel result = purchaseOrderRepository.save(findbyIdPurchaceOrder.get());

        return result;
    }
}
