package com.spring.training.boardAdvance.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.training.boardAdvance.dao.BoardAdvanceDAO;
import com.spring.training.boardAdvance.dto.MainBoardDTO;
import com.spring.training.boardAdvance.dto.ReplyDTO;

@Service		
public class BoardAdvanceServiceImpl implements BoardAdvanceService {

	@Autowired				
	private BoardAdvanceDAO boardAdvanceDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static Logger logger = LoggerFactory.getLogger(BoardAdvanceServiceImpl.class);
	
	@Override
	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap) throws Exception{
		return boardAdvanceDAO.selectListBoard(searchMap);
	}
	 
	@Override
	public int getAllBoardCnt(Map<String, String> searchCntMap) throws Exception {
		return boardAdvanceDAO.selectOneAllBoardCnt(searchCntMap);
	}

	@Override
	@Transactional
	public MainBoardDTO getBoardDetail(long boardId, boolean isIncreaseReadCnt) throws Exception {
		if(isIncreaseReadCnt) {
			boardAdvanceDAO.updateReadCnt(boardId);
		}
		return  boardAdvanceDAO.selectOneBoard(boardId);
	}	
	
	@Override
	public void addBoard(MainBoardDTO mainBoardDTO) throws Exception {
		mainBoardDTO.setPasswd(bCryptPasswordEncoder.encode(mainBoardDTO.getPasswd()));
		boardAdvanceDAO.insertBoard(mainBoardDTO);
	}
	
	@Override
	public boolean modifyBoard(MainBoardDTO mainBoardDTO) throws Exception {
		boolean isUpdate = false;
		if (bCryptPasswordEncoder.matches(mainBoardDTO.getPasswd() , boardAdvanceDAO.selectOneValidateBoardUserCheck(mainBoardDTO.getBoardId()))) {
			isUpdate = true;
			boardAdvanceDAO.updateBoard(mainBoardDTO);
		}
		return isUpdate;
	}

	@Override
	public boolean removeBoard(MainBoardDTO mainBoardDTO) throws Exception {
		boolean isDelete = false;
		if (bCryptPasswordEncoder.matches(mainBoardDTO.getPasswd() , boardAdvanceDAO.selectOneValidateBoardUserCheck(mainBoardDTO.getBoardId()))) {
			boardAdvanceDAO.deleteBoard(mainBoardDTO.getBoardId());
			isDelete = true;
		}
		return isDelete;
	}

	@Override
	public void addBoardDummy() throws Exception {
		
		Random ran = new Random();
		
		List<MainBoardDTO> dummyBoardList = new ArrayList<MainBoardDTO>();
		
		String[] word = {"가","나","다","라","마","바","사","아","자","차","카","타","파","하"};
		
		for (int i = 1001; i < 1301; i++) {
			
			String writer    = "";
			String subject   = "";
			String content   = "";
			for (int j = 0; j < 7; j++) {
				writer  += word[ran.nextInt(word.length)];
				subject += word[ran.nextInt(word.length)];
				content += word[ran.nextInt(word.length)];
			}
			
			MainBoardDTO temp = new MainBoardDTO();
			temp.setBoardId(i);		
			temp.setWriter(writer);
			temp.setSubject(subject);
			temp.setPasswd(bCryptPasswordEncoder.encode("1111"));
			temp.setContent(content);
			
			dummyBoardList.add(temp);
			
		}
		
		boardAdvanceDAO.insertBoardDummy(dummyBoardList);
		
	}
	
	@Override
	public List<ReplyDTO> getReplyList(long boardId) throws Exception {
		return boardAdvanceDAO.selectListReply(boardId);
	}
	
	@Override
	public int getAllReplyCnt(long boardId) throws Exception {
		return boardAdvanceDAO.selectOneAllReplyCnt(boardId);
	}
	
	@Override
	public ReplyDTO getReplyDetail(long replyId) throws Exception {
		return boardAdvanceDAO.selectOneReply(replyId);
	}
	
	@Override
	public void addReply(ReplyDTO replyDTO) throws Exception {
		replyDTO.setPasswd(bCryptPasswordEncoder.encode(replyDTO.getPasswd()));
		boardAdvanceDAO.insertReply(replyDTO);
	}

	@Override
	public boolean modifyReply(ReplyDTO replyDTO) throws Exception {
		if (bCryptPasswordEncoder.matches(replyDTO.getPasswd() , boardAdvanceDAO.selectOneValidateReplyUserCheck(replyDTO.getReplyId()))) {
			boardAdvanceDAO.updateReply(replyDTO);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeReply(ReplyDTO replyDTO) throws Exception {
		if (bCryptPasswordEncoder.matches(replyDTO.getPasswd() , boardAdvanceDAO.selectOneValidateReplyUserCheck(replyDTO.getReplyId()))) {
			boardAdvanceDAO.deleteReply(replyDTO.getReplyId());
			return true;
		}
		return false;
	}

	@Override
	@Scheduled(cron="59 59 23 * * *")
	public void getTodayEnrolledBoardAndReplyCnt() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		
		logger.info("(" + today + ") 오늘의 게시글 등록수 : " + boardAdvanceDAO.selectOneTodayEnrolledBoardCnt(today));
		logger.info("(" + today + ") 오늘의 댓글 등록수 : " + boardAdvanceDAO.selectOneTodayEnrolledReplyCnt(today));
		
	}

	
}
