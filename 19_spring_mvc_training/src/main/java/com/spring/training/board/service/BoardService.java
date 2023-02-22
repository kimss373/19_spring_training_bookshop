package com.spring.training.board.service;

import java.util.List;

import com.spring.training.board.dto.BoardDTO;

public interface BoardService {

	public void addBoard(BoardDTO boardDTO) throws Exception;
	public List<BoardDTO> getBoardList() throws Exception;
	public BoardDTO getBoardDetail(long boardId, boolean isUpdateReadCnt) throws Exception;
	public boolean modifyBoard(BoardDTO boardDTO) throws Exception;
	public boolean removeBoard(BoardDTO boardDTO) throws Exception;
	
}
