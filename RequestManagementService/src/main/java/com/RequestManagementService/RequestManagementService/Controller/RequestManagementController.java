package com.RequestManagementService.RequestManagementService.Controller;


import com.RequestManagementService.RequestManagementService.Entity.Request;
import com.RequestManagementService.RequestManagementService.Entity.Status;
import com.RequestManagementService.RequestManagementService.Services.RequestManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/requestmanagement")
public class RequestManagementController {
    private static final Logger logger = LoggerFactory.getLogger(RequestManagementController.class);

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



}
