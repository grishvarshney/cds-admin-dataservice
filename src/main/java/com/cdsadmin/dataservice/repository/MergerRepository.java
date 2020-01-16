package com.cdsadmin.dataservice.repository;

import com.cdsadmin.dataservice.domain.Merger;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Merger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MergerRepository extends JpaRepository<Merger, Long> {
    List<Merger> findByCustomerFromOrCustomerTo(Long customerFrom, Long customerTo);
}
