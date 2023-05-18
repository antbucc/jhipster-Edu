package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Path;
import com.modis.edu.domain.enumeration.PathType;
import com.modis.edu.repository.PathRepository;
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
 * Integration tests for the {@link PathResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PathResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final PathType DEFAULT_TYPE = PathType.PASS;
    private static final PathType UPDATED_TYPE = PathType.FAIL;

    private static final String ENTITY_API_URL = "/api/paths";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private MockMvc restPathMockMvc;

    private Path path;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Path createEntity() {
        Path path = new Path().title(DEFAULT_TITLE).type(DEFAULT_TYPE);
        return path;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Path createUpdatedEntity() {
        Path path = new Path().title(UPDATED_TITLE).type(UPDATED_TYPE);
        return path;
    }

    @BeforeEach
    public void initTest() {
        pathRepository.deleteAll();
        path = createEntity();
    }

    @Test
    void createPath() throws Exception {
        int databaseSizeBeforeCreate = pathRepository.findAll().size();
        // Create the Path
        restPathMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path)))
            .andExpect(status().isCreated());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeCreate + 1);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPath.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createPathWithExistingId() throws Exception {
        // Create the Path with an existing ID
        path.setId("existing_id");

        int databaseSizeBeforeCreate = pathRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPathMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path)))
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPaths() throws Exception {
        // Initialize the database
        pathRepository.save(path);

        // Get all the pathList
        restPathMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(path.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    void getPath() throws Exception {
        // Initialize the database
        pathRepository.save(path);

        // Get the path
        restPathMockMvc
            .perform(get(ENTITY_API_URL_ID, path.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(path.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    void getNonExistingPath() throws Exception {
        // Get the path
        restPathMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPath() throws Exception {
        // Initialize the database
        pathRepository.save(path);

        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path
        Path updatedPath = pathRepository.findById(path.getId()).get();
        updatedPath.title(UPDATED_TITLE).type(UPDATED_TYPE);

        restPathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPath.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPath))
            )
            .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPath.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, path.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(path)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePathWithPatch() throws Exception {
        // Initialize the database
        pathRepository.save(path);

        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path using partial update
        Path partialUpdatedPath = new Path();
        partialUpdatedPath.setId(path.getId());

        partialUpdatedPath.title(UPDATED_TITLE);

        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPath.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPath))
            )
            .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPath.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdatePathWithPatch() throws Exception {
        // Initialize the database
        pathRepository.save(path);

        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path using partial update
        Path partialUpdatedPath = new Path();
        partialUpdatedPath.setId(path.getId());

        partialUpdatedPath.title(UPDATED_TITLE).type(UPDATED_TYPE);

        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPath.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPath))
            )
            .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPath.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, path.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(path))
            )
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();
        path.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPathMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(path)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePath() throws Exception {
        // Initialize the database
        pathRepository.save(path);

        int databaseSizeBeforeDelete = pathRepository.findAll().size();

        // Delete the path
        restPathMockMvc
            .perform(delete(ENTITY_API_URL_ID, path.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
