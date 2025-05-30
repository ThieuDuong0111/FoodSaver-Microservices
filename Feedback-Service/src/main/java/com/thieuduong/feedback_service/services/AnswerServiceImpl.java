package com.thieuduong.feedback_service.services;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.clients.IUserClient;
import com.thieuduong.commons.dto.AddAnswerDTO;
import com.thieuduong.commons.dto.AnswerDTO;
import com.thieuduong.feedback_service.models.Answer;
import com.thieuduong.feedback_service.models.Feedback;
import com.thieuduong.feedback_service.repositories.IAnswerRepository;

@Service
public class AnswerServiceImpl implements IAnswerService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IUserClient userClient;

	@Autowired
	private IAnswerRepository answerRepository;

	@Override
	public AnswerDTO convertToDto(Answer Answer) {
		AnswerDTO AnswerDTO = modelMapper.map(Answer, AnswerDTO.class);
		System.out.println(Answer.getUserId());
		AnswerDTO.setUserAnswer(userClient.getStoreById(Answer.getUserId()));
		return AnswerDTO;
	}

	@Override
	public void addAnswer(AddAnswerDTO addAnswerDTO) {

		Feedback feedBack = new Feedback();
		feedBack.setId(addAnswerDTO.getFeedBackId());
		Answer answer = new Answer();
		answer.setUserId(addAnswerDTO.getUserId());
		answer.setFeedback(feedBack);
		answer.setIsCreator(false);
		answer.setPublishedDate(new Date());
		answer.setAnswer(addAnswerDTO.getAnswer());

		answerRepository.save(answer);

	}

}
