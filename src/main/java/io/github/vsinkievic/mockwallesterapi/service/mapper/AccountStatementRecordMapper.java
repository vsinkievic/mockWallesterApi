package io.github.vsinkievic.mockwallesterapi.service.mapper;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.service.dto.AccountStatementRecordDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountStatementRecord} and its DTO {@link AccountStatementRecordDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccountStatementRecordMapper extends EntityMapper<AccountStatementRecordDTO, AccountStatementRecord> {}
