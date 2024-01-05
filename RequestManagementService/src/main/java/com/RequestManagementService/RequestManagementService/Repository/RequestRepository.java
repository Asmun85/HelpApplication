package com.RequestManagementService.RequestManagementService.Repository;

import com.RequestManagementService.RequestManagementService.Entity.Request;
import com.RequestManagementService.RequestManagementService.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {
    List<Request> findAllByValidatorIdAndStatus(long validatorId, Status status);

    List<Request> findAllByDemandeurIdAndStatus(long demandeurId, Status status);

    List<Request> findAllByStatus(Status status);

    @Query(value = "select * from request where request_id = :id",nativeQuery = true)
    Optional<Request> findRequestByRequestId(@Param("id") long id);
}
