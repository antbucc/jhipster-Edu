package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EffectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Effect.class);
        Effect effect1 = new Effect();
        effect1.setId("id1");
        Effect effect2 = new Effect();
        effect2.setId(effect1.getId());
        assertThat(effect1).isEqualTo(effect2);
        effect2.setId("id2");
        assertThat(effect1).isNotEqualTo(effect2);
        effect1.setId(null);
        assertThat(effect1).isNotEqualTo(effect2);
    }
}
