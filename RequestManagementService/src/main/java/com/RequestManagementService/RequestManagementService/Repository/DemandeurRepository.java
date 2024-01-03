package com.RequestManagementService.RequestManagementService.Repository;

import com.RequestManagementService.RequestManagementService.Entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur,Long> {
}
