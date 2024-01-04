package com.RequestManagementService.RequestManagementService.ServicesImpl;

import com.RequestManagementService.RequestManagementService.Entity.Request;
import com.RequestManagementService.RequestManagementService.Entity.Status;
import com.RequestManagementService.RequestManagementService.Repository.*;
import com.RequestManagementService.RequestManagementService.Services.RequestManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestManagementServiceImpl implements RequestManagementService {

    private static final Logger logger = LoggerFactory.getLogger(RequestManagementServiceImpl.class);

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<Request> getAllValidatorRequests(long validatorId, Status status) {
        // Retrieve all requests for a validator with a given status
        logger.info(String.valueOf(requestRepository.findAllByValidatorIdAndStatus(validatorId, status).isEmpty()));
        return requestRepository.findAllByValidatorIdAndStatus(validatorId, status);
    }
    @Override
    public List<Request> getAllDemandeurRequests(long demandeurId, Status status) {
        // Retrieve all requests for a demandeur with a given status
        return requestRepository.findAllByDemandeurIdAndStatus(demandeurId, status);
    }
    @Override
    public Request getRequestById(long id) {
        // Retrieve a request by its id
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public List<Request> getRequestRealisee() {
        // Retrieve all requests with status 'REALISEE'
        return requestRepository.findAllByStatus(Status.REALISEE);
    }

    @Override
    public List<Request> getAllRequest() {
        return requestRepository.findAll();
    }


}
