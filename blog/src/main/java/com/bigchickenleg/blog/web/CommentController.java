package com.bigchickenleg.blog.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bigchickenleg.blog.po.Comment;
import com.bigchickenleg.blog.po.User;
import com.bigchickenleg.blog.service.BlogService;
import com.bigchickenleg.blog.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BlogService blogService;
	
	//set in application.yml
	@Value("${comment.avatar}")
	private String avatar;
	
	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId, Model model,HttpSession session) {
		session.setAttribute("blogId",blogId);
		model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
		return "post :: commentList";
	}
	
	@PostMapping("/comments")
	public String postComment(Comment comment, HttpSession session) {
		Long blogId = comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogId));
		
		User user = (User)session.getAttribute("user");
		if(user !=null) {
			comment.setNickname(user.getNickname());
			comment.setEmail(user.getEmail());
			comment.setAvatar(user.getAvatar());
			comment.setUser(user);
		}else {
			comment.setAvatar(avatar);
		}
			
		commentService.saveComment(comment);
		return "redirect:/comments/"+blogId;
	}
	
	@PostMapping("/comments/delete")
	public String delete(Long id,Model model,HttpSession session) {
		if(commentService.getComment(id).getChildrenComment().size()==0) {
			commentService.deleteComment(id);	
			
		}
		Long blogId =(Long) session.getAttribute("blogId");
		return "redirect:/comments/"+blogId.toString();
	}
}
