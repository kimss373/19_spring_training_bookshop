package com.spring.training.member.controller;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.training.member.dto.MemberDTO;
import com.spring.training.member.service.MemberService;

import net.coobird.thumbnailator.Thumbnails;


@Controller
@RequestMapping("/member")
public class MemberController {

	private String FILE_REPO_PATH = "C:\\spring_mvc_member_file_repo\\";
	
	@Autowired
	private MemberService memberService;
	
	
	
	@GetMapping("/searchMemberList")
	public @ResponseBody List<MemberDTO> searchMemberList(@RequestParam Map<String,String> searchMap) throws Exception {
		return memberService.getMemberSearchList(searchMap);
	}
	
	
	@GetMapping("/mainMember")
	public ModelAndView main() throws Exception {
		return new ModelAndView("member/mainMember");
	}
	
	
	@GetMapping("/registerMember")
	public ModelAndView register() throws Exception{
		return new ModelAndView("member/registerMember");
	}
	
	
	@PostMapping("/registerMember")
	public @ResponseBody String register(MultipartHttpServletRequest multipartRequest , HttpServletRequest request) throws Exception {
		
		Iterator<String> fileList = multipartRequest.getFileNames();			
		String fileName = "";
		if (fileList.hasNext()) {												
			MultipartFile uploadFile = multipartRequest.getFile(fileList.next()); 
			if (!uploadFile.getOriginalFilename().isEmpty()) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
				fileName = fmt.format(new Date()) + "_" + UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();
				uploadFile.transferTo(new File(FILE_REPO_PATH + fileName)); 
			}
		
		}
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(multipartRequest.getParameter("memberId"));
		memberDTO.setPasswd(multipartRequest.getParameter("passwd"));
		memberDTO.setProfile(fileName);
		memberDTO.setMemberNm(multipartRequest.getParameter("memberNm"));
		memberDTO.setSex(multipartRequest.getParameter("sex"));
		memberDTO.setBirthDt(multipartRequest.getParameter("birthDt"));
		memberDTO.setHp(multipartRequest.getParameter("hp"));

		if (multipartRequest.getParameter("smsstsYn") == null )  memberDTO.setSmsstsYn("N");
		else													 memberDTO.setSmsstsYn("Y");
		memberDTO.setEmail(multipartRequest.getParameter("email"));
		
		if (multipartRequest.getParameter("emailstsYn") == null) memberDTO.setEmailstsYn("N");
		else													 memberDTO.setEmailstsYn("Y");
		
		memberDTO.setZipcode(multipartRequest.getParameter("zipcode"));
		memberDTO.setRoadAddress(multipartRequest.getParameter("roadAddress"));
		memberDTO.setJibunAddress(multipartRequest.getParameter("jibunAddress"));
		memberDTO.setNamujiAddress(multipartRequest.getParameter("namujiAddress"));
		memberDTO.setEtc(multipartRequest.getParameter("etc"));
		
		memberService.addMember(memberDTO);
		
		String jsScript = "<script>";
			   jsScript += "alert('You are now a member.');";
			   jsScript += "location.href='" + request.getContextPath() + "/member/mainMember'";
			   jsScript += "</script>";
			   
