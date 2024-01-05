package com.FeedbackService.FeedbackService.Repository;

import com.FeedbackService.FeedbackService.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    @Query(value = "Select * from feedback where request_id = :requestId",nativeQuery = true)
    List<Feedback> findByRequestId(@Param("requestId") Long requestId);
}
