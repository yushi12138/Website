package com.bigchickenleg.blog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigchickenleg.blog.dao.CommentRepository;
import com.bigchickenleg.blog.po.Comment;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private  CommentRepository commentRepository;
	
	@Transactional
	@Override
	public List<Comment> listCommentByBlogId(Long blogId) {
		Sort sort = new Sort(Sort.Direction.ASC,"createTime");
		List<Comment> parents = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
		return rebuildComments(parents);
	}
	//rebuild the comments to form the two layer tree structure without modifying the database data structure 
	private List<Comment> rebuildComments(List<Comment> comments){
		List<Comment> rebuilded = new ArrayList<>();
		List<Comment> reply = new ArrayList<>();
		for(Comment comment : comments) {
			Comment c = new Comment();
			BeanUtils.copyProperties(comment, c);
			dfs(reply,c);
			c.setChildrenComment(new ArrayList<Comment>(reply));
			reply.clear();
			rebuilded.add(c);
		}
		return rebuilded;
		
	}
	private void dfs(List<Comment> reply, Comment parent) {
		if(parent==null) return;
		for(Comment comment: parent.getChildrenComment()) {
			if(comment!=null) {
				reply.add(comment);
				dfs(reply,comment);
			}
		}
	}
	
	@Transactional
	@Override
	public Comment saveComment(Comment comment) {
		Long parentCommentId = comment.getParentComment().getId();
		if(parentCommentId != -1){
			comment.setParentComment(commentRepository.getOne(parentCommentId));
		}else {
			comment.setParentComment(null);
		}
		comment.setCreateTime(new Date());
		return commentRepository.save(comment);
	}
	@Override
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
		
	}
	@Override
	public Comment getComment(Long id) {

		return commentRepository.getOne(id);
	}
	
	

}
