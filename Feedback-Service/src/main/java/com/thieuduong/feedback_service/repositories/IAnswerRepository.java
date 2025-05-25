package com.thieuduong.feedback_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.feedback_service.models.Answer;

@Repository
public interface IAnswerRepository extends JpaRepository<Answer, Integer> {

}
