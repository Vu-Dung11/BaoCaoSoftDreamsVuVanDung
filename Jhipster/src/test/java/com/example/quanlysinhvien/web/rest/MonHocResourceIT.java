package com.example.quanlysinhvien.web.rest;

import static com.example.quanlysinhvien.domain.MonHocAsserts.*;
import static com.example.quanlysinhvien.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.quanlysinhvien.IntegrationTest;
import com.example.quanlysinhvien.domain.MonHoc;
import com.example.quanlysinhvien.repository.MonHocRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
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
 * Integration tests for the {@link MonHocResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MonHocResourceIT {

    private static final String DEFAULT_TEN_MON = "AAAAAAAAAA";
    private static final String UPDATED_TEN_MON = "BBBBBBBBBB";

    private static final Double DEFAULT_TY_LE_DIEM_QUA_TRINH = 1D;
    private static final Double UPDATED_TY_LE_DIEM_QUA_TRINH = 2D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/mon-hocs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonHocMockMvc;

    private MonHoc monHoc;

    private MonHoc insertedMonHoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonHoc createEntity() {
        return new MonHoc()
            .tenMon(DEFAULT_TEN_MON)
            .tyLeDiemQuaTrinh(DEFAULT_TY_LE_DIEM_QUA_TRINH)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonHoc createUpdatedEntity() {
        return new MonHoc()
            .tenMon(UPDATED_TEN_MON)
            .tyLeDiemQuaTrinh(UPDATED_TY_LE_DIEM_QUA_TRINH)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
    }

    @BeforeEach
    void initTest() {
        monHoc = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMonHoc != null) {
            monHocRepository.delete(insertedMonHoc);
            insertedMonHoc = null;
        }
    }

    @Test
    @Transactional
    void createMonHoc() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MonHoc
        var returnedMonHoc = om.readValue(
            restMonHocMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(monHoc)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MonHoc.class
        );

        // Validate the MonHoc in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMonHocUpdatableFieldsEquals(returnedMonHoc, getPersistedMonHoc(returnedMonHoc));

        insertedMonHoc = returnedMonHoc;
    }

    @Test
    @Transactional
    void createMonHocWithExistingId() throws Exception {
        // Create the MonHoc with an existing ID
        monHoc.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(monHoc)))
            .andExpect(status().isBadRequest());

        // Validate the MonHoc in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenMonIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        monHoc.setTenMon(null);

        // Create the MonHoc, which fails.

        restMonHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(monHoc)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTyLeDiemQuaTrinhIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        monHoc.setTyLeDiemQuaTrinh(null);

        // Create the MonHoc, which fails.

        restMonHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(monHoc)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMonHocs() throws Exception {
        // Initialize the database
        insertedMonHoc = monHocRepository.saveAndFlush(monHoc);

        // Get all the monHocList
        restMonHocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monHoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenMon").value(hasItem(DEFAULT_TEN_MON)))
            .andExpect(jsonPath("$.[*].tyLeDiemQuaTrinh").value(hasItem(DEFAULT_TY_LE_DIEM_QUA_TRINH)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getMonHoc() throws Exception {
        // Initialize the database
        insertedMonHoc = monHocRepository.saveAndFlush(monHoc);

        // Get the monHoc
        restMonHocMockMvc
            .perform(get(ENTITY_API_URL_ID, monHoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monHoc.getId().intValue()))
            .andExpect(jsonPath("$.tenMon").value(DEFAULT_TEN_MON))
            .andExpect(jsonPath("$.tyLeDiemQuaTrinh").value(DEFAULT_TY_LE_DIEM_QUA_TRINH))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMonHoc() throws Exception {
        // Get the monHoc
        restMonHocMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMonHoc() throws Exception {
        // Initialize the database
        insertedMonHoc = monHocRepository.saveAndFlush(monHoc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the monHoc
        MonHoc updatedMonHoc = monHocRepository.findById(monHoc.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMonHoc are not directly saved in db
        em.detach(updatedMonHoc);
        updatedMonHoc
            .tenMon(UPDATED_TEN_MON)
            .tyLeDiemQuaTrinh(UPDATED_TY_LE_DIEM_QUA_TRINH)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restMonHocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMonHoc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMonHoc))
            )
            .andExpect(status().isOk());

        // Validate the MonHoc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMonHocToMatchAllProperties(updatedMonHoc);
    }

    @Test
    @Transactional
    void putNonExistingMonHoc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        monHoc.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonHocMockMvc
            .perform(put(ENTITY_API_URL_ID, monHoc.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(monHoc)))
            .andExpect(status().isBadRequest());

        // Validate the MonHoc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMonHoc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        monHoc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonHocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(monHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonHoc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMonHoc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        monHoc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonHocMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(monHoc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonHoc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMonHocWithPatch() throws Exception {
        // Initialize the database
        insertedMonHoc = monHocRepository.saveAndFlush(monHoc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the monHoc using partial update
        MonHoc partialUpdatedMonHoc = new MonHoc();
        partialUpdatedMonHoc.setId(monHoc.getId());

        partialUpdatedMonHoc.tyLeDiemQuaTrinh(UPDATED_TY_LE_DIEM_QUA_TRINH);

        restMonHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonHoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMonHoc))
            )
            .andExpect(status().isOk());

        // Validate the MonHoc in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMonHocUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMonHoc, monHoc), getPersistedMonHoc(monHoc));
    }

    @Test
    @Transactional
    void fullUpdateMonHocWithPatch() throws Exception {
        // Initialize the database
        insertedMonHoc = monHocRepository.saveAndFlush(monHoc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the monHoc using partial update
        MonHoc partialUpdatedMonHoc = new MonHoc();
        partialUpdatedMonHoc.setId(monHoc.getId());

        partialUpdatedMonHoc
            .tenMon(UPDATED_TEN_MON)
            .tyLeDiemQuaTrinh(UPDATED_TY_LE_DIEM_QUA_TRINH)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restMonHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonHoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMonHoc))
            )
            .andExpect(status().isOk());

        // Validate the MonHoc in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMonHocUpdatableFieldsEquals(partialUpdatedMonHoc, getPersistedMonHoc(partialUpdatedMonHoc));
    }

    @Test
    @Transactional
    void patchNonExistingMonHoc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        monHoc.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, monHoc.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(monHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonHoc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMonHoc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        monHoc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(monHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonHoc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMonHoc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        monHoc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonHocMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(monHoc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonHoc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMonHoc() throws Exception {
        // Initialize the database
        insertedMonHoc = monHocRepository.saveAndFlush(monHoc);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the monHoc
        restMonHocMockMvc
            .perform(delete(ENTITY_API_URL_ID, monHoc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return monHocRepository.count();
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

    protected MonHoc getPersistedMonHoc(MonHoc monHoc) {
        return monHocRepository.findById(monHoc.getId()).orElseThrow();
    }

    protected void assertPersistedMonHocToMatchAllProperties(MonHoc expectedMonHoc) {
        assertMonHocAllPropertiesEquals(expectedMonHoc, getPersistedMonHoc(expectedMonHoc));
    }

    protected void assertPersistedMonHocToMatchUpdatableProperties(MonHoc expectedMonHoc) {
        assertMonHocAllUpdatablePropertiesEquals(expectedMonHoc, getPersistedMonHoc(expectedMonHoc));
    }
}
