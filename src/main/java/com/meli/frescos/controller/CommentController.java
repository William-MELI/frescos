package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.*;
import com.meli.frescos.exception.CommentNotFoundException;
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
     * @param commentPostRequest the Comment instance
     * @return a Comment instance
     */
    @PostMapping
    public ResponseEntity<CommentPostResponse> save(@Valid @RequestBody CommentPostRequest commentPostRequest) throws InvalidCommentException {
        CommentModel responseCommentModel = iCommentService.save(commentPostRequest.toModel());
        CommentPostResponse response = CommentPostResponse.toResponse(responseCommentModel);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentListResponse> getRecentComment(@PathVariable("id") Long productId) {
        List<CommentModel> responseCommentModel = iCommentService.getRecentComments(productId);
        if (responseCommentModel.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else {
            CommentListResponse response = CommentListResponse.toResponse(responseCommentModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long commentId) throws CommentNotFoundException {
        CommentModel commentModel = new CommentModel();
        commentModel.setId(commentId);
        iCommentService.delete(commentModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<CommentPatchResponse> update(@Valid @RequestBody CommentPatchRequest commentPatchRequest) throws CommentNotFoundException, InvalidCommentException {
        CommentModel responseCommentModel = iCommentService.update(commentPatchRequest.toModel());
        CommentPatchResponse response = CommentPatchResponse.toResponse(responseCommentModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
