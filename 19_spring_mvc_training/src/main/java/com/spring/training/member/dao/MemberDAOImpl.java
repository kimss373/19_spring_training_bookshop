package com.spring.training.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.training.member.dto.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO{

	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public void insertMember(MemberDTO memberDTO) throws Exception {
		sqlSession.insert("member.insertMember" , memberDTO);
	}
	
	@Override
	public MemberDTO selectOneloginMember(MemberDTO memberDTO) throws Exception {
		return sqlSession.selectOne("member.selectOneloginMember" , memberDTO);
	}
	
	@Override
	public MemberDTO selectOneDuplicatedMemberCheck(String memberId) throws Exception {
		return sqlSession.selectOne("member.selectOneDuplicatedMemberCheck" , memberId);
	}
	
	@Override
	public List<MemberDTO> selectListMember() throws Exception {
		return sqlSession.selectList("member.selectListMember");
	}

	@Override
	public MemberDTO selectOneMember(String memberId) throws Exception {
		return sqlSession.selectOne("member.selectOneMember" , memberId);
	}

	@Override
	public void deleteMember(String memberId) throws Exception {
		sqlSession.delete("member.deleteMember" , memberId);
	}

	@Override
	public void updateMember(MemberDTO memberDTO) throws Exception {
		sqlSession.update("member.updateMember" , memberDTO);
	}

	@Override
	public List<MemberDTO> selectListSearchMember(Map<String, String> searchMap) throws Exception {
		return sqlSession.selectList("member.selectListSearchMember" , searchMap);
	}

	@Override
	public int selectOneTodayNewMemberCnt(String today) throws Exception {
		return sqlSession.selectOne("member.selectOneMemberCnt" , today);
	}
	
}
