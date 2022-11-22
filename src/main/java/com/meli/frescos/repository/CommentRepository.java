package com.meli.frescos.repository;

import com.meli.frescos.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentModel, Long> {

    public List<CommentModel> findByProductIdOrderByCreatedAt(Long productId);

    public CommentModel findByBuyerIdAndProductId(Long buyerId, long productId);


}