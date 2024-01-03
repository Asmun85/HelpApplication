package com.RequestManagementService.RequestManagementService.Services;


import com.RequestManagementService.RequestManagementService.Entity.Request;
import com.RequestManagementService.RequestManagementService.Entity.Status;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface RequestManagementService {

    public abstract List<Request> getAllValidatorRequests(long validatorId, Status status);

    public abstract List<Request> getAllDemandeurRequests(long demandeurId, Status status);

    public abstract Request getRequestById(long id);

    public abstract List<Request> getRequestRealisee();

    public abstract List<Request> getAllRequest();
}
