package io.github.vsinkievic.mockwallesterapi.domain;

import static io.github.vsinkievic.mockwallesterapi.domain.CardTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Card.class);
        Card card1 = getCardSample1();
        Card card2 = new Card();
        assertThat(card1).isNotEqualTo(card2);

        card2.setId(card1.getId());
        assertThat(card1).isEqualTo(card2);

        card2 = getCardSample2();
        assertThat(card1).isNotEqualTo(card2);
    }
}
