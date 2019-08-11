package com.bigchickenleg.blog.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "t_blog")
public class Blog {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String description;
	
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private String content;
	private String indexImage;
	private String flag;
	private Integer views;
	private boolean shareInfo;
	private boolean comment;
	private boolean recommended;
	private boolean published;
	
	@Transient
	private String tagIds;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Category category;
	
	@ManyToMany(cascade = {CascadeType.PERSIST})//接连新增，add tag, save it in DB
	private List<Tag> tags = new ArrayList<>();
	
	@OneToMany(mappedBy = "blog")
	private List<Comment> comments = new ArrayList<>();
	
	public Blog() {
		super();
	}
	
	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIndexImage() {
		return indexImage;
	}
	public void setIndexImage(String indexImage) {
		this.indexImage = indexImage;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public boolean isShareInfo() {
		return shareInfo;
	}
	public void setShareInfo(boolean shareInfo) {
		this.shareInfo = shareInfo;
	}
	public boolean isComment() {
		return comment;
	}
	public void setComment(boolean comment) {
		this.comment = comment;
	}
	public boolean isRecommended() {
		return recommended;
	}
	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content
				+ ", indexImage=" + indexImage + ", flag=" + flag + ", views=" + views + ", shareInfo=" + shareInfo
				+ ", comment=" + comment + ", recommended=" + recommended + ", published=" + published + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", user=" + user + ", category=" + category + ", tags="
				+ tags + ", comments=" + comments + "]";
	}


	
	
	
	
}
