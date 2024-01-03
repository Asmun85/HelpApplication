package com.RequestManagementService.RequestManagementService.Repository;


import com.RequestManagementService.RequestManagementService.Entity.Demandeur;
import com.RequestManagementService.RequestManagementService.Entity.Validator;
import com.RequestManagementService.RequestManagementService.Entity.ValidatorDemandeurLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidatorDemandeurLinkRepository extends JpaRepository<ValidatorDemandeurLink,Long> {

    boolean existsByValidatorAndDemandeur(Validator validator, Demandeur demandeur);
}
