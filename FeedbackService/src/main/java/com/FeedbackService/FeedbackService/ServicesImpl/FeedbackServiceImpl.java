package com.FeedbackService.FeedbackService.ServicesImpl;

import com.FeedbackService.FeedbackService.Entity.Feedback;
import com.FeedbackService.FeedbackService.Entity.Request;
import com.FeedbackService.FeedbackService.Entity.User;
import com.FeedbackService.FeedbackService.Repository.FeedbackRepository;
import com.FeedbackService.FeedbackService.Repository.RequestRepository;
import com.FeedbackService.FeedbackService.Repository.UserRepository;
import com.FeedbackService.FeedbackService.Services.FeedbackService;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository; // Assuming this exists

    @Autowired
    private RequestRepository requestRepository; // Assuming this exists

    @Override
    public List<Feedback> getFeedbackByRequestID(Long requestId) {
        return feedbackRepository.findByRequestId(requestId);
    }

    @Override
    @Transactional
    public Feedback postFeedback(Long userId, Long requestId, String content, String text) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Request request = requestRepository.findRequestByRequestId(requestId).orElseThrow(() -> new IllegalArgumentException("Request not found"));

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setRequest(request);
        logger.info(request.toString());
        feedback.setContent(content);
        feedback.setText(text);

        return feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void deleteFeedback(Long feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    @Override
    @Transactional
    public Feedback updateFeedback(Long feedbackId, String content, String text) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found for this id :: " + feedbackId));

        feedback.setContent(content);
        feedback.setText(text);
        return feedbackRepository.save(feedback);
    }
}
