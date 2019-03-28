package com.jungle.app.service.impl;

import com.jungle.app.service.StudentOneService;
import com.jungle.app.domain.StudentOne;
import com.jungle.app.repository.StudentOneRepository;
import com.jungle.app.repository.search.StudentOneSearchRepository;
import com.jungle.app.service.dto.StudentOneDTO;
import com.jungle.app.service.mapper.StudentOneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StudentOne.
 */
@Service
@Transactional
public class StudentOneServiceImpl implements StudentOneService {

    private final Logger log = LoggerFactory.getLogger(StudentOneServiceImpl.class);

    private final StudentOneRepository studentOneRepository;

    private final StudentOneMapper studentOneMapper;

    private final StudentOneSearchRepository studentOneSearchRepository;

    public StudentOneServiceImpl(StudentOneRepository studentOneRepository, StudentOneMapper studentOneMapper, StudentOneSearchRepository studentOneSearchRepository) {
        this.studentOneRepository = studentOneRepository;
        this.studentOneMapper = studentOneMapper;
        this.studentOneSearchRepository = studentOneSearchRepository;
    }

    /**
     * Save a studentOne.
     *
     * @param studentOneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentOneDTO save(StudentOneDTO studentOneDTO) {
        log.debug("Request to save StudentOne : {}", studentOneDTO);
        StudentOne studentOne = studentOneMapper.toEntity(studentOneDTO);
        studentOne = studentOneRepository.save(studentOne);
        StudentOneDTO result = studentOneMapper.toDto(studentOne);
        studentOneSearchRepository.save(studentOne);
        return result;
    }

    /**
     * Get all the studentOnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentOneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentOnes");
        return studentOneRepository.findAll(pageable)
            .map(studentOneMapper::toDto);
    }


    /**
     * Get one studentOne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentOneDTO> findOne(Long id) {
        log.debug("Request to get StudentOne : {}", id);
        return studentOneRepository.findById(id)
            .map(studentOneMapper::toDto);
    }

    /**
     * Delete the studentOne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentOne : {}", id);
        studentOneRepository.deleteById(id);
        studentOneSearchRepository.deleteById(id);
    }

    /**
     * Search for the studentOne corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentOneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentOnes for query {}", query);
        return studentOneSearchRepository.search(queryStringQuery(query), pageable)
            .map(studentOneMapper::toDto);
    }
}
