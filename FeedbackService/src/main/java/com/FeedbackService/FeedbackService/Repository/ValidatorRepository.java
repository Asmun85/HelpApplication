package com.FeedbackService.FeedbackService.Repository;

import com.FeedbackService.FeedbackService.Entity.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorRepository extends JpaRepository<Validator,Long> {
}
