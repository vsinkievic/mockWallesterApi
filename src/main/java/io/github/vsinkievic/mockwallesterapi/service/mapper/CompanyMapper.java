package io.github.vsinkievic.mockwallesterapi.service.mapper;

import io.github.vsinkievic.mockwallesterapi.domain.Company;
import io.github.vsinkievic.mockwallesterapi.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {}
