package com.bigchickenleg.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bigchickenleg.blog.po.Tag;

public interface TagService {

	Tag saveTag(Tag tag);
	
	void deleteTag(Long id);
	
	Tag update(Long id, Tag tag);
	
	Tag getTag(Long id);
	
	Page<Tag> pageTag(Pageable pageable);
	
	List<Tag> listTag();
	
	List<Tag> listTagTop(Integer size);
	
	Tag getTagByName(String name);
	
	List<Tag> listTag(String ids);
}
