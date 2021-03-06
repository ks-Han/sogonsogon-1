package com.kh.sogon.help.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.sogon.help.model.dao.AnswerDAO;
import com.kh.sogon.help.model.vo.Answer;

@Service
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
	private AnswerDAO answerDAO;
	
	//문의글 조회 Service 구현
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Answer selectAnswer(int helpNo) {
		return answerDAO.selectAnswer(helpNo);
	}

	//문의글 삽입 Service 구현
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertAnswer(Answer answer) {
		
		int result = answerDAO.updateHelp(answer.getParentHelpNo());
		
		int upResult = 0;
		
		if(result>0) {
			upResult = answerDAO.insertAnswer(answer);
		}
		return upResult;
	}

}
