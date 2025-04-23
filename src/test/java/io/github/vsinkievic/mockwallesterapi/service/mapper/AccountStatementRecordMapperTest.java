package io.github.vsinkievic.mockwallesterapi.service.mapper;

import static io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecordAsserts.*;
import static io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecordTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountStatementRecordMapperTest {

    private AccountStatementRecordMapper accountStatementRecordMapper;

    @BeforeEach
    void setUp() {
        accountStatementRecordMapper = new AccountStatementRecordMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAccountStatementRecordSample1();
        var actual = accountStatementRecordMapper.toEntity(accountStatementRecordMapper.toDto(expected));
        assertAccountStatementRecordAllPropertiesEquals(expected, actual);
    }
}
