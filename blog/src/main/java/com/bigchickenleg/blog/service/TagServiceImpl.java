package com.bigchickenleg.blog.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigchickenleg.blog.dao.TagRepository;
import com.bigchickenleg.blog.exceptions.NotFoundException;
import com.bigchickenleg.blog.po.Tag;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private TagRepository tagRepository;
	
	@Override
	public List<Tag> listTagTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
		Pageable pageable = PageRequest.of(0, size, sort);   //instead of use: new PagaRequest();
		return tagRepository.findTop(pageable);
	}

	@Transactional
	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}
	
	@Transactional
	@Override
	public void deleteTag(Long id) {

		tagRepository.deleteById(id);
	}
	
	@Override
	public Tag getTagByName(String name) {

		return tagRepository.findByName(name);
	}

	@Transactional
	@Override
	public Tag update(Long id, Tag tag) {
		Tag t = tagRepository.getOne(id);
		if(t==null) {
			throw new NotFoundException("Not Found");
		}
		BeanUtils.copyProperties(tag, t);
		return tagRepository.save(t);
	}
	
	@Transactional
	@Override
	public Tag getTag(Long id) {
		return tagRepository.getOne(id);
	}
	
	@Transactional
	@Override
	public Page<Tag> pageTag(Pageable pageable) {
		Page<Tag> page = tagRepository.findAll(pageable);
		return page;
	}
	
	@Transactional
	@Override
	public List<Tag> listTag() {
		List<Tag> list = tagRepository.findAll();
		return list;
	}
	
	@Transactional
	@Override
	public List<Tag> listTag(String ids) {

		return tagRepository.findAllById(toList(ids));  //findAllById(Iterable iter)
	}
	//convert String ids:"1,2,3,4" to iterable
	private List<Long> toList(String ids){
		List<Long> list = new ArrayList<>();
		if(ids!=null && !"".equals(ids)) {
			String[] id = ids.split(",");
			for(String s:id) {
				list.add(new Long(s));
			}
		}
		return list;
	}
	
}
