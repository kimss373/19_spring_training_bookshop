package com.spring.training.boardAdvance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.training.boardAdvance.dto.MainBoardDTO;
import com.spring.training.boardAdvance.dto.ReplyDTO;

@Repository				
public class BoardAdvanceDAOImpl implements BoardAdvanceDAO {

	@Autowired							
	private SqlSession sqlSession;

	@Override
	public int selectOneAllBoardCnt(Map<String, String> searchCntMap) throws Exception {
		return sqlSession.selectOne("boardAdvanceMapper.selectOneAllBoardCnt" , searchCntMap);
	}

	@Override
	public List<MainBoardDTO> selectListBoard(Map<String, Object> searchMap) throws Exception {
		return sqlSession.selectList("boardAdvanceMapper.selectListBoard" , searchMap);
	}

	@Override
	public MainBoardDTO selectOneBoard(long boardId) throws Exception {
		return sqlSession.selectOne("boardAdvanceMapper.selectOneBoard" , boardId);
	}

	@Override
	public void updateReadCnt(long boardId) throws Exception {
		sqlSession.update("boardAdvanceMapper.updateReadCnt" , boardId);
	}

	@Override
	public void insertBoard(MainBoardDTO mainBoardDTO) throws Exception {
		sqlSession.insert("boardAdvanceMapper.insertBoard" , mainBoardDTO);
	}

	@Override
	public void updateBoard(MainBoardDTO mainBoardDTO) throws Exception {
		sqlSession.update("boardAdvanceMapper.updateBoard" , mainBoardDTO);
	}

	@Override
	public void deleteBoard(long boardId) throws Exception {
		sqlSession.delete("boardAdvanceMapper.deleteBoard" , boardId);
	}

	@Override
	public String selectOneValidateBoardUserCheck(long boardId) throws Exception {
		return sqlSession.selectOne("boardAdvanceMapper.selectOneValidateBoardUserCheck" , boardId);
	}

	@Override
	public void insertBoardDummy(List<MainBoardDTO> dummyBoardList) throws Exception {
		sqlSession.insert("boardAdvanceMapper.insertBoardDummy", dummyBoardList);		
	}

	@Override
	public int selectOneAllReplyCnt(long boardId) throws Exception {
		return sqlSession.selectOne("boardAdvanceMapper.selectOneAllReplyCnt" , boardId);
	}

	@Override
	public List<ReplyDTO> selectListReply(long boardId) throws Exception {
		return sqlSession.selectList("boardAdvanceMapper.selectListReply" , boardId);
	}

	@Override
	public ReplyDTO selectOneReply(long replyId) throws Exception {
		return sqlSession.selectOne("boardAdvanceMapper.selectOneReply" , replyId);
	}

	@Override
	public void insertReply(ReplyDTO replyDTO) throws Exception {
		sqlSession.insert("boardAdvanceMapper.insertReply" , replyDTO);		
	}

	@Override
	public void updateReply(ReplyDTO replyDTO) throws Exception {
		sqlSession.update("boardAdvanceMapper.updateReply" , replyDTO);
	}

	@Override
	public void deleteReply(long replyId) throws Exception {
		sqlSession.delete("boardAdvanceMapper.deleteReply" , replyId);		
	}

	@Override
	public String selectOneValidateReplyUserCheck(long replyId) throws Exception {
		return sqlSession.selectOne("boardAdvanceMapper.selectOneValidateReplyUserCheck" , replyId);
	}

	@Override
	public int selectOneTodayEnrolledBoardCnt(String today) throws Exception {
		return sqlSession.selectOne("boardAdvanceMapper.selectOneTodayEnrolledBoardCnt" , today);
	}

	@Override
	public int selectOneTodayEnrolledReplyCnt(String today) throws Exception {
		return sqlSession.selectOne("boardAdvanceMapper.selectOneTodayEnrolledReplyCnt" , today);
	}		
	

}
