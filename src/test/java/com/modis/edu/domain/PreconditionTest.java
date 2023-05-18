package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreconditionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Precondition.class);
        Precondition precondition1 = new Precondition();
        precondition1.setId("id1");
        Precondition precondition2 = new Precondition();
        precondition2.setId(precondition1.getId());
        assertThat(precondition1).isEqualTo(precondition2);
        precondition2.setId("id2");
        assertThat(precondition1).isNotEqualTo(precondition2);
        precondition1.setId(null);
        assertThat(precondition1).isNotEqualTo(precondition2);
    }
}
