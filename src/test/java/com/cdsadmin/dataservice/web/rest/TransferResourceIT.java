package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.CdsdataserviceApp;
import com.cdsadmin.dataservice.domain.Transfer;
import com.cdsadmin.dataservice.repository.TransferRepository;
import com.cdsadmin.dataservice.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.cdsadmin.dataservice.web.rest.TestUtil.sameInstant;
import static com.cdsadmin.dataservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransferResource} REST controller.
 */
@SpringBootTest(classes = CdsdataserviceApp.class)
public class TransferResourceIT {

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CUSTOMER_FROM = 1L;
    private static final Long UPDATED_CUSTOMER_FROM = 2L;

    private static final Long DEFAULT_CUSTOMER_TO = 1L;
    private static final Long UPDATED_CUSTOMER_TO = 2L;

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSFERRING_ENTITY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSFERRING_ENTITY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTransferMockMvc;

    private Transfer transfer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransferResource transferResource = new TransferResource(transferRepository);
        this.restTransferMockMvc = MockMvcBuilders.standaloneSetup(transferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createEntity(EntityManager em) {
        Transfer transfer = new Transfer()
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .customerFrom(DEFAULT_CUSTOMER_FROM)
            .customerTo(DEFAULT_CUSTOMER_TO)
            .action(DEFAULT_ACTION)
            .transferringEntity(DEFAULT_TRANSFERRING_ENTITY)
            .timeStamp(DEFAULT_TIME_STAMP);
        return transfer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createUpdatedEntity(EntityManager em) {
        Transfer transfer = new Transfer()
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .customerFrom(UPDATED_CUSTOMER_FROM)
            .customerTo(UPDATED_CUSTOMER_TO)
            .action(UPDATED_ACTION)
            .transferringEntity(UPDATED_TRANSFERRING_ENTITY)
            .timeStamp(UPDATED_TIME_STAMP);
        return transfer;
    }

    @BeforeEach
    public void initTest() {
        transfer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransfer() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();

        // Create the Transfer
        restTransferMockMvc.perform(post("/api/transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isCreated());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate + 1);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testTransfer.getCustomerFrom()).isEqualTo(DEFAULT_CUSTOMER_FROM);
        assertThat(testTransfer.getCustomerTo()).isEqualTo(DEFAULT_CUSTOMER_TO);
        assertThat(testTransfer.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testTransfer.getTransferringEntity()).isEqualTo(DEFAULT_TRANSFERRING_ENTITY);
        assertThat(testTransfer.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createTransferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();

        // Create the Transfer with an existing ID
        transfer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferMockMvc.perform(post("/api/transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransfers() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList
        restTransferMockMvc.perform(get("/api/transfers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerFrom").value(hasItem(DEFAULT_CUSTOMER_FROM.intValue())))
            .andExpect(jsonPath("$.[*].customerTo").value(hasItem(DEFAULT_CUSTOMER_TO.intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].transferringEntity").value(hasItem(DEFAULT_TRANSFERRING_ENTITY)))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(sameInstant(DEFAULT_TIME_STAMP))));
    }
    
    @Test
    @Transactional
    public void getTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get the transfer
        restTransferMockMvc.perform(get("/api/transfers/{id}", transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transfer.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.customerFrom").value(DEFAULT_CUSTOMER_FROM.intValue()))
            .andExpect(jsonPath("$.customerTo").value(DEFAULT_CUSTOMER_TO.intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.transferringEntity").value(DEFAULT_TRANSFERRING_ENTITY))
            .andExpect(jsonPath("$.timeStamp").value(sameInstant(DEFAULT_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingTransfer() throws Exception {
        // Get the transfer
        restTransferMockMvc.perform(get("/api/transfers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer
        Transfer updatedTransfer = transferRepository.findById(transfer.getId()).get();
        // Disconnect from session so that the updates on updatedTransfer are not directly saved in db
        em.detach(updatedTransfer);
        updatedTransfer
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .customerFrom(UPDATED_CUSTOMER_FROM)
            .customerTo(UPDATED_CUSTOMER_TO)
            .action(UPDATED_ACTION)
            .transferringEntity(UPDATED_TRANSFERRING_ENTITY)
            .timeStamp(UPDATED_TIME_STAMP);

        restTransferMockMvc.perform(put("/api/transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransfer)))
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testTransfer.getCustomerFrom()).isEqualTo(UPDATED_CUSTOMER_FROM);
        assertThat(testTransfer.getCustomerTo()).isEqualTo(UPDATED_CUSTOMER_TO);
        assertThat(testTransfer.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testTransfer.getTransferringEntity()).isEqualTo(UPDATED_TRANSFERRING_ENTITY);
        assertThat(testTransfer.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Create the Transfer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferMockMvc.perform(put("/api/transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeDelete = transferRepository.findAll().size();

        // Delete the transfer
        restTransferMockMvc.perform(delete("/api/transfers/{id}", transfer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
