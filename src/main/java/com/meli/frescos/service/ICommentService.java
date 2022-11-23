package com.meli.frescos.service;

import com.meli.frescos.exception.CommentNotFoundException;
import com.meli.frescos.exception.InvalidCommentException;
import com.meli.frescos.model.CommentModel;

import java.util.List;

public interface ICommentService {

    CommentModel save(CommentModel commentModel) throws InvalidCommentException;

    CommentModel update(CommentModel commentModel) throws InvalidCommentException, CommentNotFoundException;

    List<CommentModel> getRecentComments(Long productId);

    void delete(CommentModel commentModel) throws CommentNotFoundException;
}
