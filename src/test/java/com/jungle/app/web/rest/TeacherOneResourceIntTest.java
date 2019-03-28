package com.jungle.app.web.rest;

import com.jungle.app.TestauditApp;

import com.jungle.app.domain.TeacherOne;
import com.jungle.app.repository.TeacherOneRepository;
import com.jungle.app.repository.search.TeacherOneSearchRepository;
import com.jungle.app.service.TeacherOneService;
import com.jungle.app.service.dto.TeacherOneDTO;
import com.jungle.app.service.mapper.TeacherOneMapper;
import com.jungle.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.jungle.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TeacherOneResource REST controller.
 *
 * @see TeacherOneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestauditApp.class)
public class TeacherOneResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TeacherOneRepository teacherOneRepository;

    @Autowired
    private TeacherOneMapper teacherOneMapper;

    @Autowired
    private TeacherOneService teacherOneService;

    /**
     * This repository is mocked in the com.jungle.app.repository.search test package.
     *
     * @see com.jungle.app.repository.search.TeacherOneSearchRepositoryMockConfiguration
     */
    @Autowired
    private TeacherOneSearchRepository mockTeacherOneSearchRepository;

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

    private MockMvc restTeacherOneMockMvc;

    private TeacherOne teacherOne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeacherOneResource teacherOneResource = new TeacherOneResource(teacherOneService);
        this.restTeacherOneMockMvc = MockMvcBuilders.standaloneSetup(teacherOneResource)
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
    public static TeacherOne createEntity(EntityManager em) {
        TeacherOne teacherOne = new TeacherOne()
            .name(DEFAULT_NAME);
        return teacherOne;
    }

    @Before
    public void initTest() {
        teacherOne = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeacherOne() throws Exception {
        int databaseSizeBeforeCreate = teacherOneRepository.findAll().size();

        // Create the TeacherOne
        TeacherOneDTO teacherOneDTO = teacherOneMapper.toDto(teacherOne);
        restTeacherOneMockMvc.perform(post("/api/teacher-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherOneDTO)))
            .andExpect(status().isCreated());

        // Validate the TeacherOne in the database
        List<TeacherOne> teacherOneList = teacherOneRepository.findAll();
        assertThat(teacherOneList).hasSize(databaseSizeBeforeCreate + 1);
        TeacherOne testTeacherOne = teacherOneList.get(teacherOneList.size() - 1);
        assertThat(testTeacherOne.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the TeacherOne in Elasticsearch
        verify(mockTeacherOneSearchRepository, times(1)).save(testTeacherOne);
    }

    @Test
    @Transactional
    public void createTeacherOneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teacherOneRepository.findAll().size();

        // Create the TeacherOne with an existing ID
        teacherOne.setId(1L);
        TeacherOneDTO teacherOneDTO = teacherOneMapper.toDto(teacherOne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherOneMockMvc.perform(post("/api/teacher-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherOneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeacherOne in the database
        List<TeacherOne> teacherOneList = teacherOneRepository.findAll();
        assertThat(teacherOneList).hasSize(databaseSizeBeforeCreate);

        // Validate the TeacherOne in Elasticsearch
        verify(mockTeacherOneSearchRepository, times(0)).save(teacherOne);
    }

    @Test
    @Transactional
    public void getAllTeacherOnes() throws Exception {
        // Initialize the database
        teacherOneRepository.saveAndFlush(teacherOne);

        // Get all the teacherOneList
        restTeacherOneMockMvc.perform(get("/api/teacher-ones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacherOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTeacherOne() throws Exception {
        // Initialize the database
        teacherOneRepository.saveAndFlush(teacherOne);

        // Get the teacherOne
        restTeacherOneMockMvc.perform(get("/api/teacher-ones/{id}", teacherOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teacherOne.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTeacherOne() throws Exception {
        // Get the teacherOne
        restTeacherOneMockMvc.perform(get("/api/teacher-ones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeacherOne() throws Exception {
        // Initialize the database
        teacherOneRepository.saveAndFlush(teacherOne);

        int databaseSizeBeforeUpdate = teacherOneRepository.findAll().size();

        // Update the teacherOne
        TeacherOne updatedTeacherOne = teacherOneRepository.findById(teacherOne.getId()).get();
        // Disconnect from session so that the updates on updatedTeacherOne are not directly saved in db
        em.detach(updatedTeacherOne);
        updatedTeacherOne
            .name(UPDATED_NAME);
        TeacherOneDTO teacherOneDTO = teacherOneMapper.toDto(updatedTeacherOne);

        restTeacherOneMockMvc.perform(put("/api/teacher-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherOneDTO)))
            .andExpect(status().isOk());

        // Validate the TeacherOne in the database
        List<TeacherOne> teacherOneList = teacherOneRepository.findAll();
        assertThat(teacherOneList).hasSize(databaseSizeBeforeUpdate);
        TeacherOne testTeacherOne = teacherOneList.get(teacherOneList.size() - 1);
        assertThat(testTeacherOne.getName()).isEqualTo(UPDATED_NAME);

        // Validate the TeacherOne in Elasticsearch
        verify(mockTeacherOneSearchRepository, times(1)).save(testTeacherOne);
    }

    @Test
    @Transactional
    public void updateNonExistingTeacherOne() throws Exception {
        int databaseSizeBeforeUpdate = teacherOneRepository.findAll().size();

        // Create the TeacherOne
        TeacherOneDTO teacherOneDTO = teacherOneMapper.toDto(teacherOne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeacherOneMockMvc.perform(put("/api/teacher-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherOneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeacherOne in the database
        List<TeacherOne> teacherOneList = teacherOneRepository.findAll();
        assertThat(teacherOneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TeacherOne in Elasticsearch
        verify(mockTeacherOneSearchRepository, times(0)).save(teacherOne);
    }

    @Test
    @Transactional
    public void deleteTeacherOne() throws Exception {
        // Initialize the database
        teacherOneRepository.saveAndFlush(teacherOne);

        int databaseSizeBeforeDelete = teacherOneRepository.findAll().size();

        // Delete the teacherOne
        restTeacherOneMockMvc.perform(delete("/api/teacher-ones/{id}", teacherOne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeacherOne> teacherOneList = teacherOneRepository.findAll();
        assertThat(teacherOneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TeacherOne in Elasticsearch
        verify(mockTeacherOneSearchRepository, times(1)).deleteById(teacherOne.getId());
    }

    @Test
    @Transactional
    public void searchTeacherOne() throws Exception {
        // Initialize the database
        teacherOneRepository.saveAndFlush(teacherOne);
        when(mockTeacherOneSearchRepository.search(queryStringQuery("id:" + teacherOne.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(teacherOne), PageRequest.of(0, 1), 1));
        // Search the teacherOne
        restTeacherOneMockMvc.perform(get("/api/_search/teacher-ones?query=id:" + teacherOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacherOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherOne.class);
        TeacherOne teacherOne1 = new TeacherOne();
        teacherOne1.setId(1L);
        TeacherOne teacherOne2 = new TeacherOne();
        teacherOne2.setId(teacherOne1.getId());
        assertThat(teacherOne1).isEqualTo(teacherOne2);
        teacherOne2.setId(2L);
        assertThat(teacherOne1).isNotEqualTo(teacherOne2);
        teacherOne1.setId(null);
        assertThat(teacherOne1).isNotEqualTo(teacherOne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherOneDTO.class);
        TeacherOneDTO teacherOneDTO1 = new TeacherOneDTO();
        teacherOneDTO1.setId(1L);
        TeacherOneDTO teacherOneDTO2 = new TeacherOneDTO();
        assertThat(teacherOneDTO1).isNotEqualTo(teacherOneDTO2);
        teacherOneDTO2.setId(teacherOneDTO1.getId());
        assertThat(teacherOneDTO1).isEqualTo(teacherOneDTO2);
        teacherOneDTO2.setId(2L);
        assertThat(teacherOneDTO1).isNotEqualTo(teacherOneDTO2);
        teacherOneDTO1.setId(null);
        assertThat(teacherOneDTO1).isNotEqualTo(teacherOneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teacherOneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teacherOneMapper.fromId(null)).isNull();
    }
}
