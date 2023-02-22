package com.spring.training.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.training.board.dto.BoardDTO;
import com.spring.training.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/addBoard")
	public ModelAndView addBoard() throws Exception {
//		ModelAndView mv = new ModelAndView("board/addBoard");
//		
//		return mv;
		return new ModelAndView("board/addBoard");
	}
	
	@PostMapping("/addBoard")
	@ResponseBody
	public String addBoard(@ModelAttribute BoardDTO boardDTO) throws Exception {
		
		boardService.addBoard(boardDTO);		
		
		String jsScript = "<script>";
			   jsScript += "alert('Post Added');";
			   jsScript += "location.href='boardList';";
			   jsScript += "</script>";
		
		return jsScript;
	}
	
	@GetMapping("/boardList")
	public ModelAndView boardList() throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/boardList");
		mv.addObject("boardList", boardService.getBoardList());
		
		return mv;
	}
	
	@GetMapping("/boardDetail")
	public ModelAndView boardDetail(@RequestParam("boardId") long boardId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/boardDetail");
		mv.addObject("boardDTO", boardService.getBoardDetail(boardId, true));
		
		return mv;
	}
	
	@GetMapping("/modifyBoard")
	public ModelAndView modifyBoard(@RequestParam("boardId") long boardId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/modifyBoard");
		mv.addObject("boardDTO", boardService.getBoardDetail(boardId, false));
		return mv;
		
	}
	
	@PostMapping("/modifyBoard")
	@ResponseBody
	public String modifyBoard(@ModelAttribute BoardDTO boardDTO) throws Exception {
		
		String jsString = "";
		if (boardService.modifyBoard(boardDTO)) {
			jsString += "<script>";
			jsString += "alert('It is changed');";
			jsString += "location.href='boardList';";
			jsString += "</script>";
		}
		else {
			jsString += "<script>";
			jsString += "alert('Check your Id or Password');";
			jsString += "history.go(-1);";
			jsString += "</script>";
		}
		
		return jsString;
	}
	
	@GetMapping("/removeBoard")
	public ModelAndView removeBoard(@RequestParam("boardId") long boardId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/removeBoard");
		mv.addObject("boardDTO", boardService.getBoardDetail(boardId, false));
		return mv;
	}
	
	@PostMapping("/removeBoard")
	@ResponseBody
	public String removeBoard(@ModelAttribute BoardDTO boardDTO) throws Exception {
		
		String jsString = "";
		if (boardService.removeBoard(boardDTO)) {
			jsString += "<script>";
			jsString += "alert('It has been deleted');";
			jsString += "location.href='boardList';";
			jsString += "</script>";
		}
		else {
			jsString += "<script>";
			jsString += "alert('Check your Id or Password');";
			jsString += "history.go(-1);";
			jsString += "</script>";
		}
		
		return jsString;
	}
	
	
}
