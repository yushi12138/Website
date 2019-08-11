package com.bigchickenleg.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigchickenleg.blog.po.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{

}
