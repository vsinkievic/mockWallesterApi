package io.github.vsinkievic.mockwallesterapi.domain;

import static io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecordTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountStatementRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountStatementRecord.class);
        AccountStatementRecord accountStatementRecord1 = getAccountStatementRecordSample1();
        AccountStatementRecord accountStatementRecord2 = new AccountStatementRecord();
        assertThat(accountStatementRecord1).isNotEqualTo(accountStatementRecord2);

        accountStatementRecord2.setId(accountStatementRecord1.getId());
        assertThat(accountStatementRecord1).isEqualTo(accountStatementRecord2);

        accountStatementRecord2 = getAccountStatementRecordSample2();
        assertThat(accountStatementRecord1).isNotEqualTo(accountStatementRecord2);
    }
}
