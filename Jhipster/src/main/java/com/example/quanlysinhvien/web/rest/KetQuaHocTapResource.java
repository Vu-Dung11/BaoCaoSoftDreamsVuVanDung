package com.example.quanlysinhvien.web.rest;

import com.example.quanlysinhvien.domain.KetQuaHocTap;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;
import com.example.quanlysinhvien.service.KetQuaHocTapService;
import com.example.quanlysinhvien.service.dto.TruotDoDTO;
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
 * REST controller for managing {@link com.example.quanlysinhvien.domain.KetQuaHocTap}.
 */
@RestController
@RequestMapping("/api/ket-qua-hoc-taps")
public class KetQuaHocTapResource {

    private static final Logger LOG = LoggerFactory.getLogger(KetQuaHocTapResource.class);

    private static final String ENTITY_NAME = "ketQuaHocTap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KetQuaHocTapService ketQuaHocTapService;

    private final KetQuaHocTapRepository ketQuaHocTapRepository;




    public KetQuaHocTapResource(KetQuaHocTapService ketQuaHocTapService, KetQuaHocTapRepository ketQuaHocTapRepository) {
        this.ketQuaHocTapService = ketQuaHocTapService;
        this.ketQuaHocTapRepository = ketQuaHocTapRepository;
    }


    // xem kết quả trượt đỗ

    @GetMapping("/ket-qua/{id}")
    public ResponseEntity<List<TruotDoDTO>> getKetQua(@PathVariable Long id) {
        List<TruotDoDTO> ds = ketQuaHocTapService.getKetQuaMonHoc(id);
        return ResponseEntity.ok(ds);
    }















    /**
     * {@code POST  /ket-qua-hoc-taps} : Create a new ketQuaHocTap.
     *
     * @param ketQuaHocTap the ketQuaHocTap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ketQuaHocTap, or with status {@code 400 (Bad Request)} if the ketQuaHocTap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<KetQuaHocTap> createKetQuaHocTap(@Valid @RequestBody KetQuaHocTap ketQuaHocTap) throws URISyntaxException {
        LOG.debug("REST request to save KetQuaHocTap : {}", ketQuaHocTap);
        if (ketQuaHocTap.getId() != null) {
            throw new BadRequestAlertException("A new ketQuaHocTap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ketQuaHocTap = ketQuaHocTapService.save(ketQuaHocTap);
        return ResponseEntity.created(new URI("/api/ket-qua-hoc-taps/" + ketQuaHocTap.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ketQuaHocTap.getId().toString()))
            .body(ketQuaHocTap);
    }

    /**
     * {@code PUT  /ket-qua-hoc-taps/:id} : Updates an existing ketQuaHocTap.
     *
     * @param id the id of the ketQuaHocTap to save.
     * @param ketQuaHocTap the ketQuaHocTap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ketQuaHocTap,
     * or with status {@code 400 (Bad Request)} if the ketQuaHocTap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ketQuaHocTap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KetQuaHocTap> updateKetQuaHocTap(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KetQuaHocTap ketQuaHocTap
    ) throws URISyntaxException {
        LOG.debug("REST request to update KetQuaHocTap : {}, {}", id, ketQuaHocTap);
        if (ketQuaHocTap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ketQuaHocTap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ketQuaHocTapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ketQuaHocTap = ketQuaHocTapService.update(ketQuaHocTap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ketQuaHocTap.getId().toString()))
            .body(ketQuaHocTap);
    }

    /**
     * {@code PATCH  /ket-qua-hoc-taps/:id} : Partial updates given fields of an existing ketQuaHocTap, field will ignore if it is null
     *
     * @param id the id of the ketQuaHocTap to save.
     * @param ketQuaHocTap the ketQuaHocTap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ketQuaHocTap,
     * or with status {@code 400 (Bad Request)} if the ketQuaHocTap is not valid,
     * or with status {@code 404 (Not Found)} if the ketQuaHocTap is not found,
     * or with status {@code 500 (Internal Server Error)} if the ketQuaHocTap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KetQuaHocTap> partialUpdateKetQuaHocTap(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KetQuaHocTap ketQuaHocTap
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update KetQuaHocTap partially : {}, {}", id, ketQuaHocTap);
        if (ketQuaHocTap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ketQuaHocTap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ketQuaHocTapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KetQuaHocTap> result = ketQuaHocTapService.partialUpdate(ketQuaHocTap);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ketQuaHocTap.getId().toString())
        );
    }

    /**
     * {@code GET  /ket-qua-hoc-taps} : get all the ketQuaHocTaps.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ketQuaHocTaps in body.
     */
    @GetMapping("")
    public ResponseEntity<List<KetQuaHocTap>> getAllKetQuaHocTaps(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of KetQuaHocTaps");
        Page<KetQuaHocTap> page;
        if (eagerload) {
            page = ketQuaHocTapService.findAllWithEagerRelationships(pageable);
        } else {
            page = ketQuaHocTapService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ket-qua-hoc-taps/:id} : get the "id" ketQuaHocTap.
     *
     * @param id the id of the ketQuaHocTap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ketQuaHocTap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KetQuaHocTap> getKetQuaHocTap(@PathVariable("id") Long id) {
        LOG.debug("REST request to get KetQuaHocTap : {}", id);
        Optional<KetQuaHocTap> ketQuaHocTap = ketQuaHocTapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ketQuaHocTap);
    }

    /**
     * {@code DELETE  /ket-qua-hoc-taps/:id} : delete the "id" ketQuaHocTap.
     *
     * @param id the id of the ketQuaHocTap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKetQuaHocTap(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete KetQuaHocTap : {}", id);
        ketQuaHocTapService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
