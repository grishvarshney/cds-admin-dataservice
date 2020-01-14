package com.cdsadmin.dataservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cdsadmin.dataservice.web.rest.TestUtil;

public class PurposeTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurposeType.class);
        PurposeType purposeType1 = new PurposeType();
        purposeType1.setId(1L);
        PurposeType purposeType2 = new PurposeType();
        purposeType2.setId(purposeType1.getId());
        assertThat(purposeType1).isEqualTo(purposeType2);
        purposeType2.setId(2L);
        assertThat(purposeType1).isNotEqualTo(purposeType2);
        purposeType1.setId(null);
        assertThat(purposeType1).isNotEqualTo(purposeType2);
    }
}
