package com.example.demo.serviceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.demo.services.RequestManagementService;
import org.springframework.stereotype.Service;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@Service

public class RequestManagementServiceImpl implements RequestManagementService {
    private final RestTemplate restTemplate;

    @Autowired
    public RequestManagementServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override

    public String fetchDataFromRemoteService(String remoteServiceUrl) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(remoteServiceUrl);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace(); // Loggez l'exception
            return "false"; // Renvoie une valeur par défaut ou gérez autrement
        }
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
        String response = fetchDataFromRemoteService(userServiceUrl);

        // La réponse est directement interprétée comme un booléen
        return Boolean.parseBoolean(response.trim());
    }

    public boolean checkIfUserIsValidator(Long userId) throws IOException {
        String userServiceUrl = "http://localhost:9040/users/validator/" + userId;
        String response = fetchDataFromRemoteService(userServiceUrl);

        // Interpréter la réponse comme un booléen
        return Boolean.parseBoolean(response.trim());
    }


    public String validateRequest(Long requestId) throws IOException {
        String validateRequestUrl = "http://localhost:9080/validate-request/" + requestId;
        ResponseEntity<String> response = restTemplate.getForEntity(validateRequestUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "La demande a été validée avec succès.";
        } else {
            return "Échec de la validation de la demande.";
        }

    }


    public boolean checkLinkExists(Long validatorId, Long demandeurId) throws IOException {
            String linkCheckUrl = "http://localhost:9040/users/checklink/" + validatorId + "/" + demandeurId;
            String response = fetchDataFromRemoteService(linkCheckUrl);

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




