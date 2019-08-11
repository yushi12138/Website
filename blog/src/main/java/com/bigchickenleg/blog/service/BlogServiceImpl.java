package com.bigchickenleg.blog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigchickenleg.blog.dao.BlogRepository;
import com.bigchickenleg.blog.exceptions.NotFoundException;
import com.bigchickenleg.blog.po.Blog;
import com.bigchickenleg.blog.po.Category;
import com.bigchickenleg.blog.po.Tag;
import com.bigchickenleg.blog.util.MarkdownUtils;
import com.bigchickenleg.blog.util.MyBeanUtils;
import com.bigchickenleg.blog.vo.BlogQuery;

@Service
public class BlogServiceImpl implements BlogService{

	@Autowired
	private BlogRepository blogRepository;
	
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		blog.setCreateTime(new Date());
		blog.setViews(0);
		blog.setUpdateTime(new Date());
		Blog b = blogRepository.save(blog);
		return b;
	}

	@Transactional
	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}
	
	@Transactional
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b = blogRepository.getOne(id);
		if(b==null) {
			throw new NotFoundException("Blog not found!");
		}
		BeanUtils.copyProperties(blog, b,MyBeanUtils.getNullPropertyName(blog));
		b.setUpdateTime(new Date());
		return blogRepository.save(b);
	}

	@Transactional
	@Override
	public Blog getBlog(Long id) {
		
		return blogRepository.getOne(id);
	}

	//for admin-blogs
	@Transactional
	@Override
	public Page<Blog> pageBlog(Pageable pageable, BlogQuery blog) {

		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {			
				List<Predicate> predicates = new ArrayList<>();			
				if(blog.getTitle() != null && ! "".equals(blog.getTitle())) {
					predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
				}
				if(blog.getCategoryId()!=null) {
					predicates.add(criteriaBuilder.equal(root.<Category>get("category").get("id"),blog.getCategoryId()));
				}
				if(blog.isRecommended()) {
					predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommended"), blog.isRecommended()));
				}
				if(blog.isPublished()) {
					predicates.add(criteriaBuilder.equal(root.<Boolean>get("published"), blog.isPublished()));
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
			
		}, pageable);
		
	}
	
	@Transactional
	@Override
	public Page<Blog> pageBlog(Pageable pageable, Long tagId) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				//Join is a raw type. References to generic type Join<Z,X> should be parameterized
				Join<Blog, Tag> join = root.join("tags");
				return criteriaBuilder.equal(join.get("id"),tagId);
			}
			
		}, pageable);
	}

	
	@Transactional
	@Override
	public List<Blog> listRecommendedBlogTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
		Pageable pageable = PageRequest.of(0, size, sort);
		return blogRepository.findTop(pageable);
	}


	//for index display blogs
	@Transactional
	@Override
	public Page<Blog> pageBlog(Pageable pageable) {
		return blogRepository.findAll(pageable); 
	}

	@Transactional
	@Override
	public List<Blog> listBlog() {
		List<Blog> list = blogRepository.findAll();
		return list;
	}
	
	@Transactional
	@Override
	public Blog getAndConvertBlog(Long id) {
		Blog blog = blogRepository.getOne(id);
		if(blog == null) {
			throw new NotFoundException("Can't find the blog!");
		}
		Blog newBlog = new Blog();
		BeanUtils.copyProperties(blog, newBlog);
		String content = blog.getContent();
		newBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
		//views+1
		blogRepository.updateViews(id);
		return newBlog;
	}

	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {

		return blogRepository.findByQuery(query, pageable);
	}


	
}
