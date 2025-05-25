package com.thieuduong.feedback_service.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.dto.FeedbackDTO;
import com.thieuduong.feedback_service.models.Feedback;
import com.thieuduong.feedback_service.repositories.IFeedbackRepository;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

	@Autowired
	private IFeedbackRepository feedbackRepository;

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
		FeedbackDTO.getProductFeedBacks().setRating(0.0);
		return FeedbackDTO;
	}

//	@Override
//	public void addFeedback(AddFeedbackDTO addFeedbackDTO) {
//		if (addFeedbackDTO.getRating() > 5) {
//			addFeedbackDTO.setRating(5);
//		}
//		Feedback feed = new Feedback(userRepository.findById(addFeedbackDTO.getUserId()).get(),
//				productRepository.findById(addFeedbackDTO.getProductId()).get(), addFeedbackDTO.getComment(),
//				addFeedbackDTO.getRating(), new Date());
//		feedbackRepository.save(feed);
//	}

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
