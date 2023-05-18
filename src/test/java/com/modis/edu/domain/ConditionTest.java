package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConditionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Condition.class);
        Condition condition1 = new Condition();
        condition1.setId("id1");
        Condition condition2 = new Condition();
        condition2.setId(condition1.getId());
        assertThat(condition1).isEqualTo(condition2);
        condition2.setId("id2");
        assertThat(condition1).isNotEqualTo(condition2);
        condition1.setId(null);
        assertThat(condition1).isNotEqualTo(condition2);
    }
}
