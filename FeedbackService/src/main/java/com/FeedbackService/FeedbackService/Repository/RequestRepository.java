package com.FeedbackService.FeedbackService.Repository;


import com.FeedbackService.FeedbackService.Entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Long> {

}