		return jsScript;
		
	}
	
	
	@PostMapping("/overlappedId")
	public @ResponseBody String overlapped(@RequestParam("memberId") String memberId) throws Exception{
		return memberService.checkOverlappedId(memberId);
	}
	
	
	@GetMapping("/loginMember")
	public ModelAndView loginMember() throws Exception {
		return new ModelAndView("member/loginMember");
	}
	
	
	@PostMapping("/loginMember")
	public @ResponseBody String loginMember(MemberDTO memberDTO , HttpServletRequest request) throws Exception {
		
		String jsScript = "";
		if (memberService.loginMember(memberDTO) != null) {
			
			HttpSession session = request.getSession();
			session.setAttribute("memberId", memberDTO.getMemberId());
			
			jsScript += "<script>";
			jsScript += "alert('You are logged in.');";
			jsScript += "location.href='" + request.getContextPath() + "/member/mainMember'";
			jsScript += "</script>";
			
		} 
		else {
			
			jsScript += "<script>";
			jsScript += "alert('check your Id or Password!');";
			jsScript += "history.go(-1);";
			jsScript += "</script>";
			
		}
		
		return jsScript;
		
	}
	
	
	@GetMapping("/logoutMember")
	public @ResponseBody String logoutMember(HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		String jsScript = "<script>";
				jsScript += "alert('You are logged out.');";
				jsScript += "location.href='" + request.getContextPath() + "/member/mainMember'";
				jsScript += "</script>";
		
		return jsScript;
		
	}
	
	
	@GetMapping("/memberList")
	public ModelAndView memberList() throws Exception {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("member/memberList");
		mv.addObject("memberList" , memberService.getMemberList());
		
		return mv;
	
	}	
	
	
	@GetMapping("/modifyMember")
	public ModelAndView modifyMember(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("member/modifyMember");
		mv.addObject("memberDTO" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		
		return mv;
		
	}	
	
	@PostMapping("/modifyMember")
	public @ResponseBody String modifyMember(MultipartHttpServletRequest multipartRequest , HttpServletRequest request) throws Exception {
		
		Iterator<String> fileList = multipartRequest.getFileNames();			
		String fileName = "";
		if (fileList.hasNext()) {												
			
			MultipartFile uploadFile = multipartRequest.getFile(fileList.next()); 
			if (!uploadFile.getOriginalFilename().isEmpty()) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
				fileName =  fmt.format(new Date()) + "_" + UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();	
				uploadFile.transferTo(new File(FILE_REPO_PATH + fileName)); 
				new File(FILE_REPO_PATH + multipartRequest.getParameter("beforeFileName")).delete();		
				
			}
		
		}
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(multipartRequest.getParameter("memberId"));
		memberDTO.setPasswd(multipartRequest.getParameter("passwd"));
		memberDTO.setProfile(fileName);
		memberDTO.setMemberNm(multipartRequest.getParameter("memberNm"));
		memberDTO.setSex(multipartRequest.getParameter("sex"));
		memberDTO.setBirthDt(multipartRequest.getParameter("birthDt"));
		memberDTO.setHp(multipartRequest.getParameter("hp"));
		
		if (multipartRequest.getParameter("smsstsYn") == null )  memberDTO.setSmsstsYn("N");
		else													 memberDTO.setSmsstsYn("Y");
		memberDTO.setEmail(multipartRequest.getParameter("email"));
		
		if (multipartRequest.getParameter("emailstsYn") == null) memberDTO.setEmailstsYn("N");
		else													 memberDTO.setEmailstsYn("Y");
		
		memberDTO.setZipcode(multipartRequest.getParameter("zipcode"));
		memberDTO.setRoadAddress(multipartRequest.getParameter("roadAddress"));
		memberDTO.setJibunAddress(multipartRequest.getParameter("jibunAddress"));
		memberDTO.setNamujiAddress(multipartRequest.getParameter("namujiAddress"));
		memberDTO.setEtc(multipartRequest.getParameter("etc"));
		
		String jsScript = "";
		if (memberService.modifyMember(memberDTO)) {
			jsScript = "<script>";
			jsScript += "alert('It is changed.');";
			jsScript += "location.href='" + request.getContextPath() + "/member/mainMember'";
			jsScript += "</script>";
		} 
		else {
			jsScript = "<script>";
			jsScript += "alert('check your password!');";
			jsScript += "history.back(-1);";
			jsScript += "</script>";
		}
		
		return  jsScript;
		
	}
	
	
	@GetMapping("/removeMember")
	public ModelAndView removeMember() throws Exception{
		return new ModelAndView("member/removeMember");
	}
	
	
	@PostMapping("/removeMember")
	public @ResponseBody String removeMember(MemberDTO memberDTO , HttpServletRequest request) throws Exception {
		
		String jsScript = "";
		
		if (memberService.removeMember(memberDTO)) {
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			jsScript += "<script>";
			jsScript += "alert('Your account has been deleted successfully!');";
			jsScript += "location.href='" + request.getContextPath() + "/member/mainMember'";
			jsScript += "</script>";
			
		} 
		else {
			
			jsScript += "<script>";
			jsScript += "alert('Check your Id or Password');";
			jsScript += "history.go(-1);";
			jsScript += "</script>";
	
		}
		
		return jsScript;
		
	}
	
	
	// 엑셀 export 컨트롤러
	@GetMapping("/memberExcelExport")
	public void memberExcelExport(HttpServletResponse response) throws Exception {
		  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
		String fileName = sdf.format(new Date()) + "_memberList.xls";
		
	    // 워크북 생성
	    Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet("회원리스트");
	    Row row = null;
	    Cell cell = null;

	    int rowNo = 0;

	    // 테이블 헤더용 스타일
	    CellStyle headStyle = wb.createCellStyle();
	    // 가는 경계선
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);


	    // 노란색 배경
	    headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    // 가운데 정렬
	    headStyle.setAlignment(HorizontalAlignment.CENTER);


	    // 데이터용 경계 스타일 테두리만 지정
	    CellStyle bodyStyle = wb.createCellStyle();
	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);

	    // 헤더 생성
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("회원아이디");
	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("회원이름");
	    cell = row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("성별");
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("휴대폰번호");
	    cell = row.createCell(4);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("문자수신여부");
	    cell = row.createCell(5);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("주소");
	    cell = row.createCell(6);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("이메일");
	    cell = row.createCell(7);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("이메일수신여부");
	    
		for (MemberDTO memberDTO :  memberService.getMemberList()) {
			row = sheet.createRow(rowNo++);
	        cell = row.createCell(0);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDTO.getMemberId());
	        cell = row.createCell(1);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDTO.getMemberNm());
	        cell = row.createCell(2);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDTO.getSex());
	        cell = row.createCell(3);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDTO.getHp());
	        cell = row.createCell(4);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDTO.getSmsstsYn());
	        cell = row.createCell(5);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDTO.getRoadAddress() + " " + memberDTO.getJibunAddress() + " " + memberDTO.getNamujiAddress());
	        cell = row.createCell(6);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDTO.getEmail());
	        cell = row.createCell(7);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDTO.getEmailstsYn());

		} 

	    response.setContentType("ms-vnd/excel");
	    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

	    // 엑셀 출력
	    wb.write(response.getOutputStream());
	    wb.close();
		
	}
	

	@GetMapping("/thumbnails")
	public void thumbnails(@RequestParam("fileName") String fileName , HttpServletResponse response) throws Exception {
	
		OutputStream out = response.getOutputStream();
		
		File image = new File(FILE_REPO_PATH + fileName);
		if (image.exists()) { 
			Thumbnails.of(image).size(800,800).outputFormat("png").toOutputStream(out);
		}
		byte[] buffer = new byte[1024 * 8];
		out.write(buffer);
		out.close();
		
	}
	

}
