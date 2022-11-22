package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.CommentRequest;
import com.meli.frescos.controller.dto.CommentResponse;
import com.meli.frescos.exception.InvalidCommentException;
import com.meli.frescos.model.CommentModel;
import com.meli.frescos.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
     * @param commentRequest the Comment instance
     * @return a Comment instance
     */
    @PostMapping
    public ResponseEntity<CommentResponse> save(@Valid @RequestBody CommentRequest commentRequest) throws InvalidCommentException {
        CommentModel responseCommentModel = iCommentService.save(commentRequest.toModel());
        CommentResponse response = CommentResponse.toResponse(responseCommentModel);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getRecentComment(@PathVariable("id") Long productId) {
        List<CommentModel> responseCommentModel = iCommentService.getRecentComments(productId);
        if (responseCommentModel.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else {
            CommentResponse response = CommentResponse.toResponse(responseCommentModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }
}
