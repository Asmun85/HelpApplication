package com.FeedbackService.FeedbackService.Repository;


import com.FeedbackService.FeedbackService.Entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {

    @Query(value = "select * from request where request_id = :id",nativeQuery = true)
    Optional<Request> findRequestByRequestId(@Param("id") long id);

}
