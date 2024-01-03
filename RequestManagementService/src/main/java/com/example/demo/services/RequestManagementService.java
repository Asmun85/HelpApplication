package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/* Each service has an interface and impl
* the SerivceImpl gives the implementation of the service*/
@Service
public interface RequestManagementService {


    public String fetchDataFromRemoteService(String remoteServiceUrl) throws IOException ;
    String fetchDataFromUserServiceById(long userId, String userServiceUrl) throws IOException;

    public String validateRequest(Long requestId) throws IOException ;
    public boolean checkIfUserIsDemandeur(Long userId) throws IOException;

    public boolean checkIfUserIsValidator(Long userId) throws IOException;

    public boolean checkLinkExists(Long validatorId, Long demandeurId) throws IOException;
    }
 /*

    public abstract List<Request> getAllValidatorRequests(long validatorId, Status status);

    public abstract List<Request> getAllDemandeurRequests(long demandeurId, Status status);

    public abstract Request getRequestById(long id);

    public abstract List<Request> getRequestRealisee();

    public abstract List<Request> getAllRequest();
 */
