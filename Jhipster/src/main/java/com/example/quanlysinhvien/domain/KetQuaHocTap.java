package com.example.quanlysinhvien.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A KetQuaHocTap.
 */
@Entity
@Table(name = "ket_qua_hoc_tap")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KetQuaHocTap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "diem_qua_trinh")
    private Double diemQuaTrinh;

    @Column(name = "diem_thanh_phan")
    private Double diemThanhPhan;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    private SinhVien sinhVien;

    @ManyToOne(optional = false)
    @NotNull
    private MonHoc monHoc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KetQuaHocTap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiemQuaTrinh() {
        return this.diemQuaTrinh;
    }

    public KetQuaHocTap diemQuaTrinh(Double diemQuaTrinh) {
        this.setDiemQuaTrinh(diemQuaTrinh);
        return this;
    }

    public void setDiemQuaTrinh(Double diemQuaTrinh) {
        this.diemQuaTrinh = diemQuaTrinh;
    }

    public Double getDiemThanhPhan() {
        return this.diemThanhPhan;
    }

    public KetQuaHocTap diemThanhPhan(Double diemThanhPhan) {
        this.setDiemThanhPhan(diemThanhPhan);
        return this;
    }

    public void setDiemThanhPhan(Double diemThanhPhan) {
        this.diemThanhPhan = diemThanhPhan;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public KetQuaHocTap createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public KetQuaHocTap updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SinhVien getSinhVien() {
        return this.sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public KetQuaHocTap sinhVien(SinhVien sinhVien) {
        this.setSinhVien(sinhVien);
        return this;
    }

    public MonHoc getMonHoc() {
        return this.monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public KetQuaHocTap monHoc(MonHoc monHoc) {
        this.setMonHoc(monHoc);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KetQuaHocTap)) {
            return false;
        }
        return getId() != null && getId().equals(((KetQuaHocTap) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KetQuaHocTap{" +
            "id=" + getId() +
            ", diemQuaTrinh=" + getDiemQuaTrinh() +
            ", diemThanhPhan=" + getDiemThanhPhan() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
