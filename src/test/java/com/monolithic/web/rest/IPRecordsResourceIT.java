package com.monolithic.web.rest;

import com.monolithic.MonolithicApp;
import com.monolithic.domain.IPRecords;
import com.monolithic.repository.IPRecordsRepository;
import com.monolithic.service.IPRecordsService;
import com.monolithic.service.dto.IPRecordsDTO;
import com.monolithic.service.mapper.IPRecordsMapper;
import com.monolithic.web.rest.errors.ExceptionTranslator;

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

import static com.monolithic.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link IPRecordsResource} REST controller.
 */
@SpringBootTest(classes = MonolithicApp.class)
public class IPRecordsResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_DEVICE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private IPRecordsRepository iPRecordsRepository;

    @Autowired
    private IPRecordsMapper iPRecordsMapper;

    @Autowired
    private IPRecordsService iPRecordsService;

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

    private MockMvc restIPRecordsMockMvc;

    private IPRecords iPRecords;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IPRecordsResource iPRecordsResource = new IPRecordsResource(iPRecordsService);
        this.restIPRecordsMockMvc = MockMvcBuilders.standaloneSetup(iPRecordsResource)
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
    public static IPRecords createEntity(EntityManager em) {
        IPRecords iPRecords = new IPRecords()
            .userId(DEFAULT_USER_ID)
            .device(DEFAULT_DEVICE)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .countryName(DEFAULT_COUNTRY_NAME)
            .cityName(DEFAULT_CITY_NAME)
            .status(DEFAULT_STATUS);
        return iPRecords;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IPRecords createUpdatedEntity(EntityManager em) {
        IPRecords iPRecords = new IPRecords()
            .userId(UPDATED_USER_ID)
            .device(UPDATED_DEVICE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .countryName(UPDATED_COUNTRY_NAME)
            .cityName(UPDATED_CITY_NAME)
            .status(UPDATED_STATUS);
        return iPRecords;
    }

    @BeforeEach
    public void initTest() {
        iPRecords = createEntity(em);
    }

    @Test
    @Transactional
    public void createIPRecords() throws Exception {
        int databaseSizeBeforeCreate = iPRecordsRepository.findAll().size();

        // Create the IPRecords
        IPRecordsDTO iPRecordsDTO = iPRecordsMapper.toDto(iPRecords);
        restIPRecordsMockMvc.perform(post("/api/ip-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iPRecordsDTO)))
            .andExpect(status().isCreated());

        // Validate the IPRecords in the database
        List<IPRecords> iPRecordsList = iPRecordsRepository.findAll();
        assertThat(iPRecordsList).hasSize(databaseSizeBeforeCreate + 1);
        IPRecords testIPRecords = iPRecordsList.get(iPRecordsList.size() - 1);
        assertThat(testIPRecords.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testIPRecords.getDevice()).isEqualTo(DEFAULT_DEVICE);
        assertThat(testIPRecords.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testIPRecords.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testIPRecords.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testIPRecords.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createIPRecordsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iPRecordsRepository.findAll().size();

        // Create the IPRecords with an existing ID
        iPRecords.setId(1L);
        IPRecordsDTO iPRecordsDTO = iPRecordsMapper.toDto(iPRecords);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIPRecordsMockMvc.perform(post("/api/ip-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iPRecordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IPRecords in the database
        List<IPRecords> iPRecordsList = iPRecordsRepository.findAll();
        assertThat(iPRecordsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = iPRecordsRepository.findAll().size();
        // set the field null
        iPRecords.setUserId(null);

        // Create the IPRecords, which fails.
        IPRecordsDTO iPRecordsDTO = iPRecordsMapper.toDto(iPRecords);

        restIPRecordsMockMvc.perform(post("/api/ip-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iPRecordsDTO)))
            .andExpect(status().isBadRequest());

        List<IPRecords> iPRecordsList = iPRecordsRepository.findAll();
        assertThat(iPRecordsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeviceIsRequired() throws Exception {
        int databaseSizeBeforeTest = iPRecordsRepository.findAll().size();
        // set the field null
        iPRecords.setDevice(null);

        // Create the IPRecords, which fails.
        IPRecordsDTO iPRecordsDTO = iPRecordsMapper.toDto(iPRecords);

        restIPRecordsMockMvc.perform(post("/api/ip-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iPRecordsDTO)))
            .andExpect(status().isBadRequest());

        List<IPRecords> iPRecordsList = iPRecordsRepository.findAll();
        assertThat(iPRecordsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = iPRecordsRepository.findAll().size();
        // set the field null
        iPRecords.setIpAddress(null);

        // Create the IPRecords, which fails.
        IPRecordsDTO iPRecordsDTO = iPRecordsMapper.toDto(iPRecords);

        restIPRecordsMockMvc.perform(post("/api/ip-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iPRecordsDTO)))
            .andExpect(status().isBadRequest());

        List<IPRecords> iPRecordsList = iPRecordsRepository.findAll();
        assertThat(iPRecordsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIPRecords() throws Exception {
        // Initialize the database
        iPRecordsRepository.saveAndFlush(iPRecords);

        // Get all the iPRecordsList
        restIPRecordsMockMvc.perform(get("/api/ip-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iPRecords.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].device").value(hasItem(DEFAULT_DEVICE.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getIPRecords() throws Exception {
        // Initialize the database
        iPRecordsRepository.saveAndFlush(iPRecords);

        // Get the iPRecords
        restIPRecordsMockMvc.perform(get("/api/ip-records/{id}", iPRecords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iPRecords.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.device").value(DEFAULT_DEVICE.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME.toString()))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIPRecords() throws Exception {
        // Get the iPRecords
        restIPRecordsMockMvc.perform(get("/api/ip-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIPRecords() throws Exception {
        // Initialize the database
        iPRecordsRepository.saveAndFlush(iPRecords);

        int databaseSizeBeforeUpdate = iPRecordsRepository.findAll().size();

        // Update the iPRecords
        IPRecords updatedIPRecords = iPRecordsRepository.findById(iPRecords.getId()).get();
        // Disconnect from session so that the updates on updatedIPRecords are not directly saved in db
        em.detach(updatedIPRecords);
        updatedIPRecords
            .userId(UPDATED_USER_ID)
            .device(UPDATED_DEVICE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .countryName(UPDATED_COUNTRY_NAME)
            .cityName(UPDATED_CITY_NAME)
            .status(UPDATED_STATUS);
        IPRecordsDTO iPRecordsDTO = iPRecordsMapper.toDto(updatedIPRecords);

        restIPRecordsMockMvc.perform(put("/api/ip-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iPRecordsDTO)))
            .andExpect(status().isOk());

        // Validate the IPRecords in the database
        List<IPRecords> iPRecordsList = iPRecordsRepository.findAll();
        assertThat(iPRecordsList).hasSize(databaseSizeBeforeUpdate);
        IPRecords testIPRecords = iPRecordsList.get(iPRecordsList.size() - 1);
        assertThat(testIPRecords.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testIPRecords.getDevice()).isEqualTo(UPDATED_DEVICE);
        assertThat(testIPRecords.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testIPRecords.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testIPRecords.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testIPRecords.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingIPRecords() throws Exception {
        int databaseSizeBeforeUpdate = iPRecordsRepository.findAll().size();

        // Create the IPRecords
        IPRecordsDTO iPRecordsDTO = iPRecordsMapper.toDto(iPRecords);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIPRecordsMockMvc.perform(put("/api/ip-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iPRecordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IPRecords in the database
        List<IPRecords> iPRecordsList = iPRecordsRepository.findAll();
        assertThat(iPRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIPRecords() throws Exception {
        // Initialize the database
        iPRecordsRepository.saveAndFlush(iPRecords);

        int databaseSizeBeforeDelete = iPRecordsRepository.findAll().size();

        // Delete the iPRecords
        restIPRecordsMockMvc.perform(delete("/api/ip-records/{id}", iPRecords.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IPRecords> iPRecordsList = iPRecordsRepository.findAll();
        assertThat(iPRecordsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IPRecords.class);
        IPRecords iPRecords1 = new IPRecords();
        iPRecords1.setId(1L);
        IPRecords iPRecords2 = new IPRecords();
        iPRecords2.setId(iPRecords1.getId());
        assertThat(iPRecords1).isEqualTo(iPRecords2);
        iPRecords2.setId(2L);
        assertThat(iPRecords1).isNotEqualTo(iPRecords2);
        iPRecords1.setId(null);
        assertThat(iPRecords1).isNotEqualTo(iPRecords2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IPRecordsDTO.class);
        IPRecordsDTO iPRecordsDTO1 = new IPRecordsDTO();
        iPRecordsDTO1.setId(1L);
        IPRecordsDTO iPRecordsDTO2 = new IPRecordsDTO();
        assertThat(iPRecordsDTO1).isNotEqualTo(iPRecordsDTO2);
        iPRecordsDTO2.setId(iPRecordsDTO1.getId());
        assertThat(iPRecordsDTO1).isEqualTo(iPRecordsDTO2);
        iPRecordsDTO2.setId(2L);
        assertThat(iPRecordsDTO1).isNotEqualTo(iPRecordsDTO2);
        iPRecordsDTO1.setId(null);
        assertThat(iPRecordsDTO1).isNotEqualTo(iPRecordsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iPRecordsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iPRecordsMapper.fromId(null)).isNull();
    }
}
