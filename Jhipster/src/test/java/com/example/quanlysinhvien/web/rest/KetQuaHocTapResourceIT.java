package com.example.quanlysinhvien.web.rest;

import static com.example.quanlysinhvien.domain.KetQuaHocTapAsserts.*;
import static com.example.quanlysinhvien.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.quanlysinhvien.IntegrationTest;
import com.example.quanlysinhvien.domain.KetQuaHocTap;
import com.example.quanlysinhvien.domain.MonHoc;
import com.example.quanlysinhvien.domain.SinhVien;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;
import com.example.quanlysinhvien.service.KetQuaHocTapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KetQuaHocTapResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class KetQuaHocTapResourceIT {

    private static final Double DEFAULT_DIEM_QUA_TRINH = 1D;
    private static final Double UPDATED_DIEM_QUA_TRINH = 2D;

    private static final Double DEFAULT_DIEM_THANH_PHAN = 1D;
    private static final Double UPDATED_DIEM_THANH_PHAN = 2D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ket-qua-hoc-taps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KetQuaHocTapRepository ketQuaHocTapRepository;

    @Mock
    private KetQuaHocTapRepository ketQuaHocTapRepositoryMock;

    @Mock
    private KetQuaHocTapService ketQuaHocTapServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKetQuaHocTapMockMvc;

    private KetQuaHocTap ketQuaHocTap;

    private KetQuaHocTap insertedKetQuaHocTap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KetQuaHocTap createEntity(EntityManager em) {
        KetQuaHocTap ketQuaHocTap = new KetQuaHocTap()
            .diemQuaTrinh(DEFAULT_DIEM_QUA_TRINH)
            .diemThanhPhan(DEFAULT_DIEM_THANH_PHAN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        SinhVien sinhVien;
        if (TestUtil.findAll(em, SinhVien.class).isEmpty()) {
            sinhVien = SinhVienResourceIT.createEntity();
            em.persist(sinhVien);
            em.flush();
        } else {
            sinhVien = TestUtil.findAll(em, SinhVien.class).get(0);
        }
        ketQuaHocTap.setSinhVien(sinhVien);
        // Add required entity
        MonHoc monHoc;
        if (TestUtil.findAll(em, MonHoc.class).isEmpty()) {
            monHoc = MonHocResourceIT.createEntity();
            em.persist(monHoc);
            em.flush();
        } else {
            monHoc = TestUtil.findAll(em, MonHoc.class).get(0);
        }
        ketQuaHocTap.setMonHoc(monHoc);
        return ketQuaHocTap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KetQuaHocTap createUpdatedEntity(EntityManager em) {
        KetQuaHocTap updatedKetQuaHocTap = new KetQuaHocTap()
            .diemQuaTrinh(UPDATED_DIEM_QUA_TRINH)
            .diemThanhPhan(UPDATED_DIEM_THANH_PHAN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        SinhVien sinhVien;
        if (TestUtil.findAll(em, SinhVien.class).isEmpty()) {
            sinhVien = SinhVienResourceIT.createUpdatedEntity();
            em.persist(sinhVien);
            em.flush();
        } else {
            sinhVien = TestUtil.findAll(em, SinhVien.class).get(0);
        }
        updatedKetQuaHocTap.setSinhVien(sinhVien);
        // Add required entity
        MonHoc monHoc;
        if (TestUtil.findAll(em, MonHoc.class).isEmpty()) {
            monHoc = MonHocResourceIT.createUpdatedEntity();
            em.persist(monHoc);
            em.flush();
        } else {
            monHoc = TestUtil.findAll(em, MonHoc.class).get(0);
        }
        updatedKetQuaHocTap.setMonHoc(monHoc);
        return updatedKetQuaHocTap;
    }

    @BeforeEach
    void initTest() {
        ketQuaHocTap = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedKetQuaHocTap != null) {
            ketQuaHocTapRepository.delete(insertedKetQuaHocTap);
            insertedKetQuaHocTap = null;
        }
    }

    @Test
    @Transactional
    void createKetQuaHocTap() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the KetQuaHocTap
        var returnedKetQuaHocTap = om.readValue(
            restKetQuaHocTapMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ketQuaHocTap)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            KetQuaHocTap.class
        );

        // Validate the KetQuaHocTap in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKetQuaHocTapUpdatableFieldsEquals(returnedKetQuaHocTap, getPersistedKetQuaHocTap(returnedKetQuaHocTap));

        insertedKetQuaHocTap = returnedKetQuaHocTap;
    }

    @Test
    @Transactional
    void createKetQuaHocTapWithExistingId() throws Exception {
        // Create the KetQuaHocTap with an existing ID
        ketQuaHocTap.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKetQuaHocTapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ketQuaHocTap)))
            .andExpect(status().isBadRequest());

        // Validate the KetQuaHocTap in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKetQuaHocTaps() throws Exception {
        // Initialize the database
        insertedKetQuaHocTap = ketQuaHocTapRepository.saveAndFlush(ketQuaHocTap);

        // Get all the ketQuaHocTapList
        restKetQuaHocTapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ketQuaHocTap.getId().intValue())))
            .andExpect(jsonPath("$.[*].diemQuaTrinh").value(hasItem(DEFAULT_DIEM_QUA_TRINH)))
            .andExpect(jsonPath("$.[*].diemThanhPhan").value(hasItem(DEFAULT_DIEM_THANH_PHAN)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllKetQuaHocTapsWithEagerRelationshipsIsEnabled() throws Exception {
        when(ketQuaHocTapServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restKetQuaHocTapMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ketQuaHocTapServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllKetQuaHocTapsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ketQuaHocTapServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restKetQuaHocTapMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ketQuaHocTapRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getKetQuaHocTap() throws Exception {
        // Initialize the database
        insertedKetQuaHocTap = ketQuaHocTapRepository.saveAndFlush(ketQuaHocTap);

        // Get the ketQuaHocTap
        restKetQuaHocTapMockMvc
            .perform(get(ENTITY_API_URL_ID, ketQuaHocTap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ketQuaHocTap.getId().intValue()))
            .andExpect(jsonPath("$.diemQuaTrinh").value(DEFAULT_DIEM_QUA_TRINH))
            .andExpect(jsonPath("$.diemThanhPhan").value(DEFAULT_DIEM_THANH_PHAN))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingKetQuaHocTap() throws Exception {
        // Get the ketQuaHocTap
        restKetQuaHocTapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKetQuaHocTap() throws Exception {
        // Initialize the database
        insertedKetQuaHocTap = ketQuaHocTapRepository.saveAndFlush(ketQuaHocTap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ketQuaHocTap
        KetQuaHocTap updatedKetQuaHocTap = ketQuaHocTapRepository.findById(ketQuaHocTap.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKetQuaHocTap are not directly saved in db
        em.detach(updatedKetQuaHocTap);
        updatedKetQuaHocTap
            .diemQuaTrinh(UPDATED_DIEM_QUA_TRINH)
            .diemThanhPhan(UPDATED_DIEM_THANH_PHAN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restKetQuaHocTapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKetQuaHocTap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKetQuaHocTap))
            )
            .andExpect(status().isOk());

        // Validate the KetQuaHocTap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKetQuaHocTapToMatchAllProperties(updatedKetQuaHocTap);
    }

    @Test
    @Transactional
    void putNonExistingKetQuaHocTap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ketQuaHocTap.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKetQuaHocTapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ketQuaHocTap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ketQuaHocTap))
            )
            .andExpect(status().isBadRequest());

        // Validate the KetQuaHocTap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKetQuaHocTap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ketQuaHocTap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKetQuaHocTapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ketQuaHocTap))
            )
            .andExpect(status().isBadRequest());

        // Validate the KetQuaHocTap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKetQuaHocTap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ketQuaHocTap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKetQuaHocTapMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ketQuaHocTap)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KetQuaHocTap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKetQuaHocTapWithPatch() throws Exception {
        // Initialize the database
        insertedKetQuaHocTap = ketQuaHocTapRepository.saveAndFlush(ketQuaHocTap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ketQuaHocTap using partial update
        KetQuaHocTap partialUpdatedKetQuaHocTap = new KetQuaHocTap();
        partialUpdatedKetQuaHocTap.setId(ketQuaHocTap.getId());

        partialUpdatedKetQuaHocTap.diemQuaTrinh(UPDATED_DIEM_QUA_TRINH).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restKetQuaHocTapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKetQuaHocTap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKetQuaHocTap))
            )
            .andExpect(status().isOk());

        // Validate the KetQuaHocTap in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKetQuaHocTapUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKetQuaHocTap, ketQuaHocTap),
            getPersistedKetQuaHocTap(ketQuaHocTap)
        );
    }

    @Test
    @Transactional
    void fullUpdateKetQuaHocTapWithPatch() throws Exception {
        // Initialize the database
        insertedKetQuaHocTap = ketQuaHocTapRepository.saveAndFlush(ketQuaHocTap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ketQuaHocTap using partial update
        KetQuaHocTap partialUpdatedKetQuaHocTap = new KetQuaHocTap();
        partialUpdatedKetQuaHocTap.setId(ketQuaHocTap.getId());

        partialUpdatedKetQuaHocTap
            .diemQuaTrinh(UPDATED_DIEM_QUA_TRINH)
            .diemThanhPhan(UPDATED_DIEM_THANH_PHAN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restKetQuaHocTapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKetQuaHocTap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKetQuaHocTap))
            )
            .andExpect(status().isOk());

        // Validate the KetQuaHocTap in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKetQuaHocTapUpdatableFieldsEquals(partialUpdatedKetQuaHocTap, getPersistedKetQuaHocTap(partialUpdatedKetQuaHocTap));
    }

    @Test
    @Transactional
    void patchNonExistingKetQuaHocTap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ketQuaHocTap.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKetQuaHocTapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ketQuaHocTap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ketQuaHocTap))
            )
            .andExpect(status().isBadRequest());

        // Validate the KetQuaHocTap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKetQuaHocTap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ketQuaHocTap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKetQuaHocTapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ketQuaHocTap))
            )
            .andExpect(status().isBadRequest());

        // Validate the KetQuaHocTap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKetQuaHocTap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ketQuaHocTap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKetQuaHocTapMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ketQuaHocTap)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KetQuaHocTap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKetQuaHocTap() throws Exception {
        // Initialize the database
        insertedKetQuaHocTap = ketQuaHocTapRepository.saveAndFlush(ketQuaHocTap);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ketQuaHocTap
        restKetQuaHocTapMockMvc
            .perform(delete(ENTITY_API_URL_ID, ketQuaHocTap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ketQuaHocTapRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected KetQuaHocTap getPersistedKetQuaHocTap(KetQuaHocTap ketQuaHocTap) {
        return ketQuaHocTapRepository.findById(ketQuaHocTap.getId()).orElseThrow();
    }

    protected void assertPersistedKetQuaHocTapToMatchAllProperties(KetQuaHocTap expectedKetQuaHocTap) {
        assertKetQuaHocTapAllPropertiesEquals(expectedKetQuaHocTap, getPersistedKetQuaHocTap(expectedKetQuaHocTap));
    }

    protected void assertPersistedKetQuaHocTapToMatchUpdatableProperties(KetQuaHocTap expectedKetQuaHocTap) {
        assertKetQuaHocTapAllUpdatablePropertiesEquals(expectedKetQuaHocTap, getPersistedKetQuaHocTap(expectedKetQuaHocTap));
    }
}
