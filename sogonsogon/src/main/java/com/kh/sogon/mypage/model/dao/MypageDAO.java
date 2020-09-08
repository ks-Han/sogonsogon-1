package com.kh.sogon.mypage.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.sogon.member.model.vo.Member;
import com.kh.sogon.board.model.vo.PageInfo;

@Repository
public class MypageDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	
	/** 비밀번호 확인 DAO
	 * @param memberNo
	 * @return result
	 */
	public String checkPwd(Member loginMember) {
		return sqlSession.selectOne("myapageMapper.selectPwd", loginMember);
	}


	/** 회원 정보 수정 DAO
	 * @param loginMember
	 * @param newPwd1
	 * @return result
	 */
	public int updateInfo(Member loginMember, String newPwd1) {
		return sqlSession.selectOne("myapageMapper.updateInfo", loginMember);
	}


	/** 회원 탈퇴 DAO
	 * @param memberNo
	 * @return result
	 */
	public int deleteInfo(int memberNo) {
		return sqlSession.update("mypageMapper.deleteInfo", memberNo);
	}


	/** 전체 멤버 수 조회 DAO
	 * @return listCount
	 */
	public int getListMCount() {
		return sqlSession.selectOne("mypageMapper.getListMCount");
	}

	/** 페이징바에 따라 멤버 조회 DAO
	 * @param pInfo
	 * @return memberList
	 */
	public List<Member> selectMList(PageInfo pInfo) {
		
		int offset = (pInfo.getCurrentPage() - 1) * pInfo.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pInfo.getLimit());
		
		return sqlSession.selectList("mypageMapper.selectMList", null, rowBounds);
	}
	
	
}