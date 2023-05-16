package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LearnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Learner.class);
        Learner learner1 = new Learner();
        learner1.setId("id1");
        Learner learner2 = new Learner();
        learner2.setId(learner1.getId());
        assertThat(learner1).isEqualTo(learner2);
        learner2.setId("id2");
        assertThat(learner1).isNotEqualTo(learner2);
        learner1.setId(null);
        assertThat(learner1).isNotEqualTo(learner2);
    }
}
