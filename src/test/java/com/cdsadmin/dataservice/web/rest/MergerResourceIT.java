package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.CdsdataserviceApp;
import com.cdsadmin.dataservice.domain.Merger;
import com.cdsadmin.dataservice.repository.MergerRepository;
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
 * Integration tests for the {@link MergerResource} REST controller.
 */
@SpringBootTest(classes = CdsdataserviceApp.class)
public class MergerResourceIT {

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CUSTOMER_FROM = 1L;
    private static final Long UPDATED_CUSTOMER_FROM = 2L;

    private static final Long DEFAULT_CUSTOMER_TO = 1L;
    private static final Long UPDATED_CUSTOMER_TO = 2L;

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_MERGING_ENTITY = "AAAAAAAAAA";
    private static final String UPDATED_MERGING_ENTITY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private MergerRepository mergerRepository;

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

    private MockMvc restMergerMockMvc;

    private Merger merger;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MergerResource mergerResource = new MergerResource(mergerRepository);
        this.restMergerMockMvc = MockMvcBuilders.standaloneSetup(mergerResource)
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
    public static Merger createEntity(EntityManager em) {
        Merger merger = new Merger()
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .customerFrom(DEFAULT_CUSTOMER_FROM)
            .customerTo(DEFAULT_CUSTOMER_TO)
            .action(DEFAULT_ACTION)
            .mergingEntity(DEFAULT_MERGING_ENTITY)
            .timeStamp(DEFAULT_TIME_STAMP);
        return merger;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Merger createUpdatedEntity(EntityManager em) {
        Merger merger = new Merger()
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .customerFrom(UPDATED_CUSTOMER_FROM)
            .customerTo(UPDATED_CUSTOMER_TO)
            .action(UPDATED_ACTION)
            .mergingEntity(UPDATED_MERGING_ENTITY)
            .timeStamp(UPDATED_TIME_STAMP);
        return merger;
    }

    @BeforeEach
    public void initTest() {
        merger = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerger() throws Exception {
        int databaseSizeBeforeCreate = mergerRepository.findAll().size();

        // Create the Merger
        restMergerMockMvc.perform(post("/api/mergers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merger)))
            .andExpect(status().isCreated());

        // Validate the Merger in the database
        List<Merger> mergerList = mergerRepository.findAll();
        assertThat(mergerList).hasSize(databaseSizeBeforeCreate + 1);
        Merger testMerger = mergerList.get(mergerList.size() - 1);
        assertThat(testMerger.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testMerger.getCustomerFrom()).isEqualTo(DEFAULT_CUSTOMER_FROM);
        assertThat(testMerger.getCustomerTo()).isEqualTo(DEFAULT_CUSTOMER_TO);
        assertThat(testMerger.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testMerger.getMergingEntity()).isEqualTo(DEFAULT_MERGING_ENTITY);
        assertThat(testMerger.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createMergerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mergerRepository.findAll().size();

        // Create the Merger with an existing ID
        merger.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMergerMockMvc.perform(post("/api/mergers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merger)))
            .andExpect(status().isBadRequest());

        // Validate the Merger in the database
        List<Merger> mergerList = mergerRepository.findAll();
        assertThat(mergerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMergers() throws Exception {
        // Initialize the database
        mergerRepository.saveAndFlush(merger);

        // Get all the mergerList
        restMergerMockMvc.perform(get("/api/mergers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merger.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerFrom").value(hasItem(DEFAULT_CUSTOMER_FROM.intValue())))
            .andExpect(jsonPath("$.[*].customerTo").value(hasItem(DEFAULT_CUSTOMER_TO.intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].mergingEntity").value(hasItem(DEFAULT_MERGING_ENTITY)))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(sameInstant(DEFAULT_TIME_STAMP))));
    }
    
    @Test
    @Transactional
    public void getMerger() throws Exception {
        // Initialize the database
        mergerRepository.saveAndFlush(merger);

        // Get the merger
        restMergerMockMvc.perform(get("/api/mergers/{id}", merger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merger.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.customerFrom").value(DEFAULT_CUSTOMER_FROM.intValue()))
            .andExpect(jsonPath("$.customerTo").value(DEFAULT_CUSTOMER_TO.intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.mergingEntity").value(DEFAULT_MERGING_ENTITY))
            .andExpect(jsonPath("$.timeStamp").value(sameInstant(DEFAULT_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingMerger() throws Exception {
        // Get the merger
        restMergerMockMvc.perform(get("/api/mergers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerger() throws Exception {
        // Initialize the database
        mergerRepository.saveAndFlush(merger);

        int databaseSizeBeforeUpdate = mergerRepository.findAll().size();

        // Update the merger
        Merger updatedMerger = mergerRepository.findById(merger.getId()).get();
        // Disconnect from session so that the updates on updatedMerger are not directly saved in db
        em.detach(updatedMerger);
        updatedMerger
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .customerFrom(UPDATED_CUSTOMER_FROM)
            .customerTo(UPDATED_CUSTOMER_TO)
            .action(UPDATED_ACTION)
            .mergingEntity(UPDATED_MERGING_ENTITY)
            .timeStamp(UPDATED_TIME_STAMP);

        restMergerMockMvc.perform(put("/api/mergers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerger)))
            .andExpect(status().isOk());

        // Validate the Merger in the database
        List<Merger> mergerList = mergerRepository.findAll();
        assertThat(mergerList).hasSize(databaseSizeBeforeUpdate);
        Merger testMerger = mergerList.get(mergerList.size() - 1);
        assertThat(testMerger.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testMerger.getCustomerFrom()).isEqualTo(UPDATED_CUSTOMER_FROM);
        assertThat(testMerger.getCustomerTo()).isEqualTo(UPDATED_CUSTOMER_TO);
        assertThat(testMerger.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testMerger.getMergingEntity()).isEqualTo(UPDATED_MERGING_ENTITY);
        assertThat(testMerger.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingMerger() throws Exception {
        int databaseSizeBeforeUpdate = mergerRepository.findAll().size();

        // Create the Merger

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMergerMockMvc.perform(put("/api/mergers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merger)))
            .andExpect(status().isBadRequest());

        // Validate the Merger in the database
        List<Merger> mergerList = mergerRepository.findAll();
        assertThat(mergerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMerger() throws Exception {
        // Initialize the database
        mergerRepository.saveAndFlush(merger);

        int databaseSizeBeforeDelete = mergerRepository.findAll().size();

        // Delete the merger
        restMergerMockMvc.perform(delete("/api/mergers/{id}", merger.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Merger> mergerList = mergerRepository.findAll();
        assertThat(mergerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
