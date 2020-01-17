package com.cdsadmin.dataservice.repository;

import com.cdsadmin.dataservice.domain.Merger;
import com.cdsadmin.dataservice.domain.Transfer;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Transfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query(value = "SELECT * FROM Transfer m WHERE  ( m.CUSTOMER_FROM  = :customerFrom or m.CUSTOMER_TO = :customerTo ) and m.SYSTEMS_ID  = :SystemId ",
        nativeQuery = true)
    List<Transfer> findByCustomerFromOrCustomerTo(@Param("customerFrom") String customerFrom,
                                                @Param("customerTo") String customerTo,
                                                @Param("SystemId") String SystemId);

    @Query(value = "SELECT * FROM TRANSFER m WHERE m.CUSTOMER_FROM  = :customerFrom and m.SYSTEMS_ID  = :SystemId ",
            nativeQuery = true)
        List<Transfer> findByCustomerFrom(@Param("customerFrom") String customerTo,
                                                    @Param("SystemId") String SystemId);

    @Query(value = "SELECT * FROM TRANSFER m WHERE m.CUSTOMER_TO  = :customerTo and m.SYSTEMS_ID  = :SystemId ",
            nativeQuery = true)
        List<Transfer> findByCustomerTo(@Param("customerTo") String customerTo,
                                                    @Param("SystemId") String SystemId);

}
