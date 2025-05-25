package com.thieuduong.feedback_service.services;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.dto.AnswerDTO;
import com.thieuduong.feedback_service.models.Answer;

@Service
public class AnswerServiceImpl implements IAnswerService {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AnswerDTO convertToDto(Answer Answer) {
		return modelMapper.map(Answer, AnswerDTO.class);
	}

//	@Override
//	public void addAnswer(AddAnswerDTO addAnswerDTO) {
//
//		Feedback feedBack = new Feedback();
//		feedBack.setId(addAnswerDTO.getFeedBackId());
//		MyUser user = new MyUser();
//		user.setId(addAnswerDTO.getUserId());
//		Answer answer = new Answer();
//		answer.setFeedback(feedBack);
//		answer.setUserAnswer(user);
//		answer.setIsCreator(false);
//		answer.setPublishedDate(new Date());
//		answer.setAnswer(addAnswerDTO.getAnswer());
//
//		answerRepository.save(answer);
//
//	}

}
