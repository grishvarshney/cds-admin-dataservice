package com.cdsadmin.dataservice.repository;

import com.cdsadmin.dataservice.domain.CustomerFinancial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerFinancial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerFinancialRepository extends JpaRepository<CustomerFinancial, Long> {

}
