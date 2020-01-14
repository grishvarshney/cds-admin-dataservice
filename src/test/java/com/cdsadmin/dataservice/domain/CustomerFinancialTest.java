package com.cdsadmin.dataservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cdsadmin.dataservice.web.rest.TestUtil;

public class CustomerFinancialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerFinancial.class);
        CustomerFinancial customerFinancial1 = new CustomerFinancial();
        customerFinancial1.setId(1L);
        CustomerFinancial customerFinancial2 = new CustomerFinancial();
        customerFinancial2.setId(customerFinancial1.getId());
        assertThat(customerFinancial1).isEqualTo(customerFinancial2);
        customerFinancial2.setId(2L);
        assertThat(customerFinancial1).isNotEqualTo(customerFinancial2);
        customerFinancial1.setId(null);
        assertThat(customerFinancial1).isNotEqualTo(customerFinancial2);
    }
}
