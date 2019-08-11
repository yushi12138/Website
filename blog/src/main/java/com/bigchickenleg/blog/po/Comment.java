package com.bigchickenleg.blog.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "t_comment")
public class Comment {

	@Id
	@GeneratedValue
	private Long id;
	private String nickname;
	private String email;
	private String avatar;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@ManyToOne
	private Blog blog;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "parentComment")
	private List<Comment> childrenComment = new ArrayList<>();
	@ManyToOne
	private Comment parentComment;
	
	
	public Comment() {
		
	}

	public List<Comment> getChildrenComment() {
		return childrenComment;
	}

	public void setChildrenComment(List<Comment> childrenComment) {
		this.childrenComment = childrenComment;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", nickname=" + nickname + ", email=" + email + ", avatar=" + avatar + ", content="
				+ content + ", createTime=" + createTime + ", blog=" + blog + ", user=" + user + ", childrenComment="
				+ childrenComment + ", parentComment=" + parentComment + "]";
	}




	
	
}
