package io.github.vsinkievic.mockwallesterapi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CardAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardAccountDTO.class);
        CardAccountDTO cardAccountDTO1 = new CardAccountDTO();
        cardAccountDTO1.setId(UUID.randomUUID());
        CardAccountDTO cardAccountDTO2 = new CardAccountDTO();
        assertThat(cardAccountDTO1).isNotEqualTo(cardAccountDTO2);
        cardAccountDTO2.setId(cardAccountDTO1.getId());
        assertThat(cardAccountDTO1).isEqualTo(cardAccountDTO2);
        cardAccountDTO2.setId(UUID.randomUUID());
        assertThat(cardAccountDTO1).isNotEqualTo(cardAccountDTO2);
        cardAccountDTO1.setId(null);
        assertThat(cardAccountDTO1).isNotEqualTo(cardAccountDTO2);
    }
}
