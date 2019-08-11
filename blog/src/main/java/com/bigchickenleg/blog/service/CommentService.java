package com.bigchickenleg.blog.service;

import java.util.List;

import com.bigchickenleg.blog.po.Comment;

public interface CommentService {
	
	List<Comment> listCommentByBlogId(Long blogId);
	
	Comment saveComment(Comment comment);
	
	void deleteComment(Long id);
	
	Comment getComment(Long id);
	
}
