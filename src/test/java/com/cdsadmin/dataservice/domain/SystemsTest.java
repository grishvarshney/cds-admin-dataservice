package com.cdsadmin.dataservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cdsadmin.dataservice.web.rest.TestUtil;

public class SystemsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Systems.class);
        Systems systems1 = new Systems();
        systems1.setId(1L);
        Systems systems2 = new Systems();
        systems2.setId(systems1.getId());
        assertThat(systems1).isEqualTo(systems2);
        systems2.setId(2L);
        assertThat(systems1).isNotEqualTo(systems2);
        systems1.setId(null);
        assertThat(systems1).isNotEqualTo(systems2);
    }
}
