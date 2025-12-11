package com.example.quanlysinhvien.service;

import com.example.quanlysinhvien.domain.KetQuaHocTap;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.quanlysinhvien.service.dto.TruotDoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.example.quanlysinhvien.domain.KetQuaHocTap}.
 */
@Service
@Transactional
public class KetQuaHocTapService {

    private static final Logger LOG = LoggerFactory.getLogger(KetQuaHocTapService.class);

    private final KetQuaHocTapRepository ketQuaHocTapRepository;

    public KetQuaHocTapService(KetQuaHocTapRepository ketQuaHocTapRepository) {
        this.ketQuaHocTapRepository = ketQuaHocTapRepository;
    }






    public List<TruotDoDTO> getKetQuaMonHoc(Long maSv) {
        var data = ketQuaHocTapRepository.findKetQuaBySinhVien(maSv);
        List<TruotDoDTO> result = new ArrayList<>();
        for (Object[] row : data) {
            String tenMon = (String) row[2];
            double diemQT = row[0] != null ? ((Number) row[0]).doubleValue() : 0;
            double diemTP = row[1] != null ? ((Number) row[1]).doubleValue() : 0;
            double tyLeQT = row[3] != null ? ((Number) row[3]).doubleValue() : 0.0;

            // Công thức tính điểm cuối kỳ
            double diemCK = diemQT * tyLeQT + diemTP * (1 - tyLeQT);

            boolean isDo = diemCK >= 5;

            TruotDoDTO dto = new TruotDoDTO();
            dto.setTenMon(tenMon);
            dto.setDiemQuaTrinh(diemQT);
            dto.setDiemThanhPhan(diemTP);
            dto.setDiemCuoiKy(diemCK);
            dto.setDo(isDo);
            result.add(dto);
        }

        return result;
    }






    /**
     * Save a ketQuaHocTap.
     *
     * @param ketQuaHocTap the entity to save.
     * @return the persisted entity.
     */
    public KetQuaHocTap save(KetQuaHocTap ketQuaHocTap) {
        LOG.debug("Request to save KetQuaHocTap : {}", ketQuaHocTap);
        return ketQuaHocTapRepository.save(ketQuaHocTap);
    }

    /**
     * Update a ketQuaHocTap.
     *
     * @param ketQuaHocTap the entity to save.
     * @return the persisted entity.
     */
    public KetQuaHocTap update(KetQuaHocTap ketQuaHocTap) {
        LOG.debug("Request to update KetQuaHocTap : {}", ketQuaHocTap);
        return ketQuaHocTapRepository.save(ketQuaHocTap);
    }

    /**
     * Partially update a ketQuaHocTap.
     *
     * @param ketQuaHocTap the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<KetQuaHocTap> partialUpdate(KetQuaHocTap ketQuaHocTap) {
        LOG.debug("Request to partially update KetQuaHocTap : {}", ketQuaHocTap);

        return ketQuaHocTapRepository
            .findById(ketQuaHocTap.getId())
            .map(existingKetQuaHocTap -> {
                if (ketQuaHocTap.getDiemQuaTrinh() != null) {
                    existingKetQuaHocTap.setDiemQuaTrinh(ketQuaHocTap.getDiemQuaTrinh());
                }
                if (ketQuaHocTap.getDiemThanhPhan() != null) {
                    existingKetQuaHocTap.setDiemThanhPhan(ketQuaHocTap.getDiemThanhPhan());
                }
                if (ketQuaHocTap.getCreatedAt() != null) {
                    existingKetQuaHocTap.setCreatedAt(ketQuaHocTap.getCreatedAt());
                }
                if (ketQuaHocTap.getUpdatedAt() != null) {
                    existingKetQuaHocTap.setUpdatedAt(ketQuaHocTap.getUpdatedAt());
                }

                return existingKetQuaHocTap;
            })
            .map(ketQuaHocTapRepository::save);
    }

    /**
     * Get all the ketQuaHocTaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<KetQuaHocTap> findAll(Pageable pageable) {
        LOG.debug("Request to get all KetQuaHocTaps");
        return ketQuaHocTapRepository.findAll(pageable);
    }

    /**
     * Get all the ketQuaHocTaps with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<KetQuaHocTap> findAllWithEagerRelationships(Pageable pageable) {
        return ketQuaHocTapRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one ketQuaHocTap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<KetQuaHocTap> findOne(Long id) {
        LOG.debug("Request to get KetQuaHocTap : {}", id);
        return ketQuaHocTapRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the ketQuaHocTap by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete KetQuaHocTap : {}", id);
        ketQuaHocTapRepository.deleteById(id);
    }
}
