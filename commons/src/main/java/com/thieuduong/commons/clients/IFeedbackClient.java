package com.thieuduong.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.thieuduong.commons.dto.FeedbackInformationDTO;

@FeignClient(name = "feedback-service", url = "http://localhost:8084", path = "/api/feedback/")
public interface IFeedbackClient {
	@GetMapping("/get-feedback-information/{id}")
	FeedbackInformationDTO getFeedbackInformation(@PathVariable("id") int identify);
}
