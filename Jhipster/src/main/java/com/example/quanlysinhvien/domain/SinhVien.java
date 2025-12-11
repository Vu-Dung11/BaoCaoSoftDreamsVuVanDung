package com.example.quanlysinhvien.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SinhVien.
 */
@Entity
@Table(name = "sinh_vien")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SinhVien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "ten_sv", length = 30, nullable = false)
    private String tenSv;

    @NotNull
    @Size(max = 10)
    @Column(name = "gioi_tinh", length = 10, nullable = false)
    private String gioiTinh;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Size(max = 30)
    @Column(name = "lop", length = 30)
    private String lop;

    @Size(max = 30)
    @Column(name = "khoa", length = 30)
    private String khoa;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SinhVien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenSv() {
        return this.tenSv;
    }

    public SinhVien tenSv(String tenSv) {
        this.setTenSv(tenSv);
        return this;
    }

    public void setTenSv(String tenSv) {
        this.tenSv = tenSv;
    }

    public String getGioiTinh() {
        return this.gioiTinh;
    }

    public SinhVien gioiTinh(String gioiTinh) {
        this.setGioiTinh(gioiTinh);
        return this;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public LocalDate getNgaySinh() {
        return this.ngaySinh;
    }

    public SinhVien ngaySinh(LocalDate ngaySinh) {
        this.setNgaySinh(ngaySinh);
        return this;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getLop() {
        return this.lop;
    }

    public SinhVien lop(String lop) {
        this.setLop(lop);
        return this;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getKhoa() {
        return this.khoa;
    }

    public SinhVien khoa(String khoa) {
        this.setKhoa(khoa);
        return this;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public SinhVien createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public SinhVien updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SinhVien)) {
            return false;
        }
        return getId() != null && getId().equals(((SinhVien) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SinhVien{" +
            "id=" + getId() +
            ", tenSv='" + getTenSv() + "'" +
            ", gioiTinh='" + getGioiTinh() + "'" +
            ", ngaySinh='" + getNgaySinh() + "'" +
            ", lop='" + getLop() + "'" +
            ", khoa='" + getKhoa() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
