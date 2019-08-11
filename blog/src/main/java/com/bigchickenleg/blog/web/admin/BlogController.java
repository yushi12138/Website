package com.bigchickenleg.blog.web.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bigchickenleg.blog.po.Blog;
import com.bigchickenleg.blog.po.Tag;
import com.bigchickenleg.blog.po.User;
import com.bigchickenleg.blog.service.BlogService;
import com.bigchickenleg.blog.service.CategoryService;
import com.bigchickenleg.blog.service.TagService;
import com.bigchickenleg.blog.vo.BlogQuery;

@Controller
@RequestMapping("admin")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/blogs")
	public String blogs(@PageableDefault(size=6,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
						BlogQuery blog, Model model) {
		model.addAttribute("page",blogService.pageBlog(pageable, blog));
		model.addAttribute("category",categoryService.listCategory());
		return "admin/blogs";
	}
	

	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size=6,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
						BlogQuery blog, Model model) {
		model.addAttribute("page",blogService.pageBlog(pageable, blog));
		model.addAttribute("category",categoryService.listCategory());
		return "admin/blogs :: blogList";
	}
	
	//get
	@GetMapping("/editor")
	public String editor(Model model) {
		model.addAttribute("blog",new Blog());
		model.addAttribute("category",categoryService.listCategory());
		model.addAttribute("tags",tagService.listTag());
		return "admin/editor";
	}
	//edit
	@GetMapping("/{id}/edit")
	public String editBlog(@PathVariable Long id, Model model) {
		Blog blog = blogService.getBlog(id);
		blog.setTagIds(formTagIds(blog));
		model.addAttribute("blog",blog);
		model.addAttribute("category",categoryService.listCategory());
		model.addAttribute("tags",tagService.listTag());
		return "admin/editor";
	}
	//get the tagIds from blog.tags
	private String formTagIds(Blog blog) {
		String ans = "";
		for(Tag tag:blog.getTags()) {
			ans = ans + tag.getId().toString()+",";
		}
		return ans.substring(0,ans.length()-1);
	}
	//post the blog
	@PostMapping("/editor")
	public String editorPost(Blog blog, HttpSession session, RedirectAttributes attributes) {
		blog.setUser((User)session.getAttribute("user"));
		blog.setCategory(categoryService.getCategory(blog.getCategory().getId()));
		blog.setTags(tagService.listTag(blog.getTagIds()));
		Blog b;
		if(blog.getId()==null)
			b = blogService.saveBlog(blog);
		else
			b = blogService.updateBlog(blog.getId(), blog);
		
		if(b==null) {
			attributes.addFlashAttribute("message", "Save Failure");
		}else {
			attributes.addFlashAttribute("message", "Save Success");
		}
		return "redirect:/admin/blogs";
	}
	//delete the blog
	@GetMapping("/{id}/delete")
	public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "Delete success");
		return "redirect:/admin/blogs";
	}
	
}
