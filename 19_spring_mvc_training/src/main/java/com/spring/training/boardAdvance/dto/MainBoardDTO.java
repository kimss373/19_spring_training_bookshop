package com.spring.training.boardAdvance.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class MainBoardDTO {

	private long boardId;
	private String writer;
	private String subject;
	private String content;
	private String passwd;
	private int readCnt;
	private Date enrollDt;
	
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public int getReadCnt() {
		return readCnt;
	}
	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}
	public Date getEnrollDt() {
		return enrollDt;
	}
	public void setEnrollDt(Date enrollDt) {
		this.enrollDt = enrollDt;
	}
	
	@Override
	public String toString() {
		return "MainBoardDTO [boardId=" + boardId + ", writer=" + writer + ", subject=" + subject + ", content="
				+ content + ", passwd=" + passwd + ", readCnt=" + readCnt + ", enrollDt=" + enrollDt + "]";
	}
	
}
