package com.example.demo.services;


import com.example.demo.entity.Request;


import java.util.List;
import java.util.Optional;

public interface HelpRequestService {

    Request createRequest(Request request);
    Optional<Request> getRequestById(Long id);
    Optional<Request> updateRequest(Long id, Request requestDetails);
    void deleteRequest(Long id);
    List<Request> getAllRequests();

    List<Request> getRequestFromUser(Long userId);
    public  void validateRequest(Long requestId) ;

    public void refuseRequest(Long requestId, String motif);


}
