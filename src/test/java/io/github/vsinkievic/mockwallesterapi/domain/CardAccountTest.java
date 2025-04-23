package io.github.vsinkievic.mockwallesterapi.domain;

import static io.github.vsinkievic.mockwallesterapi.domain.CardAccountTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CardAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardAccount.class);
        CardAccount cardAccount1 = getCardAccountSample1();
        CardAccount cardAccount2 = new CardAccount();
        assertThat(cardAccount1).isNotEqualTo(cardAccount2);

        cardAccount2.setId(cardAccount1.getId());
        assertThat(cardAccount1).isEqualTo(cardAccount2);

        cardAccount2 = getCardAccountSample2();
        assertThat(cardAccount1).isNotEqualTo(cardAccount2);
    }
}
