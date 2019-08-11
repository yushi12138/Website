package com.bigchickenleg.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bigchickenleg.blog.service.BlogService;
import com.bigchickenleg.blog.service.TagService;
import com.bigchickenleg.blog.vo.BlogQuery;

@Controller
public class SearchController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private TagService tagService;
	//-----------------------------------------search--------------------------------------------
	
	@PostMapping("/search")
	public String search(@PageableDefault(size = 4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
							Model model,@RequestParam String query) {
		//sql query:
		//select * from t_blog where title like '%content%'
		model.addAttribute("blogs", blogService.listBlog("%"+query+"%",pageable));
		model.addAttribute("query",	query);
		
		//-----------------summary-data---------------------------------
		BlogQuery sharingBQ = new BlogQuery();
		BlogQuery projectBQ = new BlogQuery();
		setBlogQuery(sharingBQ,new Long(1),false,true);
		model.addAttribute("sharingNum",blogService.pageBlog(pageable, sharingBQ).getNumberOfElements());
		setBlogQuery(projectBQ,new Long(2),false,true);
		model.addAttribute("projectNum",blogService.pageBlog(pageable, projectBQ).getNumberOfElements());
		
		//--------------------tag---------------------------------------
		Integer tagsNum = 9;
		model.addAttribute("tags",tagService.listTagTop(tagsNum));
		//-----------------------recent-post/recommended-----------------------------
		setBlogQuery(projectBQ,new Long(2),true,true);
		model.addAttribute("recommended",blogService.pageBlog(pageable, projectBQ));
	
		
		return "search";
	}
	
	private void setBlogQuery(BlogQuery bq,Long id, boolean isRecommended, boolean isPublished) {
		bq.setCategoryId(id);
		bq.setRecommended(isRecommended);
		bq.setPublished(isPublished);	
	}
	
	
	//--------------------------search-ajax-post-load-page------------------------------
	@PostMapping("/search/page")
	public String loadSearchPage(@PageableDefault(size=4,sort= {"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,
							Model model,String query) {
		model.addAttribute("blogs", blogService.listBlog("%"+query+"%",pageable));
		model.addAttribute("query",	query);

		return "search :: searchFragment";		
		
	}
	
	
	
	
}
