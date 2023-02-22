package com.spring.training.member.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.training.member.dao.MemberDAO;
import com.spring.training.member.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private String FILE_REPO_PATH = "C:\\spring_mvc_member_file_repo\\";
	
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Override
	public void addMember(MemberDTO memberDTO) throws Exception {
		memberDTO.setPasswd(bCryptPasswordEncoder.encode(memberDTO.getPasswd())); 
		memberDAO.insertMember(memberDTO);
	}

	@Override
	public MemberDTO loginMember(MemberDTO memberDTO) throws Exception {
		
		MemberDTO dbMemberDTO = memberDAO.selectOneloginMember(memberDTO);
		
		if (dbMemberDTO != null) {
			if (bCryptPasswordEncoder.matches(memberDTO.getPasswd(), dbMemberDTO.getPasswd())) {
				return memberDTO;
			} 
		}
		
		return null;
		
	}
	
	@Override
	public List<MemberDTO> getMemberList() throws Exception {
		return memberDAO.selectListMember();
	}
	
	@Override
	public MemberDTO getMemberDetail(String memberId) throws Exception {
		return memberDAO.selectOneMember(memberId);
	}

	@Override
	public boolean modifyMember(MemberDTO memberDTO) throws Exception {
		
		MemberDTO dbMemberDTO = memberDAO.selectOneloginMember(memberDTO);
		
		if (bCryptPasswordEncoder.matches(memberDTO.getPasswd(), dbMemberDTO.getPasswd())) {
			memberDAO.updateMember(memberDTO);
			return true;
		}
		
		return false;
		
	}
	
	@Override
	public boolean removeMember(MemberDTO memberDTO) throws Exception {
		
		MemberDTO dbMemberDTO = memberDAO.selectOneloginMember(memberDTO);
		
		if (bCryptPasswordEncoder.matches(memberDTO.getPasswd(), dbMemberDTO.getPasswd())) {
			new File(FILE_REPO_PATH + dbMemberDTO.getProfile()).delete();
			memberDAO.deleteMember(memberDTO.getMemberId());
			return true;
		}
		
		return false;
		
	}

	@Override
	public String checkOverlappedId(String memberId) throws Exception {
		
		String isOverLapped = "Y";
		
		if (memberDAO.selectOneDuplicatedMemberCheck(memberId) == null) {
			return "N";
		}
		
		return isOverLapped;
		
	}

	@Override
	public List<MemberDTO> getMemberSearchList(Map<String, String> searchMap) throws Exception {
		return memberDAO.selectListSearchMember(searchMap);
	}

	@Override
	@Scheduled(cron="59 59 23 * * *")
	public void getTodayNewMemberCnt() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		logger.info("(" + today + ") 신규회원수 : " + memberDAO.selectOneTodayNewMemberCnt(today));
		
	}
	
}
