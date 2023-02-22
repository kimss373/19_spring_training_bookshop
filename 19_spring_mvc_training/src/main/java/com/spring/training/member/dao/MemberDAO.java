package com.spring.training.member.dao;

import java.util.List;
import java.util.Map;

import com.spring.training.member.dto.MemberDTO;

public interface MemberDAO {

	public void insertMember(MemberDTO memberDTO) throws Exception;
	public MemberDTO selectOneloginMember(MemberDTO memberDTO) throws Exception;
	public MemberDTO selectOneDuplicatedMemberCheck(String memberId) throws Exception;
	public List<MemberDTO> selectListMember() throws Exception;
	public MemberDTO selectOneMember(String memberId) throws Exception;
	public void updateMember(MemberDTO memberDTO) throws Exception;
	public void deleteMember(String memberId) throws Exception;
	public List<MemberDTO> selectListSearchMember(Map<String,String> searchMap) throws Exception;
	public int selectOneTodayNewMemberCnt(String today) throws Exception;
	
}
