package com.example.demo.controller;
import com.example.demo.entityDTO.RequestData;


import com.example.demo.entity.Request;
import com.example.demo.services.HelpRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController // Utilisez @RestController au lieu de @Controller pour les API REST
@RequestMapping("/request") // Préfixe tous les endpoints de ce contrôleur avec /request
public class HelpRequestController {

    private final HelpRequestService helpRequestService;

    @Autowired
    public HelpRequestController(HelpRequestService helpRequestService) {
        this.helpRequestService = helpRequestService;
    }

    // Créer une nouvelle demande d'aide
    @PostMapping
    public ResponseEntity<Request> createHelpRequest(@RequestBody Request helpRequest) {
        Request createdRequest = helpRequestService.createRequest(helpRequest);
        return ResponseEntity.ok(createdRequest);
    }

    // Obtenir une demande d'aide par ID
    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        return helpRequestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour une demande d'aide
    @PutMapping("update/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request helpRequest) {
        return helpRequestService.updateRequest(id, helpRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une demande d'aide
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        helpRequestService.deleteRequest(id);
        return ResponseEntity.ok().build();
    }

    // Lister toutes les demandes d'aide
    @GetMapping("/all")
    public ResponseEntity<List<Request>> getAllHelpRequests() {
        return ResponseEntity.ok(helpRequestService.getAllRequests());
    }
    // Lister les demandes associées à un demandeur
    @GetMapping("/bydemandeur/{demandeurId}")
    public ResponseEntity<List<Request>> getRequestsFromUser(@PathVariable Long DemandeurId) {
        List<Request> requests = helpRequestService.getRequestFromUser(DemandeurId);
        if (requests.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/validate-request/{id}")
    public ResponseEntity<String> validateRequest(@PathVariable("id") Long requestId) {
        try {
            helpRequestService.validateRequest(requestId);
            return ResponseEntity.ok("Request with ID " + requestId + " has been validated.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/refuse-request/{id}")
    public ResponseEntity<String> refuseRequest(@PathVariable("id") Long requestId, @RequestBody RequestData requestData) {
        try {
            String motif = requestData.getMotif(); // Corrigé pour extraire le motif de requestData
            helpRequestService.refuseRequest(requestId, motif);
            return ResponseEntity.ok("Request with ID " + requestId + " has been refused with motif: " + motif);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
