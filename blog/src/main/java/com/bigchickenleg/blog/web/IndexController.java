package com.bigchickenleg.blog.web;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bigchickenleg.blog.exceptions.NotFoundException;
import com.bigchickenleg.blog.service.BlogService;
import com.bigchickenleg.blog.service.CategoryService;
import com.bigchickenleg.blog.service.CommentService;
import com.bigchickenleg.blog.service.TagService;
import com.bigchickenleg.blog.vo.BlogQuery;
import com.bigchickenleg.blog.po.Blog;
import com.bigchickenleg.blog.po.Tag;

@Controller
public class IndexController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired 
	CommentService commentService;
	
	//------------------------------------index---------------------------------------------
	@RequestMapping("/")
	public String getIndex(@Qualifier("sharings") @PageableDefault(size=6,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable sharingPageable,
							@Qualifier("projects") @PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable projectPageable,
							Model model, HttpSession session) {
			Integer tagsNum = 9;
			BlogQuery sharingsBQ = new BlogQuery(), projectsBQ = new BlogQuery();
			setBlogQuery(sharingsBQ,new Long(1),true,true);
			setBlogQuery(projectsBQ,new Long(2),true,true);

			model.addAttribute("sharings",blogService.pageBlog(sharingPageable, sharingsBQ));
			model.addAttribute("projects",blogService.pageBlog(projectPageable, projectsBQ));
			model.addAttribute("tags",tagService.listTagTop(tagsNum));
			return "index";
	}
	private void setBlogQuery(BlogQuery bq,Long id, boolean isRecommended, boolean isPublished) {
		bq.setCategoryId(id);
		bq.setRecommended(isRecommended);
		bq.setPublished(isPublished);	
	}
	
	//-------------------------------------sharings---------------------------------------------
	@GetMapping("/sharings")
	public String getSharings(@PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
								Model model) {
		Integer tagsNum = 9;
		BlogQuery sharingBQ = new BlogQuery();
		BlogQuery projectBQ = new BlogQuery();
		//-------------------------content-----------------------------
		setBlogQuery(sharingBQ,new Long(1),false,true);
		model.addAttribute("sharings",blogService.pageBlog(pageable, sharingBQ));
		//-----------------summary-data---------------------------------
		model.addAttribute("tags", tagService.listTagTop(tagsNum));
		model.addAttribute("tagNum", tagService.listTag().size());
		setBlogQuery(projectBQ,new Long(2),false,true);
		model.addAttribute("projectNum",blogService.pageBlog(pageable, projectBQ).getNumberOfElements());
		//-----------------------recent-post/recommended-----------------------------
		setBlogQuery(sharingBQ,new Long(1),true,true);
		model.addAttribute("recommended",blogService.pageBlog(pageable, sharingBQ));
		
		return "sharings";
	}
	//--------------------------sharings-ajax-post-load-page------------------------------
	@PostMapping("sharings/page")
	public String loadSharingsPage(@PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
							Model model) {
		Integer tagsNum = 9;

		BlogQuery sharingBQ = new BlogQuery();
		setBlogQuery(sharingBQ,new Long(1),false,true);
		model.addAttribute("sharings",blogService.pageBlog(pageable, sharingBQ));
		model.addAttribute("tags", tagService.listTagTop(tagsNum));
		return "sharings :: sharingsFragment";		
		
	}
	
	//---------------------------------------projects-------------------------------------------
	@GetMapping("/projects")
	public String getProjects(@PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
								Model model) {
		Integer tagsNum = 9;
		BlogQuery sharingBQ = new BlogQuery();
		BlogQuery projectBQ = new BlogQuery();
		//-------------------------content-----------------------------
		setBlogQuery(projectBQ,new Long(2),false,true);
		model.addAttribute("projects",blogService.pageBlog(pageable, projectBQ));
		//-----------------summary-data---------------------------------
		model.addAttribute("tags", tagService.listTagTop(tagsNum));
		model.addAttribute("tagNum", tagService.listTag().size());
		setBlogQuery(sharingBQ,new Long(1),false,true);
		model.addAttribute("sharingNum",blogService.pageBlog(pageable, sharingBQ).getNumberOfElements());
		//-----------------------recent-post/recommended-----------------------------
		setBlogQuery(projectBQ,new Long(2),true,true);
		model.addAttribute("recommended",blogService.pageBlog(pageable, projectBQ));
		
		return "projects";
	}
	//---------------------------ajax-post-load-page------------------------------
	@PostMapping("projects/page")
	public String loadProjectsPage(@PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
							Model model) {
		Integer tagsNum = 9;

		BlogQuery projectBQ = new BlogQuery();
		setBlogQuery(projectBQ,new Long(2),false,true);
		model.addAttribute("projects",blogService.pageBlog(pageable, projectBQ));
		model.addAttribute("tags", tagService.listTagTop(tagsNum));
		return "projects :: projectsFragment";		
		
	}

	
	
	//---------------------------------------tags--------------------------------------------
	@GetMapping("/tags/{id}")
	public String tags(@PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,Model model,@PathVariable Long id) {
		List<Tag> tags = tagService.listTagTop(10000);
		if(id==0) {
			id = tags.get(0).getId();
		}
		model.addAttribute("tags", tags);
		model.addAttribute("tag", tagService.getTag(id));
		model.addAttribute("blogs", blogService.pageBlog(pageable, id));
		

		//-----------------summary-data---------------------------------
		BlogQuery sharingBQ = new BlogQuery();
		BlogQuery projectBQ = new BlogQuery();
		setBlogQuery(sharingBQ,new Long(1),false,true);
		model.addAttribute("sharingNum",blogService.pageBlog(pageable, sharingBQ).getNumberOfElements());
		setBlogQuery(projectBQ,new Long(2),false,true);
		model.addAttribute("projectNum",blogService.pageBlog(pageable, projectBQ).getNumberOfElements());
		return "tags";
	}
	//--------------------------tags-ajax-post-load-page------------------------------

	@PostMapping("/tags/page")
	public String tagsPage(@PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
							Model model, Long id) {
		List<Tag> tags = tagService.listTagTop(10000);
		if(id==0) {
			id = tags.get(0).getId();
		}
		model.addAttribute("tags", tags);
		model.addAttribute("tag", tagService.getTag(id));
		model.addAttribute("blogs", blogService.pageBlog(pageable, id));
		return "tags :: tagsFragment";
	}

	//---------------------------------------post--------------------------------------------
	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Long id, Model model) {
		Blog blog = blogService.getAndConvertBlog(id);
		model.addAttribute("blog", blog);
		model.addAttribute("comments", commentService.listCommentByBlogId(blog.getId()));
		return "post";
	}
	//---------------------------------------aboutMe--------------------------------------------
	@GetMapping("/aboutMe")
	public String aboutMe(@PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
							Model model) {
		model.addAttribute("blogs", blogService.pageBlog(pageable));
		return "aboutMe";
	}

	

}
