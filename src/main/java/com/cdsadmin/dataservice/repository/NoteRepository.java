package com.cdsadmin.dataservice.repository;

import com.cdsadmin.dataservice.domain.Merger;
import com.cdsadmin.dataservice.domain.Note;
import com.cdsadmin.dataservice.domain.Transfer;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Note entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByMerger(Merger merger);
    List<Note> findByTransfer(Transfer transfer);
    @Query(value = "SELECT * FROM NOTE WHERE TRANSFER_ID IS NULL",
            nativeQuery = true)
        List<Note> findAllNullTrarnsfer();
    @Query(value = "SELECT * FROM NOTE WHERE MERGER_ID IS NULL",
            nativeQuery = true)
        List<Note> findAllNullMerger();
}
