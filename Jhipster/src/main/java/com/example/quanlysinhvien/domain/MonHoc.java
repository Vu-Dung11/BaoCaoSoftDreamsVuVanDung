package com.example.quanlysinhvien.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MonHoc.
 */
@Entity
@Table(name = "mon_hoc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MonHoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "ten_mon", length = 30, nullable = false, unique = true)
    private String tenMon;

    @NotNull
    @Column(name = "ty_le_diem_qua_trinh", nullable = false)
    private Double tyLeDiemQuaTrinh;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MonHoc id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenMon() {
        return this.tenMon;
    }

    public MonHoc tenMon(String tenMon) {
        this.setTenMon(tenMon);
        return this;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public Double getTyLeDiemQuaTrinh() {
        return this.tyLeDiemQuaTrinh;
    }

    public MonHoc tyLeDiemQuaTrinh(Double tyLeDiemQuaTrinh) {
        this.setTyLeDiemQuaTrinh(tyLeDiemQuaTrinh);
        return this;
    }

    public void setTyLeDiemQuaTrinh(Double tyLeDiemQuaTrinh) {
        this.tyLeDiemQuaTrinh = tyLeDiemQuaTrinh;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public MonHoc createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public MonHoc updatedAt(Instant updatedAt) {
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
        if (!(o instanceof MonHoc)) {
            return false;
        }
        return getId() != null && getId().equals(((MonHoc) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonHoc{" +
            "id=" + getId() +
            ", tenMon='" + getTenMon() + "'" +
            ", tyLeDiemQuaTrinh=" + getTyLeDiemQuaTrinh() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
