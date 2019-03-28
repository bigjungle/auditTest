package com.jungle.app.service;

import com.jungle.app.service.dto.StudentOneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StudentOne.
 */
public interface StudentOneService {

    /**
     * Save a studentOne.
     *
     * @param studentOneDTO the entity to save
     * @return the persisted entity
     */
    StudentOneDTO save(StudentOneDTO studentOneDTO);

    /**
     * Get all the studentOnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentOneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentOne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StudentOneDTO> findOne(Long id);

    /**
     * Delete the "id" studentOne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the studentOne corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentOneDTO> search(String query, Pageable pageable);
}
