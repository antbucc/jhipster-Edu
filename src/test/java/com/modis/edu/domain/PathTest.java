package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PathTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Path.class);
        Path path1 = new Path();
        path1.setId("id1");
        Path path2 = new Path();
        path2.setId(path1.getId());
        assertThat(path1).isEqualTo(path2);
        path2.setId("id2");
        assertThat(path1).isNotEqualTo(path2);
        path1.setId(null);
        assertThat(path1).isNotEqualTo(path2);
    }
}
