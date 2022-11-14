package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.exception.OrderProductIsInvalidException;
import com.meli.frescos.exception.PurchaseOrderByIdNotFoundException;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        purchase.setOrderStatus(OrderStatusEnum.valueOf(purchaseOrderRequest.getOrderStatus()));
        purchase.setDate(purchaseOrderRequest.getDate());

        return purchaseOrderRepository.save(purchase);
    }

    private boolean stockAvailable(Long productId, int desiredQuantity) {

        LocalDate dateRequirement = LocalDate.now().plusWeeks(3);

        List<BatchStockModel> batchStockList = this.batchStockService.findValidProductsByDueDate(productId, dateRequirement);

        int availableQuantity = batchStockList.stream().mapToInt(BatchStockModel::getQuantity).sum();

        return desiredQuantity <= availableQuantity;
    }

    private boolean verifyOrderIsValid(List<OrderProductsRequest> orderProductsList) throws OrderProductIsInvalidException {
        Set<Long> productIdListException = new HashSet<>();
        boolean isFailure = false;
        for (OrderProductsRequest orderProduct : orderProductsList) {
            boolean response = stockAvailable(orderProduct.getProductModel(), orderProduct.getQuantity());
            if (!response) {
                isFailure = true;
                productIdListException.add(orderProduct.getProductModel());
            }
        }
        if (isFailure) {
            String auxMessage = productIdListException.stream().map(String::valueOf).collect(Collectors.joining(","));

            String exceptionMessage = String.format("Pedido de compra inválido. Produtos com ID %s em quantidades insuficiente", auxMessage);

            throw new OrderProductIsInvalidException(exceptionMessage);
        }

        return true;
    }

    public BigDecimal savePurchaseGetPrice(PurchaseOrderRequest purchaseOrderRequest) throws Exception {
        boolean isOrderValid = (verifyOrderIsValid(purchaseOrderRequest.getProducts()));
        if (isOrderValid) {


            PurchaseOrderModel purchaseOrderModel = save(purchaseOrderRequest);

            List<OrderProductsModel> orderProductsModels = new ArrayList<>();

            BigDecimal totalPrice = new BigDecimal(0);

            purchaseOrderRequest.getProducts().forEach(p -> orderProductsModels.add(orderProductService.save(
                    new OrderProductsRequest(
                            p.getProductModel(),
                            p.getQuantity(),
                            purchaseOrderModel.getId()
                    ))));

            for (OrderProductsModel orderProductsModel : orderProductsModels) {
                totalPrice = totalPrice.add(orderProductsModel.getProductModel().getPrice().multiply(BigDecimal.valueOf(orderProductsModel.getQuantity())));
            }
            return totalPrice;

        } else {
            throw new OrderProductIsInvalidException("Pedido de compra inválido");
        }
    }

    public PurchaseOrderModel getById(Long purchaseId) {
        return purchaseOrderRepository.findById(purchaseId).orElseThrow(() -> new PurchaseOrderByIdNotFoundException(purchaseId));
    }

    @Override
    public List<PurchaseOrderModel> getAll() {
        return null;
    }

    @Override
    public void updateStatus(Long id, OrderStatusEnum orderStatus) throws Exception {
        List<OrderProductsModel> orderProductsList = orderProductService.getByPurchaseId(id);
        List<OrderProductsRequest> orderProductsRequestList = new ArrayList<>();
        orderProductsList.forEach(item -> orderProductsRequestList.add(OrderProductsRequest.builder()
                        .productModel(item.getProductModel().getId())
                        .quantity(item.getQuantity())
                        .purchaseOrderModel(item.getPurchaseOrderModel().getId())
                .build()));

        verifyOrderIsValid(orderProductsRequestList);

        PurchaseOrderModel findbyIdPurchaseOrder = getById(id);
        findbyIdPurchaseOrder.setOrderStatus(orderStatus);
        findbyIdPurchaseOrder = purchaseOrderRepository.save(findbyIdPurchaseOrder);

        batchStockService.consumeBatchStockOnPurchase(findbyIdPurchaseOrder);
    }
}
