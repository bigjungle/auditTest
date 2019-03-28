package com.jungle.app.web.rest;
import com.jungle.app.service.TeacherOneService;
import com.jungle.app.web.rest.errors.BadRequestAlertException;
import com.jungle.app.web.rest.util.HeaderUtil;
import com.jungle.app.web.rest.util.PaginationUtil;
import com.jungle.app.service.dto.TeacherOneDTO;
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
 * REST controller for managing TeacherOne.
 */
@RestController
@RequestMapping("/api")
public class TeacherOneResource {

    private final Logger log = LoggerFactory.getLogger(TeacherOneResource.class);

    private static final String ENTITY_NAME = "teacherOne";

    private final TeacherOneService teacherOneService;

    public TeacherOneResource(TeacherOneService teacherOneService) {
        this.teacherOneService = teacherOneService;
    }

    /**
     * POST  /teacher-ones : Create a new teacherOne.
     *
     * @param teacherOneDTO the teacherOneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teacherOneDTO, or with status 400 (Bad Request) if the teacherOne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teacher-ones")
    public ResponseEntity<TeacherOneDTO> createTeacherOne(@RequestBody TeacherOneDTO teacherOneDTO) throws URISyntaxException {
        log.debug("REST request to save TeacherOne : {}", teacherOneDTO);
        if (teacherOneDTO.getId() != null) {
            throw new BadRequestAlertException("A new teacherOne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeacherOneDTO result = teacherOneService.save(teacherOneDTO);
        return ResponseEntity.created(new URI("/api/teacher-ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teacher-ones : Updates an existing teacherOne.
     *
     * @param teacherOneDTO the teacherOneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teacherOneDTO,
     * or with status 400 (Bad Request) if the teacherOneDTO is not valid,
     * or with status 500 (Internal Server Error) if the teacherOneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teacher-ones")
    public ResponseEntity<TeacherOneDTO> updateTeacherOne(@RequestBody TeacherOneDTO teacherOneDTO) throws URISyntaxException {
        log.debug("REST request to update TeacherOne : {}", teacherOneDTO);
        if (teacherOneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeacherOneDTO result = teacherOneService.save(teacherOneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teacherOneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teacher-ones : get all the teacherOnes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teacherOnes in body
     */
    @GetMapping("/teacher-ones")
    public ResponseEntity<List<TeacherOneDTO>> getAllTeacherOnes(Pageable pageable) {
        log.debug("REST request to get a page of TeacherOnes");
        Page<TeacherOneDTO> page = teacherOneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teacher-ones");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /teacher-ones/:id : get the "id" teacherOne.
     *
     * @param id the id of the teacherOneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teacherOneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/teacher-ones/{id}")
    public ResponseEntity<TeacherOneDTO> getTeacherOne(@PathVariable Long id) {
        log.debug("REST request to get TeacherOne : {}", id);
        Optional<TeacherOneDTO> teacherOneDTO = teacherOneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teacherOneDTO);
    }

    /**
     * DELETE  /teacher-ones/:id : delete the "id" teacherOne.
     *
     * @param id the id of the teacherOneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teacher-ones/{id}")
    public ResponseEntity<Void> deleteTeacherOne(@PathVariable Long id) {
        log.debug("REST request to delete TeacherOne : {}", id);
        teacherOneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/teacher-ones?query=:query : search for the teacherOne corresponding
     * to the query.
     *
     * @param query the query of the teacherOne search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/teacher-ones")
    public ResponseEntity<List<TeacherOneDTO>> searchTeacherOnes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TeacherOnes for query {}", query);
        Page<TeacherOneDTO> page = teacherOneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/teacher-ones");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
