package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Domain;
import com.modis.edu.repository.DomainRepository;
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
 * Integration tests for the {@link DomainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DomainResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/domains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private MockMvc restDomainMockMvc;

    private Domain domain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domain createEntity() {
        Domain domain = new Domain().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION).city(DEFAULT_CITY);
        return domain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domain createUpdatedEntity() {
        Domain domain = new Domain().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).city(UPDATED_CITY);
        return domain;
    }

    @BeforeEach
    public void initTest() {
        domainRepository.deleteAll();
        domain = createEntity();
    }

    @Test
    void createDomain() throws Exception {
        int databaseSizeBeforeCreate = domainRepository.findAll().size();
        // Create the Domain
        restDomainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(domain)))
            .andExpect(status().isCreated());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeCreate + 1);
        Domain testDomain = domainList.get(domainList.size() - 1);
        assertThat(testDomain.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDomain.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDomain.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    void createDomainWithExistingId() throws Exception {
        // Create the Domain with an existing ID
        domain.setId("existing_id");

        int databaseSizeBeforeCreate = domainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(domain)))
            .andExpect(status().isBadRequest());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDomains() throws Exception {
        // Initialize the database
        domainRepository.save(domain);

        // Get all the domainList
        restDomainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domain.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)));
    }

    @Test
    void getDomain() throws Exception {
        // Initialize the database
        domainRepository.save(domain);

        // Get the domain
        restDomainMockMvc
            .perform(get(ENTITY_API_URL_ID, domain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(domain.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY));
    }

    @Test
    void getNonExistingDomain() throws Exception {
        // Get the domain
        restDomainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDomain() throws Exception {
        // Initialize the database
        domainRepository.save(domain);

        int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Update the domain
        Domain updatedDomain = domainRepository.findById(domain.getId()).get();
        updatedDomain.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).city(UPDATED_CITY);

        restDomainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDomain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDomain))
            )
            .andExpect(status().isOk());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
        Domain testDomain = domainList.get(domainList.size() - 1);
        assertThat(testDomain.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDomain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDomain.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    void putNonExistingDomain() throws Exception {
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();
        domain.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, domain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(domain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDomain() throws Exception {
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();
        domain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDomainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(domain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDomain() throws Exception {
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();
        domain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDomainMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(domain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDomainWithPatch() throws Exception {
        // Initialize the database
        domainRepository.save(domain);

        int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Update the domain using partial update
        Domain partialUpdatedDomain = new Domain();
        partialUpdatedDomain.setId(domain.getId());

        restDomainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDomain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDomain))
            )
            .andExpect(status().isOk());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
        Domain testDomain = domainList.get(domainList.size() - 1);
        assertThat(testDomain.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDomain.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDomain.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    void fullUpdateDomainWithPatch() throws Exception {
        // Initialize the database
        domainRepository.save(domain);

        int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Update the domain using partial update
        Domain partialUpdatedDomain = new Domain();
        partialUpdatedDomain.setId(domain.getId());

        partialUpdatedDomain.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).city(UPDATED_CITY);

        restDomainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDomain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDomain))
            )
            .andExpect(status().isOk());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
        Domain testDomain = domainList.get(domainList.size() - 1);
        assertThat(testDomain.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDomain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDomain.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    void patchNonExistingDomain() throws Exception {
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();
        domain.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, domain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(domain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDomain() throws Exception {
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();
        domain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDomainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(domain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDomain() throws Exception {
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();
        domain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDomainMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(domain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDomain() throws Exception {
        // Initialize the database
        domainRepository.save(domain);

        int databaseSizeBeforeDelete = domainRepository.findAll().size();

        // Delete the domain
        restDomainMockMvc
            .perform(delete(ENTITY_API_URL_ID, domain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
