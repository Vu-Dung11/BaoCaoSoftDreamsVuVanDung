package com.example.quanlysinhvien.service;

import com.example.quanlysinhvien.domain.MonHoc;
import com.example.quanlysinhvien.repository.MonHocRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.example.quanlysinhvien.domain.MonHoc}.
 */
@Service
@Transactional
public class MonHocService {

    private static final Logger LOG = LoggerFactory.getLogger(MonHocService.class);

    private final MonHocRepository monHocRepository;

    public MonHocService(MonHocRepository monHocRepository) {
        this.monHocRepository = monHocRepository;
    }

    /**
     * Save a monHoc.
     *
     * @param monHoc the entity to save.
     * @return the persisted entity.
     */
    public MonHoc save(MonHoc monHoc) {
        LOG.debug("Request to save MonHoc : {}", monHoc);
        return monHocRepository.save(monHoc);
    }

    /**
     * Update a monHoc.
     *
     * @param monHoc the entity to save.
     * @return the persisted entity.
     */
    public MonHoc update(MonHoc monHoc) {
        LOG.debug("Request to update MonHoc : {}", monHoc);
        return monHocRepository.save(monHoc);
    }

    /**
     * Partially update a monHoc.
     *
     * @param monHoc the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MonHoc> partialUpdate(MonHoc monHoc) {
        LOG.debug("Request to partially update MonHoc : {}", monHoc);

        return monHocRepository
            .findById(monHoc.getId())
            .map(existingMonHoc -> {
                if (monHoc.getTenMon() != null) {
                    existingMonHoc.setTenMon(monHoc.getTenMon());
                }
                if (monHoc.getTyLeDiemQuaTrinh() != null) {
                    existingMonHoc.setTyLeDiemQuaTrinh(monHoc.getTyLeDiemQuaTrinh());
                }
                if (monHoc.getCreatedAt() != null) {
                    existingMonHoc.setCreatedAt(monHoc.getCreatedAt());
                }
                if (monHoc.getUpdatedAt() != null) {
                    existingMonHoc.setUpdatedAt(monHoc.getUpdatedAt());
                }

                return existingMonHoc;
            })
            .map(monHocRepository::save);
    }

    /**
     * Get all the monHocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MonHoc> findAll(Pageable pageable) {
        LOG.debug("Request to get all MonHocs");
        return monHocRepository.findAll(pageable);
    }

    /**
     * Get one monHoc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MonHoc> findOne(Long id) {
        LOG.debug("Request to get MonHoc : {}", id);
        return monHocRepository.findById(id);
    }

    /**
     * Delete the monHoc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete MonHoc : {}", id);
        monHocRepository.deleteById(id);
    }
}
