package com.spring.training.boardAdvance.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ReplyDTO {

	private long replyId;
	private String writer;
	private String content;
	private String passwd;
	private Date enrollDt;
	private long boardId;
	
	public long getReplyId() {
		return replyId;
	}
	public void setReplyId(long replyId) {
		this.replyId = replyId;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Date getEnrollDt() {
		return enrollDt;
	}
	public void setEnrollDt(Date enrollDt) {
		this.enrollDt = enrollDt;
	}
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	
	@Override
	public String toString() {
		return "ReplyDTO [replyId=" + replyId + ", writer=" + writer + ", content=" + content + ", passwd=" + passwd
				+ ", enrollDt=" + enrollDt + ", boardId=" + boardId + "]";
	}
	
}
