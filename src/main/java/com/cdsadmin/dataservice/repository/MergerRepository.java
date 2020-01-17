package com.cdsadmin.dataservice.repository;

import com.cdsadmin.dataservice.domain.Merger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Merger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MergerRepository extends JpaRepository<Merger, Long> {
    @Query(value = "SELECT * FROM Merger m WHERE  ( m.CUSTOMER_FROM  = :customerFrom or m.CUSTOMER_TO = :customerTo ) and m.SYSTEMS_ID  = :SystemId ",
        nativeQuery = true)
    List<Merger> findByCustomerFromOrCustomerTo(@Param("customerFrom") String customerFrom,
                                                @Param("customerTo") String customerTo,
                                                @Param("SystemId") String SystemId);

    @Query(value = "SELECT * FROM Merger m WHERE m.CUSTOMER_FROM  = :customerFrom and m.SYSTEMS_ID  = :SystemId ",
            nativeQuery = true)
        List<Merger> findByCustomerFrom(@Param("customerFrom") String customerTo,
                                                    @Param("SystemId") String SystemId);

    @Query(value = "SELECT * FROM Merger m WHERE m.CUSTOMER_TO  = :customerTo and m.SYSTEMS_ID  = :SystemId ",
            nativeQuery = true)
        List<Merger> findByCustomerTo(@Param("customerTo") String customerTo,
                                                    @Param("SystemId") String SystemId);
}
