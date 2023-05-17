package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FragmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fragment.class);
        Fragment fragment1 = new Fragment();
        fragment1.setId("id1");
        Fragment fragment2 = new Fragment();
        fragment2.setId(fragment1.getId());
        assertThat(fragment1).isEqualTo(fragment2);
        fragment2.setId("id2");
        assertThat(fragment1).isNotEqualTo(fragment2);
        fragment1.setId(null);
        assertThat(fragment1).isNotEqualTo(fragment2);
    }
}
