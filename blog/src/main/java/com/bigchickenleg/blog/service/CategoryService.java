package com.bigchickenleg.blog.service;

import java.util.List;
import com.bigchickenleg.blog.po.Category;

public interface CategoryService {
	
	Category getCategory(Long id);
	
	List<Category> listCategory();
	
}
