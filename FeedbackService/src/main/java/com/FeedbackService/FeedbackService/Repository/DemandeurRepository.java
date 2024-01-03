package com.FeedbackService.FeedbackService.Repository;

import com.FeedbackService.FeedbackService.Entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur,Long> {
}
