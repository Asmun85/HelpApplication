package com.RequestManagementService.RequestManagementService.Repository;

import com.RequestManagementService.RequestManagementService.Entity.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorRepository extends JpaRepository<Validator,Long> {
}
