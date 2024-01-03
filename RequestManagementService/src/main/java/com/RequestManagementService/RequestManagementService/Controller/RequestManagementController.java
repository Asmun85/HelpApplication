package com.example.demo.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.services.RequestManagementService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/requestmanagement")
public class RequestManagementController {
    @Autowired
    private RequestManagementService requestManagementService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public RequestManagementController(RequestManagementService requestManagementService) {
        this.requestManagementService = requestManagementService;
    }

    // endpoint pour récupérer toutes les requests depuis le microservice "request"
    @GetMapping("/requests")
    public ResponseEntity<String> fetchDataFromRequestMicroservice() {
        try {

            String requestServiceUrl = "http://localhost:9080/request/all";

            // Appelez le service pour récupérer les données du microservice "request"
            String data = requestManagementService.fetchDataFromRemoteService(requestServiceUrl);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            // Gérer les erreurs de manière appropriée
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des données.");
        }
    }

    // Ici on recupere juste l'id de la requete

    /*@GetMapping("/requests/{requestId}")
    public ResponseEntity<String> fetchRequestById(@PathVariable Long requestId) {
        try {
            String requestServiceUrl = "http://localhost:9080/request/" + requestId;
            String requestData = requestManagementService.fetchDataFromRemoteService(requestServiceUrl);
            return ResponseEntity.ok(requestData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération de la demande.");
        }
    }
*/

    @GetMapping("/check/{validatorId}/{demandeurId}")
    public ResponseEntity<Boolean> checkLink(
            @PathVariable(name = "validatorId") Long validatorId,
            @PathVariable(name = "demandeurId") Long demandeurId) throws IOException {

        boolean linkExists = requestManagementService.checkLinkExists(validatorId, demandeurId);
        return ResponseEntity.ok(linkExists);
    }

    // Ici on recupere le user_id de la demande specifié et n check si c'est un validator, si c'est le cas on modifie le status
    @GetMapping("/valid/{requestId}")
    public ResponseEntity<String> fetchRequestById(@PathVariable Long requestId) {
        try {
            String requestServiceUrl = "http://localhost:9080/request/" + requestId;
            String requestData = requestManagementService.fetchDataFromRemoteService(requestServiceUrl);

            JsonNode rootNode = objectMapper.readTree(requestData);
            Long demandeurId = rootNode.path("demandeurId").asLong(); // Extraction de l'ID du demandeur
            Long validatorId = rootNode.path("validatorId").asLong(); // Extraction de l'ID du validateur


            boolean linkExists = requestManagementService.checkLinkExists(validatorId, demandeurId);

            if (linkExists) {
                return new ResponseEntity<>(requestManagementService.validateRequest(requestId), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Conditions non remplies pour valider la demande", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Erreur lors de la récupération de la demande.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/UsersFromUserService")
    public ResponseEntity<String> fetchDataFromUserService () {
        try {
            String userServiceUrl = "http://localhost:9040/users/all";
            String userData = requestManagementService.fetchDataFromRemoteService(userServiceUrl);
            return ResponseEntity.ok(userData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des données utilisateur.");
        }
    }
    @GetMapping("/UserDataByUserId/{userId}")
    public ResponseEntity<String> fetchUserDataByUserId ( @PathVariable long userId){
        try {
            String userServiceUrl = "http://localhost:9040/users";
            String userData = requestManagementService.fetchDataFromUserServiceById(userId, userServiceUrl);
            return ResponseEntity.ok(userData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des données utilisateur.");
        }
    }

    /*  private static final Logger logger = LoggerFactory.getLogger(RequestManagementController.class);

    @Autowired
    private RequestManagementService requestManagementService;

    @GetMapping("validator/request/status")
    public ResponseEntity<Object> getAllValidatorRequest(@RequestParam long validator_id, @RequestParam Status status){
        return new ResponseEntity<>(requestManagementService.getAllValidatorRequests(validator_id, status),
                HttpStatus.OK);
    }

    @GetMapping("demandeur/request/status")
    public ResponseEntity<Object> getAllDemandeurRequest(@RequestParam long demandeur_id, @RequestParam Status status){
        return new ResponseEntity<>(requestManagementService.getAllDemandeurRequests(demandeur_id, status),
                HttpStatus.OK);
    }

    @GetMapping("request/{id}")
    public ResponseEntity<Object> getRequestById(@PathVariable long id){
        Request request = requestManagementService.getRequestById(id);
        if (request != null) {
            return new ResponseEntity<>(request,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No request found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("request/realisee")
    public ResponseEntity<Object> getRequestRealisee(){
        return new ResponseEntity<>(requestManagementService.getRequestRealisee(),HttpStatus.OK);
    }

    @GetMapping("request/all")
    public ResponseEntity<Object> getAllRequest(){
        return new ResponseEntity<>(requestManagementService.getAllRequest(),HttpStatus.OK);
    }


     */





}