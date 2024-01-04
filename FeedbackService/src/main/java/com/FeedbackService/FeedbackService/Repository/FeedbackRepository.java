package com.FeedbackService.FeedbackService.Repository;

import com.FeedbackService.FeedbackService.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    List<Feedback> findByRequestId(Long requestId);
}
