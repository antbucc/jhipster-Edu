package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Fragment;
import com.modis.edu.repository.FragmentRepository;
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
 * Integration tests for the {@link FragmentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FragmentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fragments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FragmentRepository fragmentRepository;

    @Mock
    private FragmentRepository fragmentRepositoryMock;

    @Autowired
    private MockMvc restFragmentMockMvc;

    private Fragment fragment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fragment createEntity() {
        Fragment fragment = new Fragment().title(DEFAULT_TITLE);
        return fragment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fragment createUpdatedEntity() {
        Fragment fragment = new Fragment().title(UPDATED_TITLE);
        return fragment;
    }

    @BeforeEach
    public void initTest() {
        fragmentRepository.deleteAll();
        fragment = createEntity();
    }

    @Test
    void createFragment() throws Exception {
        int databaseSizeBeforeCreate = fragmentRepository.findAll().size();
        // Create the Fragment
        restFragmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragment)))
            .andExpect(status().isCreated());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeCreate + 1);
        Fragment testFragment = fragmentList.get(fragmentList.size() - 1);
        assertThat(testFragment.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void createFragmentWithExistingId() throws Exception {
        // Create the Fragment with an existing ID
        fragment.setId("existing_id");

        int databaseSizeBeforeCreate = fragmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFragmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragment)))
            .andExpect(status().isBadRequest());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFragments() throws Exception {
        // Initialize the database
        fragmentRepository.save(fragment);

        // Get all the fragmentList
        restFragmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fragment.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFragmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(fragmentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFragmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fragmentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFragmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fragmentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFragmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fragmentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getFragment() throws Exception {
        // Initialize the database
        fragmentRepository.save(fragment);

        // Get the fragment
        restFragmentMockMvc
            .perform(get(ENTITY_API_URL_ID, fragment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fragment.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    void getNonExistingFragment() throws Exception {
        // Get the fragment
        restFragmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFragment() throws Exception {
        // Initialize the database
        fragmentRepository.save(fragment);

        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();

        // Update the fragment
        Fragment updatedFragment = fragmentRepository.findById(fragment.getId()).get();
        updatedFragment.title(UPDATED_TITLE);

        restFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFragment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFragment))
            )
            .andExpect(status().isOk());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
        Fragment testFragment = fragmentList.get(fragmentList.size() - 1);
        assertThat(testFragment.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void putNonExistingFragment() throws Exception {
        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();
        fragment.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fragment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFragment() throws Exception {
        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();
        fragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFragment() throws Exception {
        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();
        fragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFragmentWithPatch() throws Exception {
        // Initialize the database
        fragmentRepository.save(fragment);

        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();

        // Update the fragment using partial update
        Fragment partialUpdatedFragment = new Fragment();
        partialUpdatedFragment.setId(fragment.getId());

        restFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFragment))
            )
            .andExpect(status().isOk());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
        Fragment testFragment = fragmentList.get(fragmentList.size() - 1);
        assertThat(testFragment.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void fullUpdateFragmentWithPatch() throws Exception {
        // Initialize the database
        fragmentRepository.save(fragment);

        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();

        // Update the fragment using partial update
        Fragment partialUpdatedFragment = new Fragment();
        partialUpdatedFragment.setId(fragment.getId());

        partialUpdatedFragment.title(UPDATED_TITLE);

        restFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFragment))
            )
            .andExpect(status().isOk());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
        Fragment testFragment = fragmentList.get(fragmentList.size() - 1);
        assertThat(testFragment.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void patchNonExistingFragment() throws Exception {
        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();
        fragment.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFragment() throws Exception {
        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();
        fragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFragment() throws Exception {
        int databaseSizeBeforeUpdate = fragmentRepository.findAll().size();
        fragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fragment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fragment in the database
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFragment() throws Exception {
        // Initialize the database
        fragmentRepository.save(fragment);

        int databaseSizeBeforeDelete = fragmentRepository.findAll().size();

        // Delete the fragment
        restFragmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, fragment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fragment> fragmentList = fragmentRepository.findAll();
        assertThat(fragmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
