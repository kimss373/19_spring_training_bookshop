package com.spring.training.boardAdvance.service;

import java.util.List;
import java.util.Map;

import com.spring.training.boardAdvance.dto.MainBoardDTO;
import com.spring.training.boardAdvance.dto.ReplyDTO;

public interface BoardAdvanceService {
	
	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap) throws Exception;
	public int getAllBoardCnt(Map<String, String> searchCntMap) throws Exception;
	public MainBoardDTO getBoardDetail(long boardId , boolean isIncreaseReadCnt) throws Exception;
	public void addBoard(MainBoardDTO mainBoardDTO) throws Exception;
	public boolean modifyBoard(MainBoardDTO mainBoardDTO) throws Exception;
	public boolean removeBoard(MainBoardDTO mainBoardDTO) throws Exception;
	public void addBoardDummy() throws Exception;
	
	public List<ReplyDTO> getReplyList(long boardId) throws Exception;
	public int getAllReplyCnt(long boardId) throws Exception;
	public ReplyDTO getReplyDetail(long replyId) throws Exception;
	public void addReply(ReplyDTO replyDTO) throws Exception;
	public boolean modifyReply(ReplyDTO replyDTO) throws Exception;
	public boolean removeReply(ReplyDTO replyDTO) throws Exception;
	
	public void getTodayEnrolledBoardAndReplyCnt() throws Exception;
	
}
