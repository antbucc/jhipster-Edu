package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Condition;
import com.modis.edu.repository.ConditionRepository;
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
 * Integration tests for the {@link ConditionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConditionResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/conditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private MockMvc restConditionMockMvc;

    private Condition condition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createEntity() {
        Condition condition = new Condition().title(DEFAULT_TITLE);
        return condition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createUpdatedEntity() {
        Condition condition = new Condition().title(UPDATED_TITLE);
        return condition;
    }

    @BeforeEach
    public void initTest() {
        conditionRepository.deleteAll();
        condition = createEntity();
    }

    @Test
    void createCondition() throws Exception {
        int databaseSizeBeforeCreate = conditionRepository.findAll().size();
        // Create the Condition
        restConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(condition)))
            .andExpect(status().isCreated());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate + 1);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void createConditionWithExistingId() throws Exception {
        // Create the Condition with an existing ID
        condition.setId("existing_id");

        int databaseSizeBeforeCreate = conditionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(condition)))
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllConditions() throws Exception {
        // Initialize the database
        conditionRepository.save(condition);

        // Get all the conditionList
        restConditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    void getCondition() throws Exception {
        // Initialize the database
        conditionRepository.save(condition);

        // Get the condition
        restConditionMockMvc
            .perform(get(ENTITY_API_URL_ID, condition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(condition.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    void getNonExistingCondition() throws Exception {
        // Get the condition
        restConditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCondition() throws Exception {
        // Initialize the database
        conditionRepository.save(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition
        Condition updatedCondition = conditionRepository.findById(condition.getId()).get();
        updatedCondition.title(UPDATED_TITLE);

        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCondition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void putNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, condition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(condition)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConditionWithPatch() throws Exception {
        // Initialize the database
        conditionRepository.save(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition using partial update
        Condition partialUpdatedCondition = new Condition();
        partialUpdatedCondition.setId(condition.getId());

        partialUpdatedCondition.title(UPDATED_TITLE);

        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void fullUpdateConditionWithPatch() throws Exception {
        // Initialize the database
        conditionRepository.save(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition using partial update
        Condition partialUpdatedCondition = new Condition();
        partialUpdatedCondition.setId(condition.getId());

        partialUpdatedCondition.title(UPDATED_TITLE);

        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void patchNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, condition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCondition() throws Exception {
        // Initialize the database
        conditionRepository.save(condition);

        int databaseSizeBeforeDelete = conditionRepository.findAll().size();

        // Delete the condition
        restConditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, condition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
