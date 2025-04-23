package io.github.vsinkievic.mockwallesterapi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AccountStatementRecordDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountStatementRecordDTO.class);
        AccountStatementRecordDTO accountStatementRecordDTO1 = new AccountStatementRecordDTO();
        accountStatementRecordDTO1.setId(UUID.randomUUID());
        AccountStatementRecordDTO accountStatementRecordDTO2 = new AccountStatementRecordDTO();
        assertThat(accountStatementRecordDTO1).isNotEqualTo(accountStatementRecordDTO2);
        accountStatementRecordDTO2.setId(accountStatementRecordDTO1.getId());
        assertThat(accountStatementRecordDTO1).isEqualTo(accountStatementRecordDTO2);
        accountStatementRecordDTO2.setId(UUID.randomUUID());
        assertThat(accountStatementRecordDTO1).isNotEqualTo(accountStatementRecordDTO2);
        accountStatementRecordDTO1.setId(null);
        assertThat(accountStatementRecordDTO1).isNotEqualTo(accountStatementRecordDTO2);
    }
}
