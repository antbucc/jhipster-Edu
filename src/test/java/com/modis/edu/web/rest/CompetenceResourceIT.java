package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Competence;
import com.modis.edu.domain.enumeration.CompetenceType;
import com.modis.edu.repository.CompetenceRepository;
import com.modis.edu.service.CompetenceService;
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
 * Integration tests for the {@link CompetenceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CompetenceResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final CompetenceType DEFAULT_TYPE = CompetenceType.SKILL;
    private static final CompetenceType UPDATED_TYPE = CompetenceType.KNOWLEDGE;

    private static final String ENTITY_API_URL = "/api/competences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CompetenceRepository competenceRepository;

    @Mock
    private CompetenceRepository competenceRepositoryMock;

    @Mock
    private CompetenceService competenceServiceMock;

    @Autowired
    private MockMvc restCompetenceMockMvc;

    private Competence competence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competence createEntity() {
        Competence competence = new Competence().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION).type(DEFAULT_TYPE);
        return competence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competence createUpdatedEntity() {
        Competence competence = new Competence().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE);
        return competence;
    }

    @BeforeEach
    public void initTest() {
        competenceRepository.deleteAll();
        competence = createEntity();
    }

    @Test
    void createCompetence() throws Exception {
        int databaseSizeBeforeCreate = competenceRepository.findAll().size();
        // Create the Competence
        restCompetenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competence)))
            .andExpect(status().isCreated());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeCreate + 1);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCompetence.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetence.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createCompetenceWithExistingId() throws Exception {
        // Create the Competence with an existing ID
        competence.setId("existing_id");

        int databaseSizeBeforeCreate = competenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competence)))
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCompetences() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        // Get all the competenceList
        restCompetenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competence.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompetencesWithEagerRelationshipsIsEnabled() throws Exception {
        when(competenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompetenceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(competenceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompetencesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(competenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompetenceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(competenceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getCompetence() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        // Get the competence
        restCompetenceMockMvc
            .perform(get(ENTITY_API_URL_ID, competence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competence.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    void getNonExistingCompetence() throws Exception {
        // Get the competence
        restCompetenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCompetence() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Update the competence
        Competence updatedCompetence = competenceRepository.findById(competence.getId()).get();
        updatedCompetence.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE);

        restCompetenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompetence))
            )
            .andExpect(status().isOk());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCompetence.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetence.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competence)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCompetenceWithPatch() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Update the competence using partial update
        Competence partialUpdatedCompetence = new Competence();
        partialUpdatedCompetence.setId(competence.getId());

        partialUpdatedCompetence.description(UPDATED_DESCRIPTION).type(UPDATED_TYPE);

        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetence))
            )
            .andExpect(status().isOk());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCompetence.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetence.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void fullUpdateCompetenceWithPatch() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Update the competence using partial update
        Competence partialUpdatedCompetence = new Competence();
        partialUpdatedCompetence.setId(competence.getId());

        partialUpdatedCompetence.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE);

        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetence))
            )
            .andExpect(status().isOk());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCompetence.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetence.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(competence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCompetence() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        int databaseSizeBeforeDelete = competenceRepository.findAll().size();

        // Delete the competence
        restCompetenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, competence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
