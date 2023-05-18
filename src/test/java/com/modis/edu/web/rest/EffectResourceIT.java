package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Effect;
import com.modis.edu.repository.EffectRepository;
import com.modis.edu.service.EffectService;
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
 * Integration tests for the {@link EffectResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EffectResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/effects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EffectRepository effectRepository;

    @Mock
    private EffectRepository effectRepositoryMock;

    @Mock
    private EffectService effectServiceMock;

    @Autowired
    private MockMvc restEffectMockMvc;

    private Effect effect;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Effect createEntity() {
        Effect effect = new Effect().title(DEFAULT_TITLE);
        return effect;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Effect createUpdatedEntity() {
        Effect effect = new Effect().title(UPDATED_TITLE);
        return effect;
    }

    @BeforeEach
    public void initTest() {
        effectRepository.deleteAll();
        effect = createEntity();
    }

    @Test
    void createEffect() throws Exception {
        int databaseSizeBeforeCreate = effectRepository.findAll().size();
        // Create the Effect
        restEffectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effect)))
            .andExpect(status().isCreated());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeCreate + 1);
        Effect testEffect = effectList.get(effectList.size() - 1);
        assertThat(testEffect.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void createEffectWithExistingId() throws Exception {
        // Create the Effect with an existing ID
        effect.setId("existing_id");

        int databaseSizeBeforeCreate = effectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEffectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effect)))
            .andExpect(status().isBadRequest());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEffects() throws Exception {
        // Initialize the database
        effectRepository.save(effect);

        // Get all the effectList
        restEffectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(effect.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEffectsWithEagerRelationshipsIsEnabled() throws Exception {
        when(effectServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEffectMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(effectServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEffectsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(effectServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEffectMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(effectRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getEffect() throws Exception {
        // Initialize the database
        effectRepository.save(effect);

        // Get the effect
        restEffectMockMvc
            .perform(get(ENTITY_API_URL_ID, effect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(effect.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    void getNonExistingEffect() throws Exception {
        // Get the effect
        restEffectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEffect() throws Exception {
        // Initialize the database
        effectRepository.save(effect);

        int databaseSizeBeforeUpdate = effectRepository.findAll().size();

        // Update the effect
        Effect updatedEffect = effectRepository.findById(effect.getId()).get();
        updatedEffect.title(UPDATED_TITLE);

        restEffectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEffect.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEffect))
            )
            .andExpect(status().isOk());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
        Effect testEffect = effectList.get(effectList.size() - 1);
        assertThat(testEffect.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void putNonExistingEffect() throws Exception {
        int databaseSizeBeforeUpdate = effectRepository.findAll().size();
        effect.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEffectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, effect.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(effect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEffect() throws Exception {
        int databaseSizeBeforeUpdate = effectRepository.findAll().size();
        effect.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(effect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEffect() throws Exception {
        int databaseSizeBeforeUpdate = effectRepository.findAll().size();
        effect.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effect)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEffectWithPatch() throws Exception {
        // Initialize the database
        effectRepository.save(effect);

        int databaseSizeBeforeUpdate = effectRepository.findAll().size();

        // Update the effect using partial update
        Effect partialUpdatedEffect = new Effect();
        partialUpdatedEffect.setId(effect.getId());

        partialUpdatedEffect.title(UPDATED_TITLE);

        restEffectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEffect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEffect))
            )
            .andExpect(status().isOk());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
        Effect testEffect = effectList.get(effectList.size() - 1);
        assertThat(testEffect.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void fullUpdateEffectWithPatch() throws Exception {
        // Initialize the database
        effectRepository.save(effect);

        int databaseSizeBeforeUpdate = effectRepository.findAll().size();

        // Update the effect using partial update
        Effect partialUpdatedEffect = new Effect();
        partialUpdatedEffect.setId(effect.getId());

        partialUpdatedEffect.title(UPDATED_TITLE);

        restEffectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEffect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEffect))
            )
            .andExpect(status().isOk());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
        Effect testEffect = effectList.get(effectList.size() - 1);
        assertThat(testEffect.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void patchNonExistingEffect() throws Exception {
        int databaseSizeBeforeUpdate = effectRepository.findAll().size();
        effect.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEffectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, effect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(effect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEffect() throws Exception {
        int databaseSizeBeforeUpdate = effectRepository.findAll().size();
        effect.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(effect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEffect() throws Exception {
        int databaseSizeBeforeUpdate = effectRepository.findAll().size();
        effect.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(effect)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Effect in the database
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEffect() throws Exception {
        // Initialize the database
        effectRepository.save(effect);

        int databaseSizeBeforeDelete = effectRepository.findAll().size();

        // Delete the effect
        restEffectMockMvc
            .perform(delete(ENTITY_API_URL_ID, effect.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Effect> effectList = effectRepository.findAll();
        assertThat(effectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
