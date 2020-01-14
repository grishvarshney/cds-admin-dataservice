package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.CdsdataserviceApp;
import com.cdsadmin.dataservice.domain.Systems;
import com.cdsadmin.dataservice.repository.SystemsRepository;
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
 * Integration tests for the {@link SystemsResource} REST controller.
 */
@SpringBootTest(classes = CdsdataserviceApp.class)
public class SystemsResourceIT {

    private static final String DEFAULT_SYSTEMS_ENTITY = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEMS_ENTITY = "BBBBBBBBBB";

    @Autowired
    private SystemsRepository systemsRepository;

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

    private MockMvc restSystemsMockMvc;

    private Systems systems;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemsResource systemsResource = new SystemsResource(systemsRepository);
        this.restSystemsMockMvc = MockMvcBuilders.standaloneSetup(systemsResource)
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
    public static Systems createEntity(EntityManager em) {
        Systems systems = new Systems()
            .systemsEntity(DEFAULT_SYSTEMS_ENTITY);
        return systems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Systems createUpdatedEntity(EntityManager em) {
        Systems systems = new Systems()
            .systemsEntity(UPDATED_SYSTEMS_ENTITY);
        return systems;
    }

    @BeforeEach
    public void initTest() {
        systems = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystems() throws Exception {
        int databaseSizeBeforeCreate = systemsRepository.findAll().size();

        // Create the Systems
        restSystemsMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systems)))
            .andExpect(status().isCreated());

        // Validate the Systems in the database
        List<Systems> systemsList = systemsRepository.findAll();
        assertThat(systemsList).hasSize(databaseSizeBeforeCreate + 1);
        Systems testSystems = systemsList.get(systemsList.size() - 1);
        assertThat(testSystems.getSystemsEntity()).isEqualTo(DEFAULT_SYSTEMS_ENTITY);
    }

    @Test
    @Transactional
    public void createSystemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemsRepository.findAll().size();

        // Create the Systems with an existing ID
        systems.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemsMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systems)))
            .andExpect(status().isBadRequest());

        // Validate the Systems in the database
        List<Systems> systemsList = systemsRepository.findAll();
        assertThat(systemsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSystems() throws Exception {
        // Initialize the database
        systemsRepository.saveAndFlush(systems);

        // Get all the systemsList
        restSystemsMockMvc.perform(get("/api/systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systems.getId().intValue())))
            .andExpect(jsonPath("$.[*].systemsEntity").value(hasItem(DEFAULT_SYSTEMS_ENTITY)));
    }
    
    @Test
    @Transactional
    public void getSystems() throws Exception {
        // Initialize the database
        systemsRepository.saveAndFlush(systems);

        // Get the systems
        restSystemsMockMvc.perform(get("/api/systems/{id}", systems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systems.getId().intValue()))
            .andExpect(jsonPath("$.systemsEntity").value(DEFAULT_SYSTEMS_ENTITY));
    }

    @Test
    @Transactional
    public void getNonExistingSystems() throws Exception {
        // Get the systems
        restSystemsMockMvc.perform(get("/api/systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystems() throws Exception {
        // Initialize the database
        systemsRepository.saveAndFlush(systems);

        int databaseSizeBeforeUpdate = systemsRepository.findAll().size();

        // Update the systems
        Systems updatedSystems = systemsRepository.findById(systems.getId()).get();
        // Disconnect from session so that the updates on updatedSystems are not directly saved in db
        em.detach(updatedSystems);
        updatedSystems
            .systemsEntity(UPDATED_SYSTEMS_ENTITY);

        restSystemsMockMvc.perform(put("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSystems)))
            .andExpect(status().isOk());

        // Validate the Systems in the database
        List<Systems> systemsList = systemsRepository.findAll();
        assertThat(systemsList).hasSize(databaseSizeBeforeUpdate);
        Systems testSystems = systemsList.get(systemsList.size() - 1);
        assertThat(testSystems.getSystemsEntity()).isEqualTo(UPDATED_SYSTEMS_ENTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingSystems() throws Exception {
        int databaseSizeBeforeUpdate = systemsRepository.findAll().size();

        // Create the Systems

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemsMockMvc.perform(put("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systems)))
            .andExpect(status().isBadRequest());

        // Validate the Systems in the database
        List<Systems> systemsList = systemsRepository.findAll();
        assertThat(systemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystems() throws Exception {
        // Initialize the database
        systemsRepository.saveAndFlush(systems);

        int databaseSizeBeforeDelete = systemsRepository.findAll().size();

        // Delete the systems
        restSystemsMockMvc.perform(delete("/api/systems/{id}", systems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Systems> systemsList = systemsRepository.findAll();
        assertThat(systemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
