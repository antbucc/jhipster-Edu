package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConceptTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concept.class);
        Concept concept1 = new Concept();
        concept1.setId("id1");
        Concept concept2 = new Concept();
        concept2.setId(concept1.getId());
        assertThat(concept1).isEqualTo(concept2);
        concept2.setId("id2");
        assertThat(concept1).isNotEqualTo(concept2);
        concept1.setId(null);
        assertThat(concept1).isNotEqualTo(concept2);
    }
}
