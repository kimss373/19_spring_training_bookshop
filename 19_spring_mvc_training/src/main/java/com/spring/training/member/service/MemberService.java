package com.spring.training.member.service;

import java.util.List;
import java.util.Map;

import com.spring.training.member.dto.MemberDTO;

public interface MemberService {

	public void addMember(MemberDTO memberDTO) throws Exception;	
	public MemberDTO loginMember(MemberDTO memberDTO) throws Exception;
	public List<MemberDTO> getMemberList() throws Exception;
	public MemberDTO getMemberDetail(String memberId) throws Exception;
	public boolean modifyMember(MemberDTO memberDTO) throws Exception;
	public boolean removeMember(MemberDTO memberDTO) throws Exception;
	public String checkOverlappedId(String memberId) throws Exception; 
	public List<MemberDTO> getMemberSearchList(Map<String,String> searchMap) throws Exception;
	public void getTodayNewMemberCnt() throws Exception;
	
}
