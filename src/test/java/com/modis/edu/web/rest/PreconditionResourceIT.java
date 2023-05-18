package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Precondition;
import com.modis.edu.repository.PreconditionRepository;
import com.modis.edu.service.PreconditionService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PreconditionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PreconditionResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/preconditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PreconditionRepository preconditionRepository;

    @Mock
    private PreconditionRepository preconditionRepositoryMock;

    @Mock
    private PreconditionService preconditionServiceMock;

    @Autowired
    private MockMvc restPreconditionMockMvc;

    private Precondition precondition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Precondition createEntity() {
        Precondition precondition = new Precondition().title(DEFAULT_TITLE);
        return precondition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Precondition createUpdatedEntity() {
        Precondition precondition = new Precondition().title(UPDATED_TITLE);
        return precondition;
    }

    @BeforeEach
    public void initTest() {
        preconditionRepository.deleteAll();
        precondition = createEntity();
    }

    @Test
    void createPrecondition() throws Exception {
        int databaseSizeBeforeCreate = preconditionRepository.findAll().size();
        // Create the Precondition
        restPreconditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precondition)))
            .andExpect(status().isCreated());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeCreate + 1);
        Precondition testPrecondition = preconditionList.get(preconditionList.size() - 1);
        assertThat(testPrecondition.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void createPreconditionWithExistingId() throws Exception {
        // Create the Precondition with an existing ID
        precondition.setId("existing_id");

        int databaseSizeBeforeCreate = preconditionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreconditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precondition)))
            .andExpect(status().isBadRequest());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPreconditions() throws Exception {
        // Initialize the database
        preconditionRepository.save(precondition);

        // Get all the preconditionList
        restPreconditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precondition.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPreconditionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(preconditionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPreconditionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(preconditionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPreconditionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(preconditionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPreconditionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(preconditionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPrecondition() throws Exception {
        // Initialize the database
        preconditionRepository.save(precondition);

        // Get the precondition
        restPreconditionMockMvc
            .perform(get(ENTITY_API_URL_ID, precondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(precondition.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    void getNonExistingPrecondition() throws Exception {
        // Get the precondition
        restPreconditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPrecondition() throws Exception {
        // Initialize the database
        preconditionRepository.save(precondition);

        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();

        // Update the precondition
        Precondition updatedPrecondition = preconditionRepository.findById(precondition.getId()).get();
        updatedPrecondition.title(UPDATED_TITLE);

        restPreconditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrecondition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrecondition))
            )
            .andExpect(status().isOk());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
        Precondition testPrecondition = preconditionList.get(preconditionList.size() - 1);
        assertThat(testPrecondition.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void putNonExistingPrecondition() throws Exception {
        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();
        precondition.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreconditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, precondition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPrecondition() throws Exception {
        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();
        precondition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreconditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPrecondition() throws Exception {
        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();
        precondition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreconditionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precondition)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePreconditionWithPatch() throws Exception {
        // Initialize the database
        preconditionRepository.save(precondition);

        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();

        // Update the precondition using partial update
        Precondition partialUpdatedPrecondition = new Precondition();
        partialUpdatedPrecondition.setId(precondition.getId());

        restPreconditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecondition))
            )
            .andExpect(status().isOk());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
        Precondition testPrecondition = preconditionList.get(preconditionList.size() - 1);
        assertThat(testPrecondition.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void fullUpdatePreconditionWithPatch() throws Exception {
        // Initialize the database
        preconditionRepository.save(precondition);

        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();

        // Update the precondition using partial update
        Precondition partialUpdatedPrecondition = new Precondition();
        partialUpdatedPrecondition.setId(precondition.getId());

        partialUpdatedPrecondition.title(UPDATED_TITLE);

        restPreconditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecondition))
            )
            .andExpect(status().isOk());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
        Precondition testPrecondition = preconditionList.get(preconditionList.size() - 1);
        assertThat(testPrecondition.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void patchNonExistingPrecondition() throws Exception {
        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();
        precondition.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreconditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, precondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPrecondition() throws Exception {
        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();
        precondition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreconditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPrecondition() throws Exception {
        int databaseSizeBeforeUpdate = preconditionRepository.findAll().size();
        precondition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreconditionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(precondition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Precondition in the database
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePrecondition() throws Exception {
        // Initialize the database
        preconditionRepository.save(precondition);

        int databaseSizeBeforeDelete = preconditionRepository.findAll().size();

        // Delete the precondition
        restPreconditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, precondition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Precondition> preconditionList = preconditionRepository.findAll();
        assertThat(preconditionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
