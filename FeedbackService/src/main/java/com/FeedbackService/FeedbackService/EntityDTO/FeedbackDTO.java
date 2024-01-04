package com.FeedbackService.FeedbackService.EntityDTO;

import lombok.Data;

@Data
public class FeedbackDTO {
    private Long userId;
    private Long requestId;
    private String content;
    private String text;
}
