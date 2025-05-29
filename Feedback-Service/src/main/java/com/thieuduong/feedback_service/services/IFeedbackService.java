package com.thieuduong.feedback_service.services;

import java.util.List;

import com.thieuduong.commons.dto.AddFeedbackDTO;
import com.thieuduong.commons.dto.FeedbackDTO;
import com.thieuduong.feedback_service.models.Feedback;

public interface IFeedbackService {

	FeedbackDTO convertToDto(Feedback feedBack);

	void addFeedback(AddFeedbackDTO addFeedBackDTO);

	List<FeedbackDTO> getFeedbacksByProductId(int productId);

	Double calculateRating(int productId);
}
