package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.domain.Merger;
import com.cdsadmin.dataservice.domain.Note;
import com.cdsadmin.dataservice.repository.MergerRepository;
import com.cdsadmin.dataservice.repository.NoteRepository;
import com.cdsadmin.dataservice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.cdsadmin.dataservice.domain.Merger}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MergerResource {

    private final Logger log = LoggerFactory.getLogger(MergerResource.class);

    private static final String ENTITY_NAME = "cdsdataserviceMerger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MergerRepository mergerRepository;
    
    @Autowired
    private NoteRepository noteRepository;

    public MergerResource(MergerRepository mergerRepository) {
        this.mergerRepository = mergerRepository;
    }

    /**
     * {@code POST  /mergers} : Create a new merger.
     *
     * @param merger the merger to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new merger, or with status {@code 400 (Bad Request)} if the merger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mergers")
    public ResponseEntity<Merger> createMerger(@RequestBody Merger merger) throws URISyntaxException {
        log.debug("REST request to save Merger : {}", merger);
        if (merger.getId() != null) {
            throw new BadRequestAlertException("A new merger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Merger result = mergerRepository.save(merger);
        Set<Note> notes = merger.getNotes();
        for(Note note:notes) {
        	note.setMerger(merger);
        	Note noteResult = noteRepository.save(note);
        }
        return ResponseEntity.created(new URI("/api/mergers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mergers} : Updates an existing merger.
     *
     * @param merger the merger to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated merger,
     * or with status {@code 400 (Bad Request)} if the merger is not valid,
     * or with status {@code 500 (Internal Server Error)} if the merger couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mergers")
    public ResponseEntity<Merger> updateMerger(@RequestBody Merger merger) throws URISyntaxException {
        log.debug("REST request to update Merger : {}", merger);
        if (merger.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Merger result = mergerRepository.save(merger);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, merger.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mergers} : get all the mergers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mergers in body.
     */
    @GetMapping("/mergers")
    public List<Merger> getAllMergers() {
        log.debug("REST request to get all Mergers");
        return mergerRepository.findAll();
    }

    /**
     * {@code GET  /mergers/:id} : get the "id" merger.
     *
     * @param id the id of the merger to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the merger, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mergers/{id}")
    public ResponseEntity<Merger> getMerger(@PathVariable Long id) {
        log.debug("REST request to get Merger : {}", id);
        Optional<Merger> merger = mergerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(merger);
    }

    /**
     * {@code DELETE  /mergers/:id} : delete the "id" merger.
     *
     * @param id the id of the merger to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mergers/{id}")
    public ResponseEntity<Void> deleteMerger(@PathVariable Long id) {
        log.debug("REST request to delete Merger : {}", id);
        mergerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
