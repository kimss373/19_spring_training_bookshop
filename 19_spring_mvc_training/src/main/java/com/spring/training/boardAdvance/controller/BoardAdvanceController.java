package com.spring.training.boardAdvance.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.training.boardAdvance.dto.MainBoardDTO;
import com.spring.training.boardAdvance.dto.ReplyDTO;
import com.spring.training.boardAdvance.service.BoardAdvanceService;


@Controller
@RequestMapping("/boardAdvance")
public class BoardAdvanceController {

	@Autowired								
	private BoardAdvanceService boardAdvanceService;		
	
	
	@GetMapping("/boardList")
	public ModelAndView boardList(HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("boardAdvance/boardList");
		
		String searchKeyword = request.getParameter("searchKeyword");
		if (searchKeyword == null) searchKeyword = "total";
		
		String searchWord = request.getParameter("searchWord");
		if (searchWord == null) searchWord = "";
		
		
		int onePageViewCnt = 10;
		
		if (request.getParameter("onePageViewCnt") != null) {
			onePageViewCnt = Integer.parseInt(request.getParameter("onePageViewCnt"));
		}
		
		String temp = request.getParameter("currentPageNumber");
		if (temp == null) {
			temp = "1";
		}
		int currentPageNumber = Integer.parseInt(temp);
		
		
		Map<String, String> searchCntMap = new HashMap<String, String>();
		searchCntMap.put("searchKeyword", searchKeyword);
		searchCntMap.put("searchWord", searchWord);
		
		
		int allBoardCnt = boardAdvanceService.getAllBoardCnt(searchCntMap);
		
		int allPageCnt = allBoardCnt / onePageViewCnt + 1;
		
		if (allBoardCnt % onePageViewCnt == 0) allPageCnt--;
		
		int startPage = (currentPageNumber - 1)  / 10  * 10 + 1;
		if (startPage == 0) {
			startPage = 1;
		}
		
		int endPage = startPage + 9;
		
		if (endPage > allPageCnt) endPage = allPageCnt;
		
		
		int startBoardIdx = (currentPageNumber - 1) * onePageViewCnt;
		
		mv.addObject("startPage"         , startPage);
		mv.addObject("endPage"           , endPage);
		mv.addObject("allBoardCnt"   	 , allBoardCnt);
		mv.addObject("allPageCnt"   	 , allPageCnt);
		mv.addObject("onePageViewCnt"    , onePageViewCnt);
		mv.addObject("currentPageNumber" , currentPageNumber);
		mv.addObject("startBoardIdx"     , startBoardIdx);
		mv.addObject("searchKeyword"     , searchKeyword);
		mv.addObject("searchWord"        , searchWord);
		
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("onePageViewCnt" , onePageViewCnt);
		searchMap.put("startBoardIdx"  , startBoardIdx);
		searchMap.put("searchKeyword"  , searchKeyword);
		searchMap.put("searchWord"     , searchWord);
		mv.addObject("boardList"      ,  boardAdvanceService.getBoardList(searchMap));		
		
		
		return mv;
		
	}
	
	
	@GetMapping("/addBoard")
	public ModelAndView addBoard() throws Exception{
		return new ModelAndView("boardAdvance/addBoard");
	}
	
	
	@PostMapping("/addBoard")
	public ResponseEntity<Object> addBoard(MainBoardDTO mainBoardDTO , HttpServletRequest request) throws Exception{
		
		boardAdvanceService.addBoard(mainBoardDTO);
		
		String  jsScript =  "<script>";
				jsScript += "alert('게시글이 등록되었습니다.');";
				jsScript += "location.href='"+ request.getContextPath() +"/boardAdvance/boardList';";
				jsScript += "</script>";
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		return new ResponseEntity<Object>(jsScript , responseHeaders , HttpStatus.OK);
		
	}
	
	
	@GetMapping("/boardDetail")
	public ModelAndView boardDetail(@RequestParam("boardId") long boardId) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("boardAdvance/boardDetail");
		mv.addObject("mainBoardDTO" , boardAdvanceService.getBoardDetail(boardId , true));
		mv.addObject("allReplyCnt" , boardAdvanceService.getAllReplyCnt(boardId));
		mv.addObject("replyList" , boardAdvanceService.getReplyList(boardId));
		
