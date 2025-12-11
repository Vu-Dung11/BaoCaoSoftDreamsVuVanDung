package com.example.quanlysinhvien.web.rest;

import com.example.quanlysinhvien.domain.MonHoc;
import com.example.quanlysinhvien.repository.MonHocRepository;
import com.example.quanlysinhvien.service.MonHocService;
import com.example.quanlysinhvien.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.example.quanlysinhvien.domain.MonHoc}.
 */
@RestController
@RequestMapping("/api/mon-hocs")
public class MonHocResource {

    private static final Logger LOG = LoggerFactory.getLogger(MonHocResource.class);

    private static final String ENTITY_NAME = "monHoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonHocService monHocService;

    private final MonHocRepository monHocRepository;

    public MonHocResource(MonHocService monHocService, MonHocRepository monHocRepository) {
        this.monHocService = monHocService;
        this.monHocRepository = monHocRepository;
    }

    /**
     * {@code POST  /mon-hocs} : Create a new monHoc.
     *
     * @param monHoc the monHoc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monHoc, or with status {@code 400 (Bad Request)} if the monHoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MonHoc> createMonHoc(@Valid @RequestBody MonHoc monHoc) throws URISyntaxException {
        LOG.debug("REST request to save MonHoc : {}", monHoc);
        if (monHoc.getId() != null) {
            throw new BadRequestAlertException("A new monHoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        monHoc = monHocService.save(monHoc);
        return ResponseEntity.created(new URI("/api/mon-hocs/" + monHoc.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, monHoc.getId().toString()))
            .body(monHoc);
    }

    /**
     * {@code PUT  /mon-hocs/:id} : Updates an existing monHoc.
     *
     * @param id the id of the monHoc to save.
     * @param monHoc the monHoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monHoc,
     * or with status {@code 400 (Bad Request)} if the monHoc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monHoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MonHoc> updateMonHoc(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MonHoc monHoc
    ) throws URISyntaxException {
        LOG.debug("REST request to update MonHoc : {}, {}", id, monHoc);
        if (monHoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monHoc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monHocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        monHoc = monHocService.update(monHoc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monHoc.getId().toString()))
            .body(monHoc);
    }

    /**
     * {@code PATCH  /mon-hocs/:id} : Partial updates given fields of an existing monHoc, field will ignore if it is null
     *
     * @param id the id of the monHoc to save.
     * @param monHoc the monHoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monHoc,
     * or with status {@code 400 (Bad Request)} if the monHoc is not valid,
     * or with status {@code 404 (Not Found)} if the monHoc is not found,
     * or with status {@code 500 (Internal Server Error)} if the monHoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MonHoc> partialUpdateMonHoc(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MonHoc monHoc
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MonHoc partially : {}, {}", id, monHoc);
        if (monHoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monHoc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monHocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MonHoc> result = monHocService.partialUpdate(monHoc);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monHoc.getId().toString())
        );
    }

    /**
     * {@code GET  /mon-hocs} : get all the monHocs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monHocs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MonHoc>> getAllMonHocs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MonHocs");
        Page<MonHoc> page = monHocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mon-hocs/:id} : get the "id" monHoc.
     *
     * @param id the id of the monHoc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monHoc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MonHoc> getMonHoc(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MonHoc : {}", id);
        Optional<MonHoc> monHoc = monHocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monHoc);
    }

    /**
     * {@code DELETE  /mon-hocs/:id} : delete the "id" monHoc.
     *
     * @param id the id of the monHoc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonHoc(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MonHoc : {}", id);
        monHocService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
