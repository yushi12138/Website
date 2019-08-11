package com.bigchickenleg.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bigchickenleg.blog.po.Blog;
import com.bigchickenleg.blog.vo.BlogQuery;

public interface BlogService {

	Blog saveBlog(Blog blog);
	
	void deleteBlog(Long id);
	
	Blog updateBlog(Long id, Blog blog);
	
	Blog getBlog(Long id);
	
	//get blog and convert markdown to html
	Blog getAndConvertBlog(Long id);
	
	Page<Blog> pageBlog(Pageable pageable, BlogQuery blog);
	//tag display
	Page<Blog> pageBlog(Pageable pageable,Long tagId);

	Page<Blog> pageBlog(Pageable pageable);
	
	List<Blog> listBlog();
	
	List<Blog> listRecommendedBlogTop(Integer size);
	
	//search 
	Page<Blog> listBlog(String query, Pageable pageable);
	
	

	
}