		return mv;
		
	}
	
	
	@GetMapping("/modifyBoard")
	public ModelAndView modifyBoard(@RequestParam("boardId") long boardId) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("boardAdvance/modifyBoard");
		mv.addObject("mainBoardDTO" , boardAdvanceService.getBoardDetail(boardId , false));
		
		return mv;
		
	}
	
	
	@PostMapping("/modifyBoard")
	public ResponseEntity<Object> modifyBoard(MainBoardDTO mainBoardDTO , HttpServletRequest request) throws Exception{
		
		String jsScript = "";
		
		if (boardAdvanceService.modifyBoard(mainBoardDTO)) {
			jsScript = "<script>";
			jsScript += "alert('수정 되었습니다.');";
			jsScript += "location.href='"+ request.getContextPath() +"/boardAdvance/boardList';";
			jsScript += "</script>";
		}
		else {
		   jsScript ="<script>"; 
		   jsScript += "alert('패스워드를 확인해주세요.');";
		   jsScript += "history.go(-1);";
		   jsScript += "</script>";
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		return new ResponseEntity<Object>(jsScript , responseHeaders , HttpStatus.OK);
		
	}
	
	
	@GetMapping("/removeBoard")
	public ModelAndView removeBoard(@RequestParam("boardId") long boardId) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("boardAdvance/removeBoard");
		mv.addObject("mainBoardDTO" , boardAdvanceService.getBoardDetail(boardId , false));
		
		return mv;
		
	}
	
	
	@PostMapping("/removeBoard")
	public ResponseEntity<Object> removeBoard(MainBoardDTO mainBoardDTO , HttpServletRequest request) throws Exception{
		
		String jsScript = "";
		
		if (boardAdvanceService.removeBoard(mainBoardDTO)) {
			jsScript = "<script>";
			jsScript += "alert('삭제 되었습니다.');";
			jsScript += "location.href='"+ request.getContextPath() +"/boardAdvance/boardList';"; 
			jsScript += "</script>";
		}
		else {
		   jsScript ="<script>"; 
		   jsScript += "alert('패스워드를 확인해주세요.');";
		   jsScript += "history.go(-1);";
		   jsScript += "</script>";
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		return new ResponseEntity<Object>(jsScript , responseHeaders , HttpStatus.OK);
		
	}
	
	
	@GetMapping("/addBoardDummy")
	public ModelAndView addBoardDummy() throws Exception{
		boardAdvanceService.addBoardDummy();
		return new ModelAndView("redirect:/boardAdvance/boardList");
	}
	
	
	
	@GetMapping("/addReply")
	public ModelAndView addReply(@RequestParam("boardId") long boardId) throws Exception{
		
		ModelAndView mv = new ModelAndView("boardAdvance/addReply");
		mv.addObject("boardId" , boardId);
		return mv;
		
	}
	
	
	@PostMapping("/addReply")
	public ResponseEntity<Object> addReply(ReplyDTO replyDTO , HttpServletRequest request) throws Exception{
		
		boardAdvanceService.addReply(replyDTO);
		
		String  jsScript =  "<script>";
				jsScript += "alert('댓글이 등록되었습니다.');";
				jsScript += "location.href='"+ request.getContextPath() +"/boardAdvance/boardDetail?boardId=" + replyDTO.getBoardId() + "';";
				jsScript += "</script>";
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		return new ResponseEntity<Object>(jsScript , responseHeaders , HttpStatus.OK);
		
	}
	
	
	@GetMapping("/modifyReply")
	public ModelAndView modifyReply(@RequestParam("replyId") long replyId) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("boardAdvance/modifyReply");
		mv.addObject("replyDTO" , boardAdvanceService.getReplyDetail(replyId));
		
		return mv;
		
	}
	
	
	@PostMapping("/modifyReply")
	public ResponseEntity<Object> modifyReply(ReplyDTO replyDTO , HttpServletRequest request) throws Exception{
		
		String jsScript = "";
		if (boardAdvanceService.modifyReply(replyDTO)) {
			jsScript = "<script>";
			jsScript += "alert('댓글이 수정되었습니다.');";
			jsScript += "location.href='"+ request.getContextPath() +"/boardAdvance/boardDetail?boardId=" + replyDTO.getBoardId() + "';";
			jsScript += "</script>";
		}
		else {
		   jsScript ="<script>"; 
		   jsScript += "alert('패스워드를 확인해주세요.');";
		   jsScript += "history.go(-1);";
		   jsScript += "</script>";
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		return new ResponseEntity<Object>(jsScript , responseHeaders , HttpStatus.OK);
		
	}
	
	
	@GetMapping("/removeReply")
	public ModelAndView removeReply(@RequestParam("replyId") long replyId) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("boardAdvance/removeReply");
		mv.addObject("replyDTO" , boardAdvanceService.getReplyDetail(replyId));
		
		return mv;
		
	}
	
	
	@PostMapping("/removeReply")
	public ResponseEntity<Object> removeReply(ReplyDTO replyDTO , HttpServletRequest request) throws Exception{
		
		String jsScript = "";
		if (boardAdvanceService.removeReply(replyDTO)) {
			jsScript = "<script>";
			jsScript += "alert('댓글이 삭제되었습니다.');";
			jsScript += "location.href='"+ request.getContextPath() +"/boardAdvance/boardDetail?boardId=" + replyDTO.getBoardId() + "';";
			jsScript += "</script>";
		}
		else {
		   jsScript ="<script>"; 
		   jsScript += "alert('패스워드를 확인해주세요.');";
		   jsScript += "history.go(-1);";
		   jsScript += "</script>";
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		return new ResponseEntity<Object>(jsScript , responseHeaders , HttpStatus.OK);
		
	}
	
}
