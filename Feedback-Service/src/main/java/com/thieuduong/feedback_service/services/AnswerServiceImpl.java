package com.thieuduong.feedback_service.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.clients.IUserClient;
import com.thieuduong.commons.dto.AnswerDTO;
import com.thieuduong.feedback_service.models.Answer;

@Service
public class AnswerServiceImpl implements IAnswerService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IUserClient userClient;

	@Override
	public AnswerDTO convertToDto(Answer Answer) {
		AnswerDTO AnswerDTO = modelMapper.map(Answer, AnswerDTO.class);
		System.out.println(Answer.getUserId());
		AnswerDTO.setUserAnswer(userClient.getStoreById(Answer.getUserId()));
		return AnswerDTO;
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
