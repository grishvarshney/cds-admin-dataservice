package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.CdsdataserviceApp;
import com.cdsadmin.dataservice.domain.PurposeType;
import com.cdsadmin.dataservice.repository.PurposeTypeRepository;
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
import java.util.List;

import static com.cdsadmin.dataservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PurposeTypeResource} REST controller.
 */
@SpringBootTest(classes = CdsdataserviceApp.class)
public class PurposeTypeResourceIT {

    private static final String DEFAULT_PURPOSE_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PurposeTypeRepository purposeTypeRepository;

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

    private MockMvc restPurposeTypeMockMvc;

    private PurposeType purposeType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurposeTypeResource purposeTypeResource = new PurposeTypeResource(purposeTypeRepository);
        this.restPurposeTypeMockMvc = MockMvcBuilders.standaloneSetup(purposeTypeResource)
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
    public static PurposeType createEntity(EntityManager em) {
        PurposeType purposeType = new PurposeType()
            .purposeTypeCode(DEFAULT_PURPOSE_TYPE_CODE)
            .purposeTypeDescription(DEFAULT_PURPOSE_TYPE_DESCRIPTION);
        return purposeType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurposeType createUpdatedEntity(EntityManager em) {
        PurposeType purposeType = new PurposeType()
            .purposeTypeCode(UPDATED_PURPOSE_TYPE_CODE)
            .purposeTypeDescription(UPDATED_PURPOSE_TYPE_DESCRIPTION);
        return purposeType;
    }

    @BeforeEach
    public void initTest() {
        purposeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurposeType() throws Exception {
        int databaseSizeBeforeCreate = purposeTypeRepository.findAll().size();

        // Create the PurposeType
        restPurposeTypeMockMvc.perform(post("/api/purpose-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purposeType)))
            .andExpect(status().isCreated());

        // Validate the PurposeType in the database
        List<PurposeType> purposeTypeList = purposeTypeRepository.findAll();
        assertThat(purposeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PurposeType testPurposeType = purposeTypeList.get(purposeTypeList.size() - 1);
        assertThat(testPurposeType.getPurposeTypeCode()).isEqualTo(DEFAULT_PURPOSE_TYPE_CODE);
        assertThat(testPurposeType.getPurposeTypeDescription()).isEqualTo(DEFAULT_PURPOSE_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPurposeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purposeTypeRepository.findAll().size();

        // Create the PurposeType with an existing ID
        purposeType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurposeTypeMockMvc.perform(post("/api/purpose-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purposeType)))
            .andExpect(status().isBadRequest());

        // Validate the PurposeType in the database
        List<PurposeType> purposeTypeList = purposeTypeRepository.findAll();
        assertThat(purposeTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurposeTypes() throws Exception {
        // Initialize the database
        purposeTypeRepository.saveAndFlush(purposeType);

        // Get all the purposeTypeList
        restPurposeTypeMockMvc.perform(get("/api/purpose-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purposeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].purposeTypeCode").value(hasItem(DEFAULT_PURPOSE_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].purposeTypeDescription").value(hasItem(DEFAULT_PURPOSE_TYPE_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPurposeType() throws Exception {
        // Initialize the database
        purposeTypeRepository.saveAndFlush(purposeType);

        // Get the purposeType
        restPurposeTypeMockMvc.perform(get("/api/purpose-types/{id}", purposeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purposeType.getId().intValue()))
            .andExpect(jsonPath("$.purposeTypeCode").value(DEFAULT_PURPOSE_TYPE_CODE))
            .andExpect(jsonPath("$.purposeTypeDescription").value(DEFAULT_PURPOSE_TYPE_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingPurposeType() throws Exception {
        // Get the purposeType
        restPurposeTypeMockMvc.perform(get("/api/purpose-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurposeType() throws Exception {
        // Initialize the database
        purposeTypeRepository.saveAndFlush(purposeType);

        int databaseSizeBeforeUpdate = purposeTypeRepository.findAll().size();

        // Update the purposeType
        PurposeType updatedPurposeType = purposeTypeRepository.findById(purposeType.getId()).get();
        // Disconnect from session so that the updates on updatedPurposeType are not directly saved in db
        em.detach(updatedPurposeType);
        updatedPurposeType
            .purposeTypeCode(UPDATED_PURPOSE_TYPE_CODE)
            .purposeTypeDescription(UPDATED_PURPOSE_TYPE_DESCRIPTION);

        restPurposeTypeMockMvc.perform(put("/api/purpose-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurposeType)))
            .andExpect(status().isOk());

        // Validate the PurposeType in the database
        List<PurposeType> purposeTypeList = purposeTypeRepository.findAll();
        assertThat(purposeTypeList).hasSize(databaseSizeBeforeUpdate);
        PurposeType testPurposeType = purposeTypeList.get(purposeTypeList.size() - 1);
        assertThat(testPurposeType.getPurposeTypeCode()).isEqualTo(UPDATED_PURPOSE_TYPE_CODE);
        assertThat(testPurposeType.getPurposeTypeDescription()).isEqualTo(UPDATED_PURPOSE_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPurposeType() throws Exception {
        int databaseSizeBeforeUpdate = purposeTypeRepository.findAll().size();

        // Create the PurposeType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurposeTypeMockMvc.perform(put("/api/purpose-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purposeType)))
            .andExpect(status().isBadRequest());

        // Validate the PurposeType in the database
        List<PurposeType> purposeTypeList = purposeTypeRepository.findAll();
        assertThat(purposeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurposeType() throws Exception {
        // Initialize the database
        purposeTypeRepository.saveAndFlush(purposeType);

        int databaseSizeBeforeDelete = purposeTypeRepository.findAll().size();

        // Delete the purposeType
        restPurposeTypeMockMvc.perform(delete("/api/purpose-types/{id}", purposeType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurposeType> purposeTypeList = purposeTypeRepository.findAll();
        assertThat(purposeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
