package com.FeedbackService.FeedbackService.Repository;

import com.FeedbackService.FeedbackService.Entity.Benevole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenevoleRepository extends JpaRepository<Benevole,Long> {
}
