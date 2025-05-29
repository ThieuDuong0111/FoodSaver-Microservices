package com.thieuduong.feedback_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.commons.dto.FeedbackInformationDTO;
import com.thieuduong.feedback_service.repositories.IFeedbackRepository;
import com.thieuduong.feedback_service.services.FeedbackServiceImpl;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackServiceImpl feedbackServiceImpl;

	@Autowired
	private IFeedbackRepository feedbackRepository;

//	@Autowired
//	private AnswerServiceImpl answerServiceImpl;
//
//	@PostMapping("/add-feedback")
//	public ResponseEntity<?> addFeedback(@RequestBody AddFeedbackDTO addFeedbackDTO) {
//		feedbackServiceImpl.addFeedback(addFeedbackDTO);
//		return ResponseEntity.status(HttpStatus.OK).body(addFeedbackDTO);
//	}

//
	@GetMapping("/get-feedbacks/{id}")
	public ResponseEntity<?> getFeedbacksByProductId(@PathVariable int id) {
		return ResponseEntity.status(HttpStatus.OK).body(feedbackServiceImpl.getFeedbacksByProductId(id));
	}

//
//	@PostMapping("/add-answer")
//	public ResponseEntity<?> addAnswer(@RequestBody AddAnswerDTO addAnswerDTO) {
//		answerServiceImpl.addAnswer(addAnswerDTO);
//		return ResponseEntity.status(HttpStatus.OK).body(addAnswerDTO);
//	}

	// Microservices
	@GetMapping("/get-feedback-information/{id}")
	public ResponseEntity<?> getFeedbackInformation(@PathVariable("id") int identify) {
		FeedbackInformationDTO FeedbackDTO = new FeedbackInformationDTO(feedbackServiceImpl.calculateRating(identify),
				feedbackRepository.countCommentsByProductId(identify),
				feedbackRepository.countRatingsByProductId(identify),
				feedbackRepository.countRatingPointByProductId(identify, 5),
				feedbackRepository.countRatingPointByProductId(identify, 4),
				feedbackRepository.countRatingPointByProductId(identify, 3),
				feedbackRepository.countRatingPointByProductId(identify, 2),
				feedbackRepository.countRatingPointByProductId(identify, 1));
		return ResponseEntity.status(HttpStatus.OK).body(FeedbackDTO);
	}
}
