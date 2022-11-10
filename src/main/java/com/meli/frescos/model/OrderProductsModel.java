package com.meli.frescos.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_products")
public class OrderProductsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel productModel;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrderModel purchaseOrderModel;

    public OrderProductsModel(ProductModel productModel, int quantity, PurchaseOrderModel purchaseOrderModel) {
        this.productModel = productModel;
        this.quantity = quantity;
        this.purchaseOrderModel = purchaseOrderModel;
    }
}
