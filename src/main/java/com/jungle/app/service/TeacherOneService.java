package com.jungle.app.service;

import com.jungle.app.service.dto.TeacherOneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TeacherOne.
 */
public interface TeacherOneService {

    /**
     * Save a teacherOne.
     *
     * @param teacherOneDTO the entity to save
     * @return the persisted entity
     */
    TeacherOneDTO save(TeacherOneDTO teacherOneDTO);

    /**
     * Get all the teacherOnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeacherOneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" teacherOne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TeacherOneDTO> findOne(Long id);

    /**
     * Delete the "id" teacherOne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the teacherOne corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeacherOneDTO> search(String query, Pageable pageable);
}
