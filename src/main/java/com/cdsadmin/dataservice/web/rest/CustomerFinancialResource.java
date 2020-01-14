package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.domain.CustomerFinancial;
import com.cdsadmin.dataservice.repository.CustomerFinancialRepository;
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
 * REST controller for managing {@link com.cdsadmin.dataservice.domain.CustomerFinancial}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerFinancialResource {

    private final Logger log = LoggerFactory.getLogger(CustomerFinancialResource.class);

    private static final String ENTITY_NAME = "cdsdataserviceCustomerFinancial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerFinancialRepository customerFinancialRepository;

    public CustomerFinancialResource(CustomerFinancialRepository customerFinancialRepository) {
        this.customerFinancialRepository = customerFinancialRepository;
    }

    /**
     * {@code POST  /customer-financials} : Create a new customerFinancial.
     *
     * @param customerFinancial the customerFinancial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerFinancial, or with status {@code 400 (Bad Request)} if the customerFinancial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-financials")
    public ResponseEntity<CustomerFinancial> createCustomerFinancial(@RequestBody CustomerFinancial customerFinancial) throws URISyntaxException {
        log.debug("REST request to save CustomerFinancial : {}", customerFinancial);
        if (customerFinancial.getId() != null) {
            throw new BadRequestAlertException("A new customerFinancial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerFinancial result = customerFinancialRepository.save(customerFinancial);
        return ResponseEntity.created(new URI("/api/customer-financials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-financials} : Updates an existing customerFinancial.
     *
     * @param customerFinancial the customerFinancial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerFinancial,
     * or with status {@code 400 (Bad Request)} if the customerFinancial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerFinancial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-financials")
    public ResponseEntity<CustomerFinancial> updateCustomerFinancial(@RequestBody CustomerFinancial customerFinancial) throws URISyntaxException {
        log.debug("REST request to update CustomerFinancial : {}", customerFinancial);
        if (customerFinancial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerFinancial result = customerFinancialRepository.save(customerFinancial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerFinancial.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-financials} : get all the customerFinancials.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerFinancials in body.
     */
    @GetMapping("/customer-financials")
    public List<CustomerFinancial> getAllCustomerFinancials() {
        log.debug("REST request to get all CustomerFinancials");
        return customerFinancialRepository.findAll();
    }

    /**
     * {@code GET  /customer-financials/:id} : get the "id" customerFinancial.
     *
     * @param id the id of the customerFinancial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerFinancial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-financials/{id}")
    public ResponseEntity<CustomerFinancial> getCustomerFinancial(@PathVariable Long id) {
        log.debug("REST request to get CustomerFinancial : {}", id);
        Optional<CustomerFinancial> customerFinancial = customerFinancialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerFinancial);
    }

    /**
     * {@code DELETE  /customer-financials/:id} : delete the "id" customerFinancial.
     *
     * @param id the id of the customerFinancial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-financials/{id}")
    public ResponseEntity<Void> deleteCustomerFinancial(@PathVariable Long id) {
        log.debug("REST request to delete CustomerFinancial : {}", id);
        customerFinancialRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
