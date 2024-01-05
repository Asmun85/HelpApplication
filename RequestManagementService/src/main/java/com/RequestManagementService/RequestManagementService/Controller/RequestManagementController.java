package com.RequestManagementService.RequestManagementService.Controller;


import com.RequestManagementService.RequestManagementService.Entity.Status;
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

    @GetMapping("validator/request/status")
    public ResponseEntity<Object> getAllValidatorRequest(@RequestParam long validator_id, @RequestParam Status status){
        return null;
    }

    @GetMapping("demandeur/request/status")
    public ResponseEntity<Object> getAllDemandeurRequest(@RequestParam long demandeur_id, @RequestParam Status status){
        return null;
    }

    @GetMapping("request/{id}")
    public ResponseEntity<Object> getRequestById(@PathVariable long id){
        return null;
    }

    @GetMapping("request/realisee")
    public ResponseEntity<Object> getRequestRealisee(){
        return null;
    }



}
