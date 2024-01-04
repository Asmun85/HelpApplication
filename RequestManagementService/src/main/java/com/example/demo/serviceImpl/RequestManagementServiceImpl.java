package com.example.demo.serviceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.example.demo.controller.RequestManagementController;
import com.example.demo.services.RequestManagementService;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service

public class RequestManagementServiceImpl implements RequestManagementService {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RequestManagementServiceImpl.class);

    @Autowired
    public RequestManagementServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override

    public String fetchDataFromRemoteService(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);

        // Ajouter les headers nécessaires, par exemple pour POST
        // con.setRequestProperty("Content-Type", "application/json");

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    @Override
    public String fetchDataFromUserServiceById(long userId, String userServiceUrl) throws IOException {
        try {
            String url = userServiceUrl + "/" + userId;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new IOException("Erreur lors de la récupération des données utilisateur.");
            }
        } catch (RestClientException e) {
            throw new IOException("Erreur lors de la récupération des données utilisateur.", e);
        }
    }

    @Override
    public boolean checkIfUserIsDemandeur(Long userId) throws IOException {
        String userServiceUrl = "http://localhost:9040/users/demandeur/" + userId;
        String response = fetchDataFromRemoteService(userServiceUrl,"GET");

        // La réponse est directement interprétée comme un booléen
        return Boolean.parseBoolean(response.trim());
    }

    public boolean checkIfUserIsValidator(Long userId) throws IOException {
        String userServiceUrl = "http://localhost:9040/users/validator/" + userId;
        String response = fetchDataFromRemoteService(userServiceUrl,"GET");

        // Interpréter la réponse comme un booléen
        return Boolean.parseBoolean(response.trim());
    }


    public String validateRequest(Long requestId) throws IOException {
        String validateRequestUrl = "http://localhost:9080/request/validate-request/" + requestId;

        // Création d'une entité de requête pour la requête PUT.
        // Vous pouvez passer un corps si nécessaire, ou null si aucun corps n'est requis.
        HttpEntity<?> requestEntity = new HttpEntity<>(null);

        // Envoi d'une requête PUT
        ResponseEntity<String> response = restTemplate.exchange(
                validateRequestUrl,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "La demande a été validée avec succès.";
        } else {
            return "Échec de la validation de la demande.";
        }
    }


    public boolean checkLinkExists(Long validatorId, Long demandeurId) throws IOException {
        String linkCheckUrl = "http://localhost:9040/users/finallink?validatorId=" + validatorId + "&demandeurId=" + demandeurId;
            String response = fetchDataFromRemoteService(linkCheckUrl,"POST");
             logger.info("Réponse de l'URL: " + response);

            // Convertit la réponse en booléen
            return Boolean.parseBoolean(response.trim());
        }


}
/*
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
 */




