package com.spring.training.boardAdvance.dao;

import java.util.List;
import java.util.Map;

import com.spring.training.boardAdvance.dto.MainBoardDTO;
import com.spring.training.boardAdvance.dto.ReplyDTO;

public interface BoardAdvanceDAO {

	public int selectOneAllBoardCnt(Map<String, String> searchCntMap) throws Exception;
	public List<MainBoardDTO> selectListBoard(Map<String, Object> searchMap) throws Exception;
	public MainBoardDTO selectOneBoard(long boardId) throws Exception;
	public void updateReadCnt(long boardId) throws Exception;
	public void insertBoard(MainBoardDTO mainBoardDTO) throws Exception;
	public void updateBoard(MainBoardDTO mainBoardDTO) throws Exception;
	public void deleteBoard(long boardId) throws Exception;
	public String selectOneValidateBoardUserCheck(long boardId) throws Exception;
	public void insertBoardDummy(List<MainBoardDTO> dummyBoardList) throws Exception;

	public int selectOneAllReplyCnt(long boardId) throws Exception;
	public List<ReplyDTO> selectListReply(long boardId) throws Exception;
	public ReplyDTO selectOneReply(long replyId) throws Exception;
	public void insertReply(ReplyDTO replyDTO) throws Exception;
	public void updateReply(ReplyDTO replyDTO) throws Exception;
	public void deleteReply(long replyId) throws Exception;
	public String selectOneValidateReplyUserCheck(long replyId) throws Exception;
	
	public int selectOneTodayEnrolledBoardCnt(String today) throws Exception;
	public int selectOneTodayEnrolledReplyCnt(String today) throws Exception;
	
}
