package io.github.vsinkievic.mockwallesterapi.service.mapper;

import static io.github.vsinkievic.mockwallesterapi.domain.CardAccountAsserts.*;
import static io.github.vsinkievic.mockwallesterapi.domain.CardAccountTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardAccountMapperTest {

    private CardAccountMapper cardAccountMapper;

    @BeforeEach
    void setUp() {
        cardAccountMapper = new CardAccountMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCardAccountSample1();
        var actual = cardAccountMapper.toEntity(cardAccountMapper.toDto(expected));
        assertCardAccountAllPropertiesEquals(expected, actual);
    }
}
