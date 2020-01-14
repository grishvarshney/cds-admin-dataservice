package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.domain.Systems;
import com.cdsadmin.dataservice.repository.SystemsRepository;
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
 * REST controller for managing {@link com.cdsadmin.dataservice.domain.Systems}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SystemsResource {

    private final Logger log = LoggerFactory.getLogger(SystemsResource.class);

    private static final String ENTITY_NAME = "cdsdataserviceSystems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemsRepository systemsRepository;

    public SystemsResource(SystemsRepository systemsRepository) {
        this.systemsRepository = systemsRepository;
    }

    /**
     * {@code POST  /systems} : Create a new systems.
     *
     * @param systems the systems to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systems, or with status {@code 400 (Bad Request)} if the systems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/systems")
    public ResponseEntity<Systems> createSystems(@RequestBody Systems systems) throws URISyntaxException {
        log.debug("REST request to save Systems : {}", systems);
        if (systems.getId() != null) {
            throw new BadRequestAlertException("A new systems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Systems result = systemsRepository.save(systems);
        return ResponseEntity.created(new URI("/api/systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /systems} : Updates an existing systems.
     *
     * @param systems the systems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systems,
     * or with status {@code 400 (Bad Request)} if the systems is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/systems")
    public ResponseEntity<Systems> updateSystems(@RequestBody Systems systems) throws URISyntaxException {
        log.debug("REST request to update Systems : {}", systems);
        if (systems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Systems result = systemsRepository.save(systems);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, systems.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /systems} : get all the systems.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systems in body.
     */
    @GetMapping("/systems")
    public List<Systems> getAllSystems() {
        log.debug("REST request to get all Systems");
        return systemsRepository.findAll();
    }

    /**
     * {@code GET  /systems/:id} : get the "id" systems.
     *
     * @param id the id of the systems to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systems, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/systems/{id}")
    public ResponseEntity<Systems> getSystems(@PathVariable Long id) {
        log.debug("REST request to get Systems : {}", id);
        Optional<Systems> systems = systemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(systems);
    }

    /**
     * {@code DELETE  /systems/:id} : delete the "id" systems.
     *
     * @param id the id of the systems to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/systems/{id}")
    public ResponseEntity<Void> deleteSystems(@PathVariable Long id) {
        log.debug("REST request to delete Systems : {}", id);
        systemsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
