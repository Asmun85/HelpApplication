package com.FeedbackService.FeedbackService.Repository;

import com.FeedbackService.FeedbackService.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
}
