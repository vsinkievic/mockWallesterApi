package io.github.vsinkievic.mockwallesterapi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardDTO.class);
        CardDTO cardDTO1 = new CardDTO();
        cardDTO1.setId(UUID.randomUUID());
        CardDTO cardDTO2 = new CardDTO();
        assertThat(cardDTO1).isNotEqualTo(cardDTO2);
        cardDTO2.setId(cardDTO1.getId());
        assertThat(cardDTO1).isEqualTo(cardDTO2);
        cardDTO2.setId(UUID.randomUUID());
        assertThat(cardDTO1).isNotEqualTo(cardDTO2);
        cardDTO1.setId(null);
        assertThat(cardDTO1).isNotEqualTo(cardDTO2);
    }
}
