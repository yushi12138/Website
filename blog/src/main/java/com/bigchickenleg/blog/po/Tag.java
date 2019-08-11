package com.bigchickenleg.blog.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;



@Entity
@Table(name="t_tag")
public class Tag {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank(message = "Tag name shouldn't be blank!")
	private String name;
	
	@ManyToMany(mappedBy="tags")
	private List<Blog> blogs = new ArrayList<>();
	
	@OneToMany(mappedBy = "parentTag")
	private List<Tag> childrenTag = new ArrayList<>();
	@ManyToOne
	private Tag parentTag;
	
	public Tag() {
		super();
	}
	
	public List<Tag> getChildrenTag() {
		return childrenTag;
	}

	public void setChildrenTag(List<Tag> childrenTag) {
		this.childrenTag = childrenTag;
	}

	public Tag getParentTag() {
		return parentTag;
	}

	public void setParentTag(Tag parentTag) {
		this.parentTag = parentTag;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + "]";
	}
	
}
