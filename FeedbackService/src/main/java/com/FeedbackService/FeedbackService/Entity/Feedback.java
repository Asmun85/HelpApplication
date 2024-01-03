package com.FeedbackService.FeedbackService.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "feedback")
public class Feedback {
    @Id
    @Column(name = "feedback_id")
    private Long id;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "request_id")
    private Request request;

    @Column(name = "content")
    private String content;

    @Column(name = "text")
    private String text;

}
