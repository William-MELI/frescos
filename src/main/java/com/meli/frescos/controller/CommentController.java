package com.meli.frescos.controller;

import com.meli.frescos.exception.InvalidCommentException;
import com.meli.frescos.model.CommentModel;
import com.meli.frescos.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * All endpoints related to Seller
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    ICommentService iCommentService;

    /**
     * Creates a new Comment instance.
     * Returns 201 CREATED when operation is success
     *
     * @param CommentModel the Comment instance
     * @return a Comment instance
     */
    @PostMapping
    public ResponseEntity<CommentModel> save(@RequestBody CommentModel commentModel) throws InvalidCommentException {
        return new ResponseEntity<>(iCommentService.save(commentModel), HttpStatus.CREATED);
    }
}
