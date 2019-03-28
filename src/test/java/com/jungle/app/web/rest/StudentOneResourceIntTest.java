package com.jungle.app.web.rest;

import com.jungle.app.TestauditApp;

import com.jungle.app.domain.StudentOne;
import com.jungle.app.repository.StudentOneRepository;
import com.jungle.app.repository.search.StudentOneSearchRepository;
import com.jungle.app.service.StudentOneService;
import com.jungle.app.service.dto.StudentOneDTO;
import com.jungle.app.service.mapper.StudentOneMapper;
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
 * Test class for the StudentOneResource REST controller.
 *
 * @see StudentOneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestauditApp.class)
public class StudentOneResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StudentOneRepository studentOneRepository;

    @Autowired
    private StudentOneMapper studentOneMapper;

    @Autowired
    private StudentOneService studentOneService;

    /**
     * This repository is mocked in the com.jungle.app.repository.search test package.
     *
     * @see com.jungle.app.repository.search.StudentOneSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentOneSearchRepository mockStudentOneSearchRepository;

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

    private MockMvc restStudentOneMockMvc;

    private StudentOne studentOne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentOneResource studentOneResource = new StudentOneResource(studentOneService);
        this.restStudentOneMockMvc = MockMvcBuilders.standaloneSetup(studentOneResource)
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
    public static StudentOne createEntity(EntityManager em) {
        StudentOne studentOne = new StudentOne()
            .name(DEFAULT_NAME);
        return studentOne;
    }

    @Before
    public void initTest() {
        studentOne = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentOne() throws Exception {
        int databaseSizeBeforeCreate = studentOneRepository.findAll().size();

        // Create the StudentOne
        StudentOneDTO studentOneDTO = studentOneMapper.toDto(studentOne);
        restStudentOneMockMvc.perform(post("/api/student-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentOneDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentOne in the database
        List<StudentOne> studentOneList = studentOneRepository.findAll();
        assertThat(studentOneList).hasSize(databaseSizeBeforeCreate + 1);
        StudentOne testStudentOne = studentOneList.get(studentOneList.size() - 1);
        assertThat(testStudentOne.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the StudentOne in Elasticsearch
        verify(mockStudentOneSearchRepository, times(1)).save(testStudentOne);
    }

    @Test
    @Transactional
    public void createStudentOneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentOneRepository.findAll().size();

        // Create the StudentOne with an existing ID
        studentOne.setId(1L);
        StudentOneDTO studentOneDTO = studentOneMapper.toDto(studentOne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentOneMockMvc.perform(post("/api/student-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentOneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentOne in the database
        List<StudentOne> studentOneList = studentOneRepository.findAll();
        assertThat(studentOneList).hasSize(databaseSizeBeforeCreate);

        // Validate the StudentOne in Elasticsearch
        verify(mockStudentOneSearchRepository, times(0)).save(studentOne);
    }

    @Test
    @Transactional
    public void getAllStudentOnes() throws Exception {
        // Initialize the database
        studentOneRepository.saveAndFlush(studentOne);

        // Get all the studentOneList
        restStudentOneMockMvc.perform(get("/api/student-ones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentOne() throws Exception {
        // Initialize the database
        studentOneRepository.saveAndFlush(studentOne);

        // Get the studentOne
        restStudentOneMockMvc.perform(get("/api/student-ones/{id}", studentOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentOne.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentOne() throws Exception {
        // Get the studentOne
        restStudentOneMockMvc.perform(get("/api/student-ones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentOne() throws Exception {
        // Initialize the database
        studentOneRepository.saveAndFlush(studentOne);

        int databaseSizeBeforeUpdate = studentOneRepository.findAll().size();

        // Update the studentOne
        StudentOne updatedStudentOne = studentOneRepository.findById(studentOne.getId()).get();
        // Disconnect from session so that the updates on updatedStudentOne are not directly saved in db
        em.detach(updatedStudentOne);
        updatedStudentOne
            .name(UPDATED_NAME);
        StudentOneDTO studentOneDTO = studentOneMapper.toDto(updatedStudentOne);

        restStudentOneMockMvc.perform(put("/api/student-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentOneDTO)))
            .andExpect(status().isOk());

        // Validate the StudentOne in the database
        List<StudentOne> studentOneList = studentOneRepository.findAll();
        assertThat(studentOneList).hasSize(databaseSizeBeforeUpdate);
        StudentOne testStudentOne = studentOneList.get(studentOneList.size() - 1);
        assertThat(testStudentOne.getName()).isEqualTo(UPDATED_NAME);

        // Validate the StudentOne in Elasticsearch
        verify(mockStudentOneSearchRepository, times(1)).save(testStudentOne);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentOne() throws Exception {
        int databaseSizeBeforeUpdate = studentOneRepository.findAll().size();

        // Create the StudentOne
        StudentOneDTO studentOneDTO = studentOneMapper.toDto(studentOne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentOneMockMvc.perform(put("/api/student-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentOneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentOne in the database
        List<StudentOne> studentOneList = studentOneRepository.findAll();
        assertThat(studentOneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StudentOne in Elasticsearch
        verify(mockStudentOneSearchRepository, times(0)).save(studentOne);
    }

    @Test
    @Transactional
    public void deleteStudentOne() throws Exception {
        // Initialize the database
        studentOneRepository.saveAndFlush(studentOne);

        int databaseSizeBeforeDelete = studentOneRepository.findAll().size();

        // Delete the studentOne
        restStudentOneMockMvc.perform(delete("/api/student-ones/{id}", studentOne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentOne> studentOneList = studentOneRepository.findAll();
        assertThat(studentOneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StudentOne in Elasticsearch
        verify(mockStudentOneSearchRepository, times(1)).deleteById(studentOne.getId());
    }

    @Test
    @Transactional
    public void searchStudentOne() throws Exception {
        // Initialize the database
        studentOneRepository.saveAndFlush(studentOne);
        when(mockStudentOneSearchRepository.search(queryStringQuery("id:" + studentOne.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(studentOne), PageRequest.of(0, 1), 1));
        // Search the studentOne
        restStudentOneMockMvc.perform(get("/api/_search/student-ones?query=id:" + studentOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentOne.class);
        StudentOne studentOne1 = new StudentOne();
        studentOne1.setId(1L);
        StudentOne studentOne2 = new StudentOne();
        studentOne2.setId(studentOne1.getId());
        assertThat(studentOne1).isEqualTo(studentOne2);
        studentOne2.setId(2L);
        assertThat(studentOne1).isNotEqualTo(studentOne2);
        studentOne1.setId(null);
        assertThat(studentOne1).isNotEqualTo(studentOne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentOneDTO.class);
        StudentOneDTO studentOneDTO1 = new StudentOneDTO();
        studentOneDTO1.setId(1L);
        StudentOneDTO studentOneDTO2 = new StudentOneDTO();
        assertThat(studentOneDTO1).isNotEqualTo(studentOneDTO2);
        studentOneDTO2.setId(studentOneDTO1.getId());
        assertThat(studentOneDTO1).isEqualTo(studentOneDTO2);
        studentOneDTO2.setId(2L);
        assertThat(studentOneDTO1).isNotEqualTo(studentOneDTO2);
        studentOneDTO1.setId(null);
        assertThat(studentOneDTO1).isNotEqualTo(studentOneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentOneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentOneMapper.fromId(null)).isNull();
    }
}
