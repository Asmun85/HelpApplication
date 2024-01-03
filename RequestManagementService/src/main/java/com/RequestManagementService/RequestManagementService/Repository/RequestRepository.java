package com.RequestManagementService.RequestManagementService.Repository;

import com.RequestManagementService.RequestManagementService.Entity.Request;
import com.RequestManagementService.RequestManagementService.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Long> {
    List<Request> findAllByValidatorIdAndStatus(long validatorId, Status status);

    List<Request> findAllByDemandeurIdAndStatus(long demandeurId, Status status);

    List<Request> findAllByStatus(Status status);
}
