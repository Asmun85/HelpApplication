package com.FeedbackService.FeedbackService.Controller;


import com.FeedbackService.FeedbackService.Entity.Feedback;
import com.FeedbackService.FeedbackService.EntityDTO.FeedbackDTO;
import com.FeedbackService.FeedbackService.Services.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/{requestId}")
    public ResponseEntity<List<Feedback>> getFeedbackByRequestID(@PathVariable Long requestId) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByRequestID(requestId);
        return new ResponseEntity<>(feedbackList,HttpStatus.OK);
    }

    @PostMapping("/newfeedback")
    public ResponseEntity<Feedback> postFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        Feedback createdFeedback = feedbackService.postFeedback(
                feedbackDTO.getUserId(),
                feedbackDTO.getRequestId(),
                feedbackDTO.getContent(),
                feedbackDTO.getText());
        return new ResponseEntity<>(createdFeedback,HttpStatus.CREATED);
    }

    @DeleteMapping("delete/feedback/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("update/feedback/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long feedbackId, @RequestBody String content, @RequestBody String text) {
        Feedback updatedFeedback = feedbackService.updateFeedback(feedbackId, content, text);
        return ResponseEntity.ok().body(updatedFeedback);
    }




}
