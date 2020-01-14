package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.CdsdataserviceApp;
import com.cdsadmin.dataservice.domain.CustomerFinancial;
import com.cdsadmin.dataservice.repository.CustomerFinancialRepository;
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
import java.time.ZoneId;
import java.util.List;

import static com.cdsadmin.dataservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerFinancialResource} REST controller.
 */
@SpringBootTest(classes = CdsdataserviceApp.class)
public class CustomerFinancialResourceIT {

    private static final String DEFAULT_CUSTOMER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_TOATL_ASSET = 1D;
    private static final Double UPDATED_TOATL_ASSET = 2D;

    private static final String DEFAULT_CMP_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_CMP_FLAG = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MTG_14_FAMILY_AMT = 1D;
    private static final Double UPDATED_MTG_14_FAMILY_AMT = 2D;

    private static final Double DEFAULT_TOTAL_RFHA = 1D;
    private static final Double UPDATED_TOTAL_RFHA = 2D;

    private static final Double DEFAULT_MVA_PERCENTAGE = 1D;
    private static final Double UPDATED_MVA_PERCENTAGE = 2D;

    private static final String DEFAULT_CAPITAL_COMPLIANT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_CAPITAL_COMPLIANT_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_WATCH_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_WATCH_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_CREDIT_SCORE = 1D;
    private static final Double UPDATED_CREDIT_SCORE = 2D;

    private static final LocalDate DEFAULT_WATCH_STATUS_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WATCH_STATUS_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT = 2D;

    private static final Double DEFAULT_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT = 1D;
    private static final Double UPDATED_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT = 2D;

    @Autowired
    private CustomerFinancialRepository customerFinancialRepository;

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

    private MockMvc restCustomerFinancialMockMvc;

