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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final RestTemplate restTemplate;

    @Autowired
    public FeedbackServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<Feedback> getFeedbackByRequestID(Long requestId) {
        return feedbackRepository.findByRequestId(requestId);
    }

    @Override
    @Transactional
    public Feedback postFeedback(Long userId, Long requestId, String content, String text) {
        Request request = fetchRequestById(requestId);
        logger.info("request found");
        User user = fetchUserById(userId);
        logger.info("user found");
        logger.info(user.toString());
        Feedback feedback = new Feedback();
        feedback.setUserId(userId);
        feedback.setRequestId(request.getId());
        feedback.setText(text);
        feedback.setContent(content);
        return feedbackRepository.save(feedback);
    }

    private Request fetchRequestById(Long requestId) {
        String url = "http://localhost:9080/request/" + requestId;
        ResponseEntity<Request> response = restTemplate.getForEntity(url, Request.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new IllegalArgumentException("Request not found");
        }
    }

    private User fetchUserById(Long userId) {
        String url = "http://localhost:9040/users/" + userId;
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new IllegalArgumentException("User not found");
        }
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
