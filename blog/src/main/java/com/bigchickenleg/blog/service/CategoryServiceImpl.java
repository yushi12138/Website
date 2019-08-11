package com.bigchickenleg.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigchickenleg.blog.dao.CategoryRepository;
import com.bigchickenleg.blog.po.Category;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional
	@Override
	public Category getCategory(Long id) {
		return categoryRepository.getOne(id);
	}
	
	@Transactional
	@Override
	public List<Category> listCategory() {

		return categoryRepository.findAll();
	}
	
	

}
