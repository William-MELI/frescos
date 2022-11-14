package com.meli.frescos.model;

import lombok.*;
import javax.persistence.*;

/**
 * Main OrderProducts Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_products")
public class OrderProductsModel {

    /**
     * OrderProducts ID.
     * Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ProductModel reference.
     * It is an N-1 relationship
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel productModel;

    /**
     * OrderProduct quantity.
     * Not nullable.
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * PurchaseOrder reference.
     * It is an N-1 relationship
     */
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrderModel purchaseOrderModel;

    public OrderProductsModel(ProductModel productModel, int quantity, PurchaseOrderModel purchaseOrderModel) {
        this.productModel = productModel;
        this.quantity = quantity;
        this.purchaseOrderModel = purchaseOrderModel;
    }
}
