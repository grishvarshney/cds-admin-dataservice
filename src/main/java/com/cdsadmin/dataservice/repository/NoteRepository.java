package com.cdsadmin.dataservice.repository;

import com.cdsadmin.dataservice.domain.Merger;
import com.cdsadmin.dataservice.domain.Note;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Note entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByMerger(Merger merger);
}
