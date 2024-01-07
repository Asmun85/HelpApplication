package com.FeedbackService.FeedbackService.Services;



import com.FeedbackService.FeedbackService.Entity.Feedback;
import com.FeedbackService.FeedbackService.Entity.Request;
import jakarta.transaction.Transactional;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getFeedbackByRequestID(Long requestId);
    Feedback postFeedback(Long userId, Long requestId, String content, String text);
    void deleteFeedback(Long feedbackId);
    Feedback updateFeedback(Long feedbackId, String content, String text);
}
