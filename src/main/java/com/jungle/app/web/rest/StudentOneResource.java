package com.jungle.app.web.rest;
import com.jungle.app.service.StudentOneService;
import com.jungle.app.web.rest.errors.BadRequestAlertException;
import com.jungle.app.web.rest.util.HeaderUtil;
import com.jungle.app.web.rest.util.PaginationUtil;
import com.jungle.app.service.dto.StudentOneDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing StudentOne.
 */
@RestController
@RequestMapping("/api")
public class StudentOneResource {

    private final Logger log = LoggerFactory.getLogger(StudentOneResource.class);

    private static final String ENTITY_NAME = "studentOne";

    private final StudentOneService studentOneService;

    public StudentOneResource(StudentOneService studentOneService) {
        this.studentOneService = studentOneService;
    }

    /**
     * POST  /student-ones : Create a new studentOne.
     *
     * @param studentOneDTO the studentOneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentOneDTO, or with status 400 (Bad Request) if the studentOne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-ones")
    public ResponseEntity<StudentOneDTO> createStudentOne(@RequestBody StudentOneDTO studentOneDTO) throws URISyntaxException {
        log.debug("REST request to save StudentOne : {}", studentOneDTO);
        if (studentOneDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentOne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentOneDTO result = studentOneService.save(studentOneDTO);
        return ResponseEntity.created(new URI("/api/student-ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-ones : Updates an existing studentOne.
     *
     * @param studentOneDTO the studentOneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentOneDTO,
     * or with status 400 (Bad Request) if the studentOneDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentOneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-ones")
    public ResponseEntity<StudentOneDTO> updateStudentOne(@RequestBody StudentOneDTO studentOneDTO) throws URISyntaxException {
        log.debug("REST request to update StudentOne : {}", studentOneDTO);
        if (studentOneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentOneDTO result = studentOneService.save(studentOneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentOneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-ones : get all the studentOnes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentOnes in body
     */
    @GetMapping("/student-ones")
    public ResponseEntity<List<StudentOneDTO>> getAllStudentOnes(Pageable pageable) {
        log.debug("REST request to get a page of StudentOnes");
        Page<StudentOneDTO> page = studentOneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-ones");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-ones/:id : get the "id" studentOne.
     *
     * @param id the id of the studentOneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentOneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-ones/{id}")
    public ResponseEntity<StudentOneDTO> getStudentOne(@PathVariable Long id) {
        log.debug("REST request to get StudentOne : {}", id);
        Optional<StudentOneDTO> studentOneDTO = studentOneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentOneDTO);
    }

    /**
     * DELETE  /student-ones/:id : delete the "id" studentOne.
     *
     * @param id the id of the studentOneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-ones/{id}")
    public ResponseEntity<Void> deleteStudentOne(@PathVariable Long id) {
        log.debug("REST request to delete StudentOne : {}", id);
        studentOneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/student-ones?query=:query : search for the studentOne corresponding
     * to the query.
     *
     * @param query the query of the studentOne search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/student-ones")
    public ResponseEntity<List<StudentOneDTO>> searchStudentOnes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StudentOnes for query {}", query);
        Page<StudentOneDTO> page = studentOneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/student-ones");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
