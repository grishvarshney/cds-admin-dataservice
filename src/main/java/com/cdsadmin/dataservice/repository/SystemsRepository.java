package com.cdsadmin.dataservice.repository;

import com.cdsadmin.dataservice.domain.Systems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Systems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemsRepository extends JpaRepository<Systems, Long> {

}
