package com.bigchickenleg.blog.web.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bigchickenleg.blog.po.Tag;
import com.bigchickenleg.blog.po.User;
import com.bigchickenleg.blog.service.BlogService;
import com.bigchickenleg.blog.service.CategoryService;
import com.bigchickenleg.blog.service.TagService;
import com.bigchickenleg.blog.service.UserService;
import com.bigchickenleg.blog.util.MD5Utils;
import com.bigchickenleg.blog.vo.BlogQuery;

@Controller
@RequestMapping("admin")
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping
	public String loginPage() {
		return "admin/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, 
						@RequestParam String password, 
						HttpSession session,
						RedirectAttributes attributes) {
		User user = userService.checkUser(username, MD5Utils.code(password));
		if(user != null) {
			user.setPassword(null);
			session.setAttribute("user", user);
			return "redirect:/admin/management";
		}else {
			attributes.addFlashAttribute("message","password is wrong");   //
			return "redirect:/admin";			
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/admin";
	}
	
	@GetMapping("/management")
	public String managementPage(@Qualifier("blogs") @PageableDefault(size=3,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable1,
			@Qualifier("tags") @PageableDefault(size=8,sort= {"id"},direction=Sort.Direction.DESC) Pageable pageable2,
			BlogQuery blogQuery, Model model) {
		//blogs
		model.addAttribute("page",blogService.pageBlog(pageable1, blogQuery));
		model.addAttribute("category",categoryService.listCategory());
		//tags
		model.addAttribute("tagPage",tagService.pageTag(pageable2));
		List<Tag> list = tagService.listTag();
		list.add(0,new Tag());
		model.addAttribute("list",list);
		if(!model.containsAttribute("tag")){
			model.addAttribute("tag",new Tag());
		}
		
		
		return "admin/blogs";
	}
	
	
}
