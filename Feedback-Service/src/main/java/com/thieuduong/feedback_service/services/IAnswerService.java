package com.thieuduong.feedback_service.services;

import com.thieuduong.commons.dto.AddAnswerDTO;
import com.thieuduong.commons.dto.AnswerDTO;
import com.thieuduong.feedback_service.models.Answer;

public interface IAnswerService {

	AnswerDTO convertToDto(Answer Answer);

	void addAnswer(AddAnswerDTO addAnswerDTO);

}
