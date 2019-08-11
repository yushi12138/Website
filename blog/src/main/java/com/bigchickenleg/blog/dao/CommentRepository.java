package com.bigchickenleg.blog.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bigchickenleg.blog.po.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByBlogId(Long blogId, Sort sort);
	
	List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);


}
