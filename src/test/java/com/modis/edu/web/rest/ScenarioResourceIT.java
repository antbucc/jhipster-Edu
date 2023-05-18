package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Scenario;
import com.modis.edu.domain.enumeration.Language;
import com.modis.edu.repository.ScenarioRepository;
import com.modis.edu.service.ScenarioService;
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
 * Integration tests for the {@link ScenarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ScenarioResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.ITALIAN;

    private static final String ENTITY_API_URL = "/api/scenarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Mock
    private ScenarioRepository scenarioRepositoryMock;

    @Mock
    private ScenarioService scenarioServiceMock;

    @Autowired
    private MockMvc restScenarioMockMvc;

    private Scenario scenario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scenario createEntity() {
        Scenario scenario = new Scenario().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION).language(DEFAULT_LANGUAGE);
        return scenario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scenario createUpdatedEntity() {
        Scenario scenario = new Scenario().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);
        return scenario;
    }

    @BeforeEach
    public void initTest() {
        scenarioRepository.deleteAll();
        scenario = createEntity();
    }

    @Test
    void createScenario() throws Exception {
        int databaseSizeBeforeCreate = scenarioRepository.findAll().size();
        // Create the Scenario
        restScenarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scenario)))
            .andExpect(status().isCreated());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeCreate + 1);
        Scenario testScenario = scenarioList.get(scenarioList.size() - 1);
        assertThat(testScenario.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testScenario.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testScenario.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    void createScenarioWithExistingId() throws Exception {
        // Create the Scenario with an existing ID
        scenario.setId("existing_id");

        int databaseSizeBeforeCreate = scenarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScenarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scenario)))
            .andExpect(status().isBadRequest());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllScenarios() throws Exception {
        // Initialize the database
        scenarioRepository.save(scenario);

        // Get all the scenarioList
        restScenarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenario.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllScenariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(scenarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restScenarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(scenarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllScenariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(scenarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restScenarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(scenarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getScenario() throws Exception {
        // Initialize the database
        scenarioRepository.save(scenario);

        // Get the scenario
        restScenarioMockMvc
            .perform(get(ENTITY_API_URL_ID, scenario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scenario.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    void getNonExistingScenario() throws Exception {
        // Get the scenario
        restScenarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingScenario() throws Exception {
        // Initialize the database
        scenarioRepository.save(scenario);

        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();

        // Update the scenario
        Scenario updatedScenario = scenarioRepository.findById(scenario.getId()).get();
        updatedScenario.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restScenarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScenario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScenario))
            )
            .andExpect(status().isOk());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
        Scenario testScenario = scenarioList.get(scenarioList.size() - 1);
        assertThat(testScenario.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testScenario.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testScenario.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    void putNonExistingScenario() throws Exception {
        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();
        scenario.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScenarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scenario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scenario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchScenario() throws Exception {
        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();
        scenario.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScenarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scenario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamScenario() throws Exception {
        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();
        scenario.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScenarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scenario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateScenarioWithPatch() throws Exception {
        // Initialize the database
        scenarioRepository.save(scenario);

        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();

        // Update the scenario using partial update
        Scenario partialUpdatedScenario = new Scenario();
        partialUpdatedScenario.setId(scenario.getId());

        partialUpdatedScenario.language(UPDATED_LANGUAGE);

        restScenarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScenario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScenario))
            )
            .andExpect(status().isOk());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
        Scenario testScenario = scenarioList.get(scenarioList.size() - 1);
        assertThat(testScenario.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testScenario.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testScenario.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    void fullUpdateScenarioWithPatch() throws Exception {
        // Initialize the database
        scenarioRepository.save(scenario);

        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();

        // Update the scenario using partial update
        Scenario partialUpdatedScenario = new Scenario();
        partialUpdatedScenario.setId(scenario.getId());

        partialUpdatedScenario.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).language(UPDATED_LANGUAGE);

        restScenarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScenario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScenario))
            )
            .andExpect(status().isOk());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
        Scenario testScenario = scenarioList.get(scenarioList.size() - 1);
        assertThat(testScenario.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testScenario.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testScenario.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    void patchNonExistingScenario() throws Exception {
        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();
        scenario.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScenarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scenario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scenario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchScenario() throws Exception {
        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();
        scenario.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScenarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scenario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamScenario() throws Exception {
        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();
        scenario.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScenarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(scenario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteScenario() throws Exception {
        // Initialize the database
        scenarioRepository.save(scenario);

        int databaseSizeBeforeDelete = scenarioRepository.findAll().size();

        // Delete the scenario
        restScenarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, scenario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
