package com.jungle.app.service.impl;

import com.jungle.app.service.TeacherOneService;
import com.jungle.app.domain.TeacherOne;
import com.jungle.app.repository.TeacherOneRepository;
import com.jungle.app.repository.search.TeacherOneSearchRepository;
import com.jungle.app.service.dto.TeacherOneDTO;
import com.jungle.app.service.mapper.TeacherOneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TeacherOne.
 */
@Service
@Transactional
public class TeacherOneServiceImpl implements TeacherOneService {

    private final Logger log = LoggerFactory.getLogger(TeacherOneServiceImpl.class);

    private final TeacherOneRepository teacherOneRepository;

    private final TeacherOneMapper teacherOneMapper;

    private final TeacherOneSearchRepository teacherOneSearchRepository;

    public TeacherOneServiceImpl(TeacherOneRepository teacherOneRepository, TeacherOneMapper teacherOneMapper, TeacherOneSearchRepository teacherOneSearchRepository) {
        this.teacherOneRepository = teacherOneRepository;
        this.teacherOneMapper = teacherOneMapper;
        this.teacherOneSearchRepository = teacherOneSearchRepository;
    }

    /**
     * Save a teacherOne.
     *
     * @param teacherOneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TeacherOneDTO save(TeacherOneDTO teacherOneDTO) {
        log.debug("Request to save TeacherOne : {}", teacherOneDTO);
        TeacherOne teacherOne = teacherOneMapper.toEntity(teacherOneDTO);
        teacherOne = teacherOneRepository.save(teacherOne);
        TeacherOneDTO result = teacherOneMapper.toDto(teacherOne);
        teacherOneSearchRepository.save(teacherOne);
        return result;
    }

    /**
     * Get all the teacherOnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherOneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeacherOnes");
        return teacherOneRepository.findAll(pageable)
            .map(teacherOneMapper::toDto);
    }


    /**
     * Get one teacherOne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeacherOneDTO> findOne(Long id) {
        log.debug("Request to get TeacherOne : {}", id);
        return teacherOneRepository.findById(id)
            .map(teacherOneMapper::toDto);
    }

    /**
     * Delete the teacherOne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeacherOne : {}", id);
        teacherOneRepository.deleteById(id);
        teacherOneSearchRepository.deleteById(id);
    }

    /**
     * Search for the teacherOne corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherOneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TeacherOnes for query {}", query);
        return teacherOneSearchRepository.search(queryStringQuery(query), pageable)
            .map(teacherOneMapper::toDto);
    }
}
