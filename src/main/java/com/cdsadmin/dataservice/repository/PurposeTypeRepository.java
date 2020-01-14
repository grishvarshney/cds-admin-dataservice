package com.cdsadmin.dataservice.repository;

import com.cdsadmin.dataservice.domain.PurposeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurposeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurposeTypeRepository extends JpaRepository<PurposeType, Long> {

}
