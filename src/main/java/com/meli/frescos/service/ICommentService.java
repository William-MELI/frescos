package com.meli.frescos.service;

import com.meli.frescos.exception.InvalidCommentException;
import com.meli.frescos.model.CommentModel;

import java.util.List;

public interface ICommentService {

    CommentModel save(CommentModel commentModel) throws InvalidCommentException;

    List<CommentModel> getRecentComments(Long productId);
}
