package com.example.demo.serviceImpl;

import com.example.demo.entity.Request;
import com.example.demo.repository.RequestRepository;
import com.example.demo.services.HelpRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class HelpRequestServiceImpl implements HelpRequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public HelpRequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Optional<Request> getRequestById(Long id) {
        return requestRepository.findById(id);
    }

    @Override
    public Optional<Request> updateRequest(Long id, Request requestDetails) {
        return requestRepository.findById(id)
                .map(requestRepository::save);
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<Request> getRequestFromUser(Long demandeurId) {
        return requestRepository.findByDemandeurId(demandeurId);
    }

    public void validateRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with ID: " + requestId));

        request.setStatus(Request.RequestStatus.VALIDEE);
        requestRepository.save(request);
    }

    public void refuseRequest(Long requestId, String motif) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with ID: " + requestId));
        request.setStatus(Request.RequestStatus.REFUSEE);
        request.setMotif(motif); // Mise à jour du motif avec la valeur spécifiée
        requestRepository.save(request);
    }

}
