package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Concept;
import com.modis.edu.repository.ConceptRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ConceptResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConceptResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/concepts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ConceptRepository conceptRepository;

    @Autowired
    private MockMvc restConceptMockMvc;

    private Concept concept;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concept createEntity() {
        Concept concept = new Concept().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION);
        return concept;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concept createUpdatedEntity() {
        Concept concept = new Concept().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);
        return concept;
    }

    @BeforeEach
    public void initTest() {
        conceptRepository.deleteAll();
        concept = createEntity();
    }

    @Test
    void createConcept() throws Exception {
        int databaseSizeBeforeCreate = conceptRepository.findAll().size();
        // Create the Concept
        restConceptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isCreated());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeCreate + 1);
        Concept testConcept = conceptList.get(conceptList.size() - 1);
        assertThat(testConcept.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testConcept.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createConceptWithExistingId() throws Exception {
        // Create the Concept with an existing ID
        concept.setId("existing_id");

        int databaseSizeBeforeCreate = conceptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllConcepts() throws Exception {
        // Initialize the database
        conceptRepository.save(concept);

        // Get all the conceptList
        restConceptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concept.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getConcept() throws Exception {
        // Initialize the database
        conceptRepository.save(concept);

        // Get the concept
        restConceptMockMvc
            .perform(get(ENTITY_API_URL_ID, concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concept.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingConcept() throws Exception {
        // Get the concept
        restConceptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingConcept() throws Exception {
        // Initialize the database
        conceptRepository.save(concept);

        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();

        // Update the concept
        Concept updatedConcept = conceptRepository.findById(concept.getId()).get();
        updatedConcept.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restConceptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcept.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConcept))
            )
            .andExpect(status().isOk());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
        Concept testConcept = conceptList.get(conceptList.size() - 1);
        assertThat(testConcept.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConcept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingConcept() throws Exception {
        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();
        concept.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concept.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concept))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConcept() throws Exception {
        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();
        concept.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConceptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concept))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConcept() throws Exception {
        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();
        concept.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConceptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConceptWithPatch() throws Exception {
        // Initialize the database
        conceptRepository.save(concept);

        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();

        // Update the concept using partial update
        Concept partialUpdatedConcept = new Concept();
        partialUpdatedConcept.setId(concept.getId());

        partialUpdatedConcept.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restConceptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcept))
            )
            .andExpect(status().isOk());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
        Concept testConcept = conceptList.get(conceptList.size() - 1);
        assertThat(testConcept.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConcept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void fullUpdateConceptWithPatch() throws Exception {
        // Initialize the database
        conceptRepository.save(concept);

        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();

        // Update the concept using partial update
        Concept partialUpdatedConcept = new Concept();
        partialUpdatedConcept.setId(concept.getId());

        partialUpdatedConcept.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restConceptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcept))
            )
            .andExpect(status().isOk());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
        Concept testConcept = conceptList.get(conceptList.size() - 1);
        assertThat(testConcept.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConcept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingConcept() throws Exception {
        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();
        concept.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concept))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConcept() throws Exception {
        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();
        concept.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConceptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concept))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConcept() throws Exception {
        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();
        concept.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConceptMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConcept() throws Exception {
        // Initialize the database
        conceptRepository.save(concept);

        int databaseSizeBeforeDelete = conceptRepository.findAll().size();

        // Delete the concept
        restConceptMockMvc
            .perform(delete(ENTITY_API_URL_ID, concept.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
