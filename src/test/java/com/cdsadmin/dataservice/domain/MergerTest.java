package com.cdsadmin.dataservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cdsadmin.dataservice.web.rest.TestUtil;

public class MergerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Merger.class);
        Merger merger1 = new Merger();
        merger1.setId(1L);
        Merger merger2 = new Merger();
        merger2.setId(merger1.getId());
        assertThat(merger1).isEqualTo(merger2);
        merger2.setId(2L);
        assertThat(merger1).isNotEqualTo(merger2);
        merger1.setId(null);
        assertThat(merger1).isNotEqualTo(merger2);
    }
}
