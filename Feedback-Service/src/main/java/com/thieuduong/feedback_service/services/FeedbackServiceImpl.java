package com.thieuduong.feedback_service.services;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.clients.IProductClient;
import com.thieuduong.commons.clients.IUserClient;
import com.thieuduong.commons.dto.AddFeedbackDTO;
import com.thieuduong.commons.dto.FeedbackDTO;
import com.thieuduong.feedback_service.models.Feedback;
import com.thieuduong.feedback_service.repositories.IFeedbackRepository;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

	@Autowired
	private IFeedbackRepository feedbackRepository;

	@Autowired
	private IProductClient productClient;

	@Autowired
	private IUserClient userClient;

	@Autowired
	private AnswerServiceImpl answerServiceImpl;

//	@Autowired
//	private IProductRepository productRepository;
//
//	@Autowired
//	private IUserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public FeedbackDTO convertToDto(Feedback Feedback) {
		FeedbackDTO FeedbackDTO = modelMapper.map(Feedback, FeedbackDTO.class);
		FeedbackDTO.setUserFeedBacks(userClient.getStoreById(Feedback.getUserId()));
		FeedbackDTO.setProductFeedBacks(productClient.getProductDetail(Feedback.getProductId()));
		FeedbackDTO.setAnswers(Feedback.getAnswers().stream().map(answerServiceImpl::convertToDto).toList());
		return FeedbackDTO;
	}

	@Override
	public AddFeedbackDTO addFeedback(AddFeedbackDTO addFeedbackDTO) {
		if (addFeedbackDTO.getRating() > 5) {
			addFeedbackDTO.setRating(5);
		}
		Feedback feed = new Feedback(addFeedbackDTO.getUserId(), addFeedbackDTO.getProductId(),
				addFeedbackDTO.getComment(), addFeedbackDTO.getRating(), new Date());
		feedbackRepository.save(feed);
		return addFeedbackDTO;
	}

	@Override
	public List<FeedbackDTO> getFeedbacksByProductId(int productId) {
		return feedbackRepository.findByProductId(productId).stream().map(this::convertToDto).toList();
	}

	@Override
	public Double calculateRating(int productId) {
		double number = feedbackRepository.findAverageRatingByProductId(productId) == null ? 5.0
				: feedbackRepository.findAverageRatingByProductId(productId);
		number = Math.floor(number * 10) / 10;
		return number;
	}
}
