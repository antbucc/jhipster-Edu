package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Learner;
import com.modis.edu.repository.LearnerRepository;
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
 * Integration tests for the {@link LearnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LearnerResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/learners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private MockMvc restLearnerMockMvc;

    private Learner learner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Learner createEntity() {
        Learner learner = new Learner()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return learner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Learner createUpdatedEntity() {
        Learner learner = new Learner()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return learner;
    }

    @BeforeEach
    public void initTest() {
        learnerRepository.deleteAll();
        learner = createEntity();
    }

    @Test
    void createLearner() throws Exception {
        int databaseSizeBeforeCreate = learnerRepository.findAll().size();
        // Create the Learner
        restLearnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learner)))
            .andExpect(status().isCreated());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeCreate + 1);
        Learner testLearner = learnerList.get(learnerList.size() - 1);
        assertThat(testLearner.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testLearner.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testLearner.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLearner.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    void createLearnerWithExistingId() throws Exception {
        // Create the Learner with an existing ID
        learner.setId("existing_id");

        int databaseSizeBeforeCreate = learnerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLearnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learner)))
            .andExpect(status().isBadRequest());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLearners() throws Exception {
        // Initialize the database
        learnerRepository.save(learner);

        // Get all the learnerList
        restLearnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(learner.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    void getLearner() throws Exception {
        // Initialize the database
        learnerRepository.save(learner);

        // Get the learner
        restLearnerMockMvc
            .perform(get(ENTITY_API_URL_ID, learner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(learner.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    void getNonExistingLearner() throws Exception {
        // Get the learner
        restLearnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLearner() throws Exception {
        // Initialize the database
        learnerRepository.save(learner);

        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();

        // Update the learner
        Learner updatedLearner = learnerRepository.findById(learner.getId()).get();
        updatedLearner.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);

        restLearnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLearner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLearner))
            )
            .andExpect(status().isOk());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
        Learner testLearner = learnerList.get(learnerList.size() - 1);
        assertThat(testLearner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testLearner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testLearner.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLearner.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    void putNonExistingLearner() throws Exception {
        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();
        learner.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLearnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, learner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(learner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLearner() throws Exception {
        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();
        learner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(learner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLearner() throws Exception {
        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();
        learner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearnerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learner)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLearnerWithPatch() throws Exception {
        // Initialize the database
        learnerRepository.save(learner);

        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();

        // Update the learner using partial update
        Learner partialUpdatedLearner = new Learner();
        partialUpdatedLearner.setId(learner.getId());

        partialUpdatedLearner.phoneNumber(UPDATED_PHONE_NUMBER);

        restLearnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLearner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLearner))
            )
            .andExpect(status().isOk());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
        Learner testLearner = learnerList.get(learnerList.size() - 1);
        assertThat(testLearner.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testLearner.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testLearner.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLearner.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    void fullUpdateLearnerWithPatch() throws Exception {
        // Initialize the database
        learnerRepository.save(learner);

        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();

        // Update the learner using partial update
        Learner partialUpdatedLearner = new Learner();
        partialUpdatedLearner.setId(learner.getId());

        partialUpdatedLearner
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restLearnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLearner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLearner))
            )
            .andExpect(status().isOk());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
        Learner testLearner = learnerList.get(learnerList.size() - 1);
        assertThat(testLearner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testLearner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testLearner.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLearner.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    void patchNonExistingLearner() throws Exception {
        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();
        learner.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLearnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, learner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(learner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLearner() throws Exception {
        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();
        learner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(learner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLearner() throws Exception {
        int databaseSizeBeforeUpdate = learnerRepository.findAll().size();
        learner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearnerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(learner)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Learner in the database
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLearner() throws Exception {
        // Initialize the database
        learnerRepository.save(learner);

        int databaseSizeBeforeDelete = learnerRepository.findAll().size();

        // Delete the learner
        restLearnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, learner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Learner> learnerList = learnerRepository.findAll();
        assertThat(learnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
