package com.RequestManagementService.RequestManagementService.Repository;

import com.RequestManagementService.RequestManagementService.Entity.Benevole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenevoleRepository extends JpaRepository<Benevole,Long> {
}
