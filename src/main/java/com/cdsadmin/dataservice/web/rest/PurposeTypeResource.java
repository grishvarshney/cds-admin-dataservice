package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.domain.PurposeType;
import com.cdsadmin.dataservice.repository.PurposeTypeRepository;
import com.cdsadmin.dataservice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cdsadmin.dataservice.domain.PurposeType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PurposeTypeResource {

    private final Logger log = LoggerFactory.getLogger(PurposeTypeResource.class);

    private static final String ENTITY_NAME = "cdsdataservicePurposeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurposeTypeRepository purposeTypeRepository;

    public PurposeTypeResource(PurposeTypeRepository purposeTypeRepository) {
        this.purposeTypeRepository = purposeTypeRepository;
    }

    /**
     * {@code POST  /purpose-types} : Create a new purposeType.
     *
     * @param purposeType the purposeType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purposeType, or with status {@code 400 (Bad Request)} if the purposeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purpose-types")
    public ResponseEntity<PurposeType> createPurposeType(@RequestBody PurposeType purposeType) throws URISyntaxException {
        log.debug("REST request to save PurposeType : {}", purposeType);
        if (purposeType.getId() != null) {
            throw new BadRequestAlertException("A new purposeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurposeType result = purposeTypeRepository.save(purposeType);
        return ResponseEntity.created(new URI("/api/purpose-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purpose-types} : Updates an existing purposeType.
     *
     * @param purposeType the purposeType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purposeType,
     * or with status {@code 400 (Bad Request)} if the purposeType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purposeType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purpose-types")
    public ResponseEntity<PurposeType> updatePurposeType(@RequestBody PurposeType purposeType) throws URISyntaxException {
        log.debug("REST request to update PurposeType : {}", purposeType);
        if (purposeType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurposeType result = purposeTypeRepository.save(purposeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purposeType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purpose-types} : get all the purposeTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purposeTypes in body.
     */
    @GetMapping("/purpose-types")
    public List<PurposeType> getAllPurposeTypes() {
        log.debug("REST request to get all PurposeTypes");
        return purposeTypeRepository.findAll();
    }

    /**
     * {@code GET  /purpose-types/:id} : get the "id" purposeType.
     *
     * @param id the id of the purposeType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purposeType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purpose-types/{id}")
    public ResponseEntity<PurposeType> getPurposeType(@PathVariable Long id) {
        log.debug("REST request to get PurposeType : {}", id);
        Optional<PurposeType> purposeType = purposeTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(purposeType);
    }

    /**
     * {@code DELETE  /purpose-types/:id} : delete the "id" purposeType.
     *
     * @param id the id of the purposeType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purpose-types/{id}")
    public ResponseEntity<Void> deletePurposeType(@PathVariable Long id) {
        log.debug("REST request to delete PurposeType : {}", id);
        purposeTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
