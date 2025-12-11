package com.example.quanlysinhvien.service;

import com.example.quanlysinhvien.domain.SinhVien;
import com.example.quanlysinhvien.repository.SinhVienRepository;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.example.quanlysinhvien.domain.SinhVien}.
 */
@Service
@Transactional
public class SinhVienService {

    private static final Logger LOG = LoggerFactory.getLogger(SinhVienService.class);

    private final SinhVienRepository sinhVienRepository;

    public SinhVienService(SinhVienRepository sinhVienRepository) {
        this.sinhVienRepository = sinhVienRepository;
    }

    /**
     * Save a sinhVien.
     *
     * @param sinhVien the entity to save.
     * @return the persisted entity.
     */
    public SinhVien save(SinhVien sinhVien) {
        LOG.debug("Request to save SinhVien : {}", sinhVien);
        return sinhVienRepository.save(sinhVien);
    }

    /**
     * Update a sinhVien.
     *
     * @param sinhVien the entity to save.
     * @return the persisted entity.
     */
    public SinhVien update(SinhVien sinhVien) {
        LOG.debug("Request to update SinhVien : {}", sinhVien);
        return sinhVienRepository.save(sinhVien);
    }

    /**
     * Partially update a sinhVien.
     *
     * @param sinhVien the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SinhVien> partialUpdate(SinhVien sinhVien) {
        LOG.debug("Request to partially update SinhVien : {}", sinhVien);

        return sinhVienRepository
            .findById(sinhVien.getId())
            .map(existingSinhVien -> {
                if (sinhVien.getTenSv() != null) {
                    existingSinhVien.setTenSv(sinhVien.getTenSv());
                }
                if (sinhVien.getGioiTinh() != null) {
                    existingSinhVien.setGioiTinh(sinhVien.getGioiTinh());
                }
                if (sinhVien.getNgaySinh() != null) {
                    existingSinhVien.setNgaySinh(sinhVien.getNgaySinh());
                }
                if (sinhVien.getLop() != null) {
                    existingSinhVien.setLop(sinhVien.getLop());
                }
                if (sinhVien.getKhoa() != null) {
                    existingSinhVien.setKhoa(sinhVien.getKhoa());
                }
                if (sinhVien.getCreatedAt() != null) {
                    existingSinhVien.setCreatedAt(sinhVien.getCreatedAt());
                }
                if (sinhVien.getUpdatedAt() != null) {
                    existingSinhVien.setUpdatedAt(sinhVien.getUpdatedAt());
                }

                return existingSinhVien;
            })
            .map(sinhVienRepository::save);
    }

    /**
     * Get all the sinhViens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SinhVien> findAll(Pageable pageable) {
        LOG.debug("Request to get all SinhViens");
        return sinhVienRepository.findAll(pageable);
    }


    public List<String> findMonHocDangKyByMaSV(Long masv) {
        var listMH = sinhVienRepository.findMonHocDangKyByMaSV(masv);
        return listMH;
    }
    /**
     * Get one sinhVien by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SinhVien> findOne(Long id) {
        LOG.debug("Request to get SinhVien : {}", id);
        return sinhVienRepository.findById(id);
    }

    /**
     * Delete the sinhVien by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SinhVien : {}", id);
        sinhVienRepository.deleteById(id);
    }





}
