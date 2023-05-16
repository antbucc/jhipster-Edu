package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Educator;
import com.modis.edu.repository.EducatorRepository;
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
 * Integration tests for the {@link EducatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducatorResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/educators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EducatorRepository educatorRepository;

    @Autowired
    private MockMvc restEducatorMockMvc;

    private Educator educator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Educator createEntity() {
        Educator educator = new Educator().firstName(DEFAULT_FIRST_NAME).lastName(DEFAULT_LAST_NAME).email(DEFAULT_EMAIL);
        return educator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Educator createUpdatedEntity() {
        Educator educator = new Educator().firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);
        return educator;
    }

    @BeforeEach
    public void initTest() {
        educatorRepository.deleteAll();
        educator = createEntity();
    }

    @Test
    void createEducator() throws Exception {
        int databaseSizeBeforeCreate = educatorRepository.findAll().size();
        // Create the Educator
        restEducatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educator)))
            .andExpect(status().isCreated());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeCreate + 1);
        Educator testEducator = educatorList.get(educatorList.size() - 1);
        assertThat(testEducator.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEducator.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEducator.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    void createEducatorWithExistingId() throws Exception {
        // Create the Educator with an existing ID
        educator.setId("existing_id");

        int databaseSizeBeforeCreate = educatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educator)))
            .andExpect(status().isBadRequest());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEducators() throws Exception {
        // Initialize the database
        educatorRepository.save(educator);

        // Get all the educatorList
        restEducatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educator.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    void getEducator() throws Exception {
        // Initialize the database
        educatorRepository.save(educator);

        // Get the educator
        restEducatorMockMvc
            .perform(get(ENTITY_API_URL_ID, educator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(educator.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    void getNonExistingEducator() throws Exception {
        // Get the educator
        restEducatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEducator() throws Exception {
        // Initialize the database
        educatorRepository.save(educator);

        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();

        // Update the educator
        Educator updatedEducator = educatorRepository.findById(educator.getId()).get();
        updatedEducator.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);

        restEducatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEducator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEducator))
            )
            .andExpect(status().isOk());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
        Educator testEducator = educatorList.get(educatorList.size() - 1);
        assertThat(testEducator.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEducator.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEducator.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    void putNonExistingEducator() throws Exception {
        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();
        educator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEducator() throws Exception {
        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();
        educator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEducator() throws Exception {
        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();
        educator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducatorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educator)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEducatorWithPatch() throws Exception {
        // Initialize the database
        educatorRepository.save(educator);

        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();

        // Update the educator using partial update
        Educator partialUpdatedEducator = new Educator();
        partialUpdatedEducator.setId(educator.getId());

        partialUpdatedEducator.lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);

        restEducatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducator))
            )
            .andExpect(status().isOk());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
        Educator testEducator = educatorList.get(educatorList.size() - 1);
        assertThat(testEducator.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEducator.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEducator.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    void fullUpdateEducatorWithPatch() throws Exception {
        // Initialize the database
        educatorRepository.save(educator);

        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();

        // Update the educator using partial update
        Educator partialUpdatedEducator = new Educator();
        partialUpdatedEducator.setId(educator.getId());

        partialUpdatedEducator.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);

        restEducatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducator))
            )
            .andExpect(status().isOk());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
        Educator testEducator = educatorList.get(educatorList.size() - 1);
        assertThat(testEducator.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEducator.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEducator.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    void patchNonExistingEducator() throws Exception {
        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();
        educator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEducator() throws Exception {
        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();
        educator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEducator() throws Exception {
        int databaseSizeBeforeUpdate = educatorRepository.findAll().size();
        educator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducatorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(educator)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Educator in the database
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEducator() throws Exception {
        // Initialize the database
        educatorRepository.save(educator);

        int databaseSizeBeforeDelete = educatorRepository.findAll().size();

        // Delete the educator
        restEducatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, educator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Educator> educatorList = educatorRepository.findAll();
        assertThat(educatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
