package io.github.vsinkievic.mockwallesterapi.service.mapper;

import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CardAccount} and its DTO {@link CardAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface CardAccountMapper extends EntityMapper<CardAccountDTO, CardAccount> {}
