package com.example.quanlysinhvien.web.rest;

import static com.example.quanlysinhvien.domain.SinhVienAsserts.*;
import static com.example.quanlysinhvien.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.quanlysinhvien.IntegrationTest;
import com.example.quanlysinhvien.domain.SinhVien;
import com.example.quanlysinhvien.repository.SinhVienRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SinhVienResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SinhVienResourceIT {

    private static final String DEFAULT_TEN_SV = "AAAAAAAAAA";
    private static final String UPDATED_TEN_SV = "BBBBBBBBBB";

    private static final String DEFAULT_GIOI_TINH = "AAAAAAAAAA";
    private static final String UPDATED_GIOI_TINH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NGAY_SINH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NGAY_SINH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LOP = "AAAAAAAAAA";
    private static final String UPDATED_LOP = "BBBBBBBBBB";

    private static final String DEFAULT_KHOA = "AAAAAAAAAA";
    private static final String UPDATED_KHOA = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sinh-viens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSinhVienMockMvc;

    private SinhVien sinhVien;

    private SinhVien insertedSinhVien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SinhVien createEntity() {
        return new SinhVien()
            .tenSv(DEFAULT_TEN_SV)
            .gioiTinh(DEFAULT_GIOI_TINH)
            .ngaySinh(DEFAULT_NGAY_SINH)
            .lop(DEFAULT_LOP)
            .khoa(DEFAULT_KHOA)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SinhVien createUpdatedEntity() {
        return new SinhVien()
            .tenSv(UPDATED_TEN_SV)
            .gioiTinh(UPDATED_GIOI_TINH)
            .ngaySinh(UPDATED_NGAY_SINH)
            .lop(UPDATED_LOP)
            .khoa(UPDATED_KHOA)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
    }

    @BeforeEach
    void initTest() {
        sinhVien = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSinhVien != null) {
            sinhVienRepository.delete(insertedSinhVien);
            insertedSinhVien = null;
        }
    }

    @Test
    @Transactional
    void createSinhVien() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SinhVien
        var returnedSinhVien = om.readValue(
            restSinhVienMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sinhVien)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SinhVien.class
        );

        // Validate the SinhVien in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSinhVienUpdatableFieldsEquals(returnedSinhVien, getPersistedSinhVien(returnedSinhVien));

        insertedSinhVien = returnedSinhVien;
    }

    @Test
    @Transactional
    void createSinhVienWithExistingId() throws Exception {
        // Create the SinhVien with an existing ID
        sinhVien.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSinhVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sinhVien)))
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenSvIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sinhVien.setTenSv(null);

        // Create the SinhVien, which fails.

        restSinhVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sinhVien)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGioiTinhIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sinhVien.setGioiTinh(null);

        // Create the SinhVien, which fails.

        restSinhVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sinhVien)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSinhViens() throws Exception {
        // Initialize the database
        insertedSinhVien = sinhVienRepository.saveAndFlush(sinhVien);

        // Get all the sinhVienList
        restSinhVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinhVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenSv").value(hasItem(DEFAULT_TEN_SV)))
            .andExpect(jsonPath("$.[*].gioiTinh").value(hasItem(DEFAULT_GIOI_TINH)))
            .andExpect(jsonPath("$.[*].ngaySinh").value(hasItem(DEFAULT_NGAY_SINH.toString())))
            .andExpect(jsonPath("$.[*].lop").value(hasItem(DEFAULT_LOP)))
            .andExpect(jsonPath("$.[*].khoa").value(hasItem(DEFAULT_KHOA)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getSinhVien() throws Exception {
        // Initialize the database
        insertedSinhVien = sinhVienRepository.saveAndFlush(sinhVien);

        // Get the sinhVien
        restSinhVienMockMvc
            .perform(get(ENTITY_API_URL_ID, sinhVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sinhVien.getId().intValue()))
            .andExpect(jsonPath("$.tenSv").value(DEFAULT_TEN_SV))
            .andExpect(jsonPath("$.gioiTinh").value(DEFAULT_GIOI_TINH))
            .andExpect(jsonPath("$.ngaySinh").value(DEFAULT_NGAY_SINH.toString()))
            .andExpect(jsonPath("$.lop").value(DEFAULT_LOP))
            .andExpect(jsonPath("$.khoa").value(DEFAULT_KHOA))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSinhVien() throws Exception {
        // Get the sinhVien
        restSinhVienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSinhVien() throws Exception {
        // Initialize the database
        insertedSinhVien = sinhVienRepository.saveAndFlush(sinhVien);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sinhVien
        SinhVien updatedSinhVien = sinhVienRepository.findById(sinhVien.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSinhVien are not directly saved in db
        em.detach(updatedSinhVien);
        updatedSinhVien
            .tenSv(UPDATED_TEN_SV)
            .gioiTinh(UPDATED_GIOI_TINH)
            .ngaySinh(UPDATED_NGAY_SINH)
            .lop(UPDATED_LOP)
            .khoa(UPDATED_KHOA)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restSinhVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSinhVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSinhVien))
            )
            .andExpect(status().isOk());

        // Validate the SinhVien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSinhVienToMatchAllProperties(updatedSinhVien);
    }

    @Test
    @Transactional
    void putNonExistingSinhVien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sinhVien.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sinhVien.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sinhVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSinhVien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sinhVien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sinhVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSinhVien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sinhVien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sinhVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SinhVien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSinhVienWithPatch() throws Exception {
        // Initialize the database
        insertedSinhVien = sinhVienRepository.saveAndFlush(sinhVien);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sinhVien using partial update
        SinhVien partialUpdatedSinhVien = new SinhVien();
        partialUpdatedSinhVien.setId(sinhVien.getId());

        partialUpdatedSinhVien.ngaySinh(UPDATED_NGAY_SINH).khoa(UPDATED_KHOA).updatedAt(UPDATED_UPDATED_AT);

        restSinhVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSinhVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSinhVien))
            )
            .andExpect(status().isOk());

        // Validate the SinhVien in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSinhVienUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSinhVien, sinhVien), getPersistedSinhVien(sinhVien));
    }

    @Test
    @Transactional
    void fullUpdateSinhVienWithPatch() throws Exception {
        // Initialize the database
        insertedSinhVien = sinhVienRepository.saveAndFlush(sinhVien);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sinhVien using partial update
        SinhVien partialUpdatedSinhVien = new SinhVien();
        partialUpdatedSinhVien.setId(sinhVien.getId());

        partialUpdatedSinhVien
            .tenSv(UPDATED_TEN_SV)
            .gioiTinh(UPDATED_GIOI_TINH)
            .ngaySinh(UPDATED_NGAY_SINH)
            .lop(UPDATED_LOP)
            .khoa(UPDATED_KHOA)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restSinhVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSinhVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSinhVien))
            )
            .andExpect(status().isOk());

        // Validate the SinhVien in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSinhVienUpdatableFieldsEquals(partialUpdatedSinhVien, getPersistedSinhVien(partialUpdatedSinhVien));
    }

    @Test
    @Transactional
    void patchNonExistingSinhVien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sinhVien.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sinhVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sinhVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSinhVien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sinhVien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sinhVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSinhVien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sinhVien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sinhVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SinhVien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSinhVien() throws Exception {
        // Initialize the database
        insertedSinhVien = sinhVienRepository.saveAndFlush(sinhVien);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sinhVien
        restSinhVienMockMvc
            .perform(delete(ENTITY_API_URL_ID, sinhVien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sinhVienRepository.count();
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

    protected SinhVien getPersistedSinhVien(SinhVien sinhVien) {
        return sinhVienRepository.findById(sinhVien.getId()).orElseThrow();
    }

    protected void assertPersistedSinhVienToMatchAllProperties(SinhVien expectedSinhVien) {
        assertSinhVienAllPropertiesEquals(expectedSinhVien, getPersistedSinhVien(expectedSinhVien));
    }

    protected void assertPersistedSinhVienToMatchUpdatableProperties(SinhVien expectedSinhVien) {
        assertSinhVienAllUpdatablePropertiesEquals(expectedSinhVien, getPersistedSinhVien(expectedSinhVien));
    }
}