    private CustomerFinancial customerFinancial;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerFinancialResource customerFinancialResource = new CustomerFinancialResource(customerFinancialRepository);
        this.restCustomerFinancialMockMvc = MockMvcBuilders.standaloneSetup(customerFinancialResource)
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
    public static CustomerFinancial createEntity(EntityManager em) {
        CustomerFinancial customerFinancial = new CustomerFinancial()
            .customerNumber(DEFAULT_CUSTOMER_NUMBER)
            .toatlAsset(DEFAULT_TOATL_ASSET)
            .cmpFlag(DEFAULT_CMP_FLAG)
            .updateDate(DEFAULT_UPDATE_DATE)
            .mtg14FamilyAmt(DEFAULT_MTG_14_FAMILY_AMT)
            .totalRFHA(DEFAULT_TOTAL_RFHA)
            .mvaPercentage(DEFAULT_MVA_PERCENTAGE)
            .capitalCompliantLevel(DEFAULT_CAPITAL_COMPLIANT_LEVEL)
            .watchStatus(DEFAULT_WATCH_STATUS)
            .creditScore(DEFAULT_CREDIT_SCORE)
            .watchStatusEffectiveDate(DEFAULT_WATCH_STATUS_EFFECTIVE_DATE)
            .totalEligibileCollateralAmount(DEFAULT_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT)
            .securityEligibileCollateralAmount(DEFAULT_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT);
        return customerFinancial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerFinancial createUpdatedEntity(EntityManager em) {
        CustomerFinancial customerFinancial = new CustomerFinancial()
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .toatlAsset(UPDATED_TOATL_ASSET)
            .cmpFlag(UPDATED_CMP_FLAG)
            .updateDate(UPDATED_UPDATE_DATE)
            .mtg14FamilyAmt(UPDATED_MTG_14_FAMILY_AMT)
            .totalRFHA(UPDATED_TOTAL_RFHA)
            .mvaPercentage(UPDATED_MVA_PERCENTAGE)
            .capitalCompliantLevel(UPDATED_CAPITAL_COMPLIANT_LEVEL)
            .watchStatus(UPDATED_WATCH_STATUS)
            .creditScore(UPDATED_CREDIT_SCORE)
            .watchStatusEffectiveDate(UPDATED_WATCH_STATUS_EFFECTIVE_DATE)
            .totalEligibileCollateralAmount(UPDATED_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT)
            .securityEligibileCollateralAmount(UPDATED_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT);
        return customerFinancial;
    }

    @BeforeEach
    public void initTest() {
        customerFinancial = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerFinancial() throws Exception {
        int databaseSizeBeforeCreate = customerFinancialRepository.findAll().size();

        // Create the CustomerFinancial
        restCustomerFinancialMockMvc.perform(post("/api/customer-financials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerFinancial)))
            .andExpect(status().isCreated());

        // Validate the CustomerFinancial in the database
        List<CustomerFinancial> customerFinancialList = customerFinancialRepository.findAll();
        assertThat(customerFinancialList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerFinancial testCustomerFinancial = customerFinancialList.get(customerFinancialList.size() - 1);
        assertThat(testCustomerFinancial.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testCustomerFinancial.getToatlAsset()).isEqualTo(DEFAULT_TOATL_ASSET);
        assertThat(testCustomerFinancial.getCmpFlag()).isEqualTo(DEFAULT_CMP_FLAG);
        assertThat(testCustomerFinancial.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testCustomerFinancial.getMtg14FamilyAmt()).isEqualTo(DEFAULT_MTG_14_FAMILY_AMT);
        assertThat(testCustomerFinancial.getTotalRFHA()).isEqualTo(DEFAULT_TOTAL_RFHA);
        assertThat(testCustomerFinancial.getMvaPercentage()).isEqualTo(DEFAULT_MVA_PERCENTAGE);
        assertThat(testCustomerFinancial.getCapitalCompliantLevel()).isEqualTo(DEFAULT_CAPITAL_COMPLIANT_LEVEL);
        assertThat(testCustomerFinancial.getWatchStatus()).isEqualTo(DEFAULT_WATCH_STATUS);
        assertThat(testCustomerFinancial.getCreditScore()).isEqualTo(DEFAULT_CREDIT_SCORE);
        assertThat(testCustomerFinancial.getWatchStatusEffectiveDate()).isEqualTo(DEFAULT_WATCH_STATUS_EFFECTIVE_DATE);
        assertThat(testCustomerFinancial.getTotalEligibileCollateralAmount()).isEqualTo(DEFAULT_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT);
        assertThat(testCustomerFinancial.getSecurityEligibileCollateralAmount()).isEqualTo(DEFAULT_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT);
    }

    @Test
    @Transactional
    public void createCustomerFinancialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerFinancialRepository.findAll().size();

        // Create the CustomerFinancial with an existing ID
        customerFinancial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerFinancialMockMvc.perform(post("/api/customer-financials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerFinancial)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerFinancial in the database
        List<CustomerFinancial> customerFinancialList = customerFinancialRepository.findAll();
        assertThat(customerFinancialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerFinancials() throws Exception {
        // Initialize the database
        customerFinancialRepository.saveAndFlush(customerFinancial);

        // Get all the customerFinancialList
        restCustomerFinancialMockMvc.perform(get("/api/customer-financials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerFinancial.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerNumber").value(hasItem(DEFAULT_CUSTOMER_NUMBER)))
            .andExpect(jsonPath("$.[*].toatlAsset").value(hasItem(DEFAULT_TOATL_ASSET.doubleValue())))
            .andExpect(jsonPath("$.[*].cmpFlag").value(hasItem(DEFAULT_CMP_FLAG)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].mtg14FamilyAmt").value(hasItem(DEFAULT_MTG_14_FAMILY_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRFHA").value(hasItem(DEFAULT_TOTAL_RFHA.doubleValue())))
            .andExpect(jsonPath("$.[*].mvaPercentage").value(hasItem(DEFAULT_MVA_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].capitalCompliantLevel").value(hasItem(DEFAULT_CAPITAL_COMPLIANT_LEVEL)))
            .andExpect(jsonPath("$.[*].watchStatus").value(hasItem(DEFAULT_WATCH_STATUS)))
            .andExpect(jsonPath("$.[*].creditScore").value(hasItem(DEFAULT_CREDIT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].watchStatusEffectiveDate").value(hasItem(DEFAULT_WATCH_STATUS_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalEligibileCollateralAmount").value(hasItem(DEFAULT_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].securityEligibileCollateralAmount").value(hasItem(DEFAULT_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerFinancial() throws Exception {
        // Initialize the database
        customerFinancialRepository.saveAndFlush(customerFinancial);

        // Get the customerFinancial
        restCustomerFinancialMockMvc.perform(get("/api/customer-financials/{id}", customerFinancial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerFinancial.getId().intValue()))
            .andExpect(jsonPath("$.customerNumber").value(DEFAULT_CUSTOMER_NUMBER))
            .andExpect(jsonPath("$.toatlAsset").value(DEFAULT_TOATL_ASSET.doubleValue()))
            .andExpect(jsonPath("$.cmpFlag").value(DEFAULT_CMP_FLAG))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.mtg14FamilyAmt").value(DEFAULT_MTG_14_FAMILY_AMT.doubleValue()))
            .andExpect(jsonPath("$.totalRFHA").value(DEFAULT_TOTAL_RFHA.doubleValue()))
            .andExpect(jsonPath("$.mvaPercentage").value(DEFAULT_MVA_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.capitalCompliantLevel").value(DEFAULT_CAPITAL_COMPLIANT_LEVEL))
            .andExpect(jsonPath("$.watchStatus").value(DEFAULT_WATCH_STATUS))
            .andExpect(jsonPath("$.creditScore").value(DEFAULT_CREDIT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.watchStatusEffectiveDate").value(DEFAULT_WATCH_STATUS_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.totalEligibileCollateralAmount").value(DEFAULT_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.securityEligibileCollateralAmount").value(DEFAULT_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerFinancial() throws Exception {
        // Get the customerFinancial
        restCustomerFinancialMockMvc.perform(get("/api/customer-financials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerFinancial() throws Exception {
        // Initialize the database
        customerFinancialRepository.saveAndFlush(customerFinancial);

        int databaseSizeBeforeUpdate = customerFinancialRepository.findAll().size();

        // Update the customerFinancial
        CustomerFinancial updatedCustomerFinancial = customerFinancialRepository.findById(customerFinancial.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerFinancial are not directly saved in db
        em.detach(updatedCustomerFinancial);
        updatedCustomerFinancial
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .toatlAsset(UPDATED_TOATL_ASSET)
            .cmpFlag(UPDATED_CMP_FLAG)
            .updateDate(UPDATED_UPDATE_DATE)
            .mtg14FamilyAmt(UPDATED_MTG_14_FAMILY_AMT)
            .totalRFHA(UPDATED_TOTAL_RFHA)
            .mvaPercentage(UPDATED_MVA_PERCENTAGE)
            .capitalCompliantLevel(UPDATED_CAPITAL_COMPLIANT_LEVEL)
            .watchStatus(UPDATED_WATCH_STATUS)
            .creditScore(UPDATED_CREDIT_SCORE)
            .watchStatusEffectiveDate(UPDATED_WATCH_STATUS_EFFECTIVE_DATE)
            .totalEligibileCollateralAmount(UPDATED_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT)
            .securityEligibileCollateralAmount(UPDATED_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT);

        restCustomerFinancialMockMvc.perform(put("/api/customer-financials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerFinancial)))
            .andExpect(status().isOk());

        // Validate the CustomerFinancial in the database
        List<CustomerFinancial> customerFinancialList = customerFinancialRepository.findAll();
        assertThat(customerFinancialList).hasSize(databaseSizeBeforeUpdate);
        CustomerFinancial testCustomerFinancial = customerFinancialList.get(customerFinancialList.size() - 1);
        assertThat(testCustomerFinancial.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testCustomerFinancial.getToatlAsset()).isEqualTo(UPDATED_TOATL_ASSET);
        assertThat(testCustomerFinancial.getCmpFlag()).isEqualTo(UPDATED_CMP_FLAG);
        assertThat(testCustomerFinancial.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testCustomerFinancial.getMtg14FamilyAmt()).isEqualTo(UPDATED_MTG_14_FAMILY_AMT);
        assertThat(testCustomerFinancial.getTotalRFHA()).isEqualTo(UPDATED_TOTAL_RFHA);
        assertThat(testCustomerFinancial.getMvaPercentage()).isEqualTo(UPDATED_MVA_PERCENTAGE);
        assertThat(testCustomerFinancial.getCapitalCompliantLevel()).isEqualTo(UPDATED_CAPITAL_COMPLIANT_LEVEL);
        assertThat(testCustomerFinancial.getWatchStatus()).isEqualTo(UPDATED_WATCH_STATUS);
        assertThat(testCustomerFinancial.getCreditScore()).isEqualTo(UPDATED_CREDIT_SCORE);
        assertThat(testCustomerFinancial.getWatchStatusEffectiveDate()).isEqualTo(UPDATED_WATCH_STATUS_EFFECTIVE_DATE);
        assertThat(testCustomerFinancial.getTotalEligibileCollateralAmount()).isEqualTo(UPDATED_TOTAL_ELIGIBILE_COLLATERAL_AMOUNT);
        assertThat(testCustomerFinancial.getSecurityEligibileCollateralAmount()).isEqualTo(UPDATED_SECURITY_ELIGIBILE_COLLATERAL_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerFinancial() throws Exception {
        int databaseSizeBeforeUpdate = customerFinancialRepository.findAll().size();

        // Create the CustomerFinancial

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerFinancialMockMvc.perform(put("/api/customer-financials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerFinancial)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerFinancial in the database
        List<CustomerFinancial> customerFinancialList = customerFinancialRepository.findAll();
        assertThat(customerFinancialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerFinancial() throws Exception {
        // Initialize the database
        customerFinancialRepository.saveAndFlush(customerFinancial);

        int databaseSizeBeforeDelete = customerFinancialRepository.findAll().size();

        // Delete the customerFinancial
        restCustomerFinancialMockMvc.perform(delete("/api/customer-financials/{id}", customerFinancial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerFinancial> customerFinancialList = customerFinancialRepository.findAll();
        assertThat(customerFinancialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
