package io.github.vsinkievic.mockwallesterapi.web.rest;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import io.github.vsinkievic.mockwallesterapi.service.CardAccountService;
import io.github.vsinkievic.mockwallesterapi.service.CompanyService;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import io.github.vsinkievic.mockwallesterapi.service.dto.CompanyDTO;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAccount;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAccountResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAccountSearchResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCompanyRequest;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCompanyResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCompanySearchResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterRestError;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import io.swagger.v3.oas.annotations.Operation;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Wallester API endpoints.
 */
@RestController
@RequestMapping("/wallester/api")
@Slf4j
public class WallesterRestApi {

    private final CompanyService companyService;
    private final CardAccountService accountService;

    public WallesterRestApi(CompanyService companyService, CardAccountService accountService) {
        this.companyService = companyService;
        this.accountService = accountService;
    }

    @ExceptionHandler(WallesterApiException.class)
    public ResponseEntity<WallesterRestError> handleWallesterRestError(WallesterApiException error) {
        log.error("Wallester API exception: {}", error.getMessage());
        return ResponseEntity.status(error.getHttpStatus()).body(new WallesterRestError(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WallesterRestError> handleException(Exception error) {
        log.error("Wallester API exception: {}", error.getMessage());
        return ResponseEntity.status(500).body(new WallesterRestError(error.getMessage()).errorText(error.getClass().getSimpleName()));
    }

    @Operation(tags = { "Testing" }, summary = "Ping endpoint", description = "Returns the input string with '-pong' suffix")
    @GetMapping("/ping")
    public ResponseEntity<String> ping(@RequestParam String request) {
        log.info("Ping request: {}", request);
        return ResponseEntity.ok(request + "-pong");
    }

    @Operation(tags = { "Company" }, summary = "Get company by ID", description = "Returns company information by its ID")
    @GetMapping("/v1/companies/{companyId}")
    public ResponseEntity<WallesterCompanyResponse> getCompanyById(@PathVariable("companyId") String companyId) {
        log.info("GET /v1/companies/{}", companyId);

        return companyService
            .findOne(UUID.fromString(companyId))
            .map(companyDTO -> ResponseEntity.ok(new WallesterCompanyResponse(companyDTO)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(tags = { "Account" }, summary = "Get Account by ID", description = "Returns Account information by its ID")
    @GetMapping("/v1/accounts/{account_id}")
    public ResponseEntity<WallesterAccountResponse> getAccountById(@PathVariable("account_id") String accountId) {
        log.info("GET /v1/accounts/{}", accountId);

        return accountService
            .findOne(UUID.fromString(accountId))
            .map(accountDTO -> ResponseEntity.ok(new WallesterAccountResponse(new WallesterAccount(accountDTO))))
            .orElseThrow(() -> new WallesterApiException(404, "Account not found"));
    }

    @Operation(tags = { "Account" }, summary = "Get Account by externalID", description = "Returns Account information by external ID")
    @GetMapping("/v1/accounts-by-external-id/{external_id}")
    public ResponseEntity<WallesterAccountResponse> getAccountByExternalId(@PathVariable("external_id") String externalId) {
        log.info("GET /v1/accounts-by-external-id/{}", externalId);

        return accountService
            .findOneByExternalId(externalId)
            .map(accountDTO -> ResponseEntity.ok(new WallesterAccountResponse(new WallesterAccount(accountDTO))))
            .orElseThrow(() -> new WallesterApiException(404, "Account not found"));
    }

    @Operation(tags = { "Company" }, summary = "Search companies", description = "Returns a list of companies matching the search criteria")
    @GetMapping("/v1/companies")
    public ResponseEntity<WallesterCompanySearchResponse> searchCompanies(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "registration_number", required = false) String registrationNumber,
        @RequestParam(value = "vat_number", required = false) String vatNumber,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = "kyb_status", required = false) String kybStatus,
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize
    ) {
        log.info(
            String.format(
                "GET /v1/companies with params: name=%s, registration_number=%s, vat_number=%s, status=%s, kyb_status=%s, page=%d, page_size=%d",
                name,
                registrationNumber,
                vatNumber,
                status,
                kybStatus,
                page,
                pageSize
            )
        );

        Page<CompanyDTO> companies = null;

        if (StringUtils.isNotBlank(registrationNumber)) {
            companies = companyService.findByRegistrationNumber(registrationNumber, PageRequest.of(page - 1, pageSize));
        } else {
            throw new WallesterApiException(422, "Not supported yet");
        }

        Page<WallesterCompanyResponse> companiesPage = companies.map(companyDTO -> new WallesterCompanyResponse(companyDTO));

        WallesterCompanySearchResponse response = new WallesterCompanySearchResponse();
        response.setCompanies(companiesPage.getContent());
        response.setTotal((int) companiesPage.getTotalElements());
        response.setPage(page);
        response.setPageSize(pageSize);

        return ResponseEntity.ok(response);
    }

    @Operation(tags = { "Company" }, summary = "Create company", description = "Creates a new company")
    @PostMapping("/v1/companies")
    public ResponseEntity<WallesterCompanyResponse> createCompany(@RequestBody WallesterCompanyRequest request) {
        log.info("POST /v1/companies with request: {}", request);

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName(request.getName());
        companyDTO.setRegistrationNumber(request.getRegistrationNumber());

        if (request.getRegistrationAddress() != null) {
            companyDTO.setRegAddressCountryCode(request.getRegistrationAddress().getCountryCode());
            companyDTO.setRegAddress1(request.getRegistrationAddress().getAddress1());
            companyDTO.setRegAddress2(request.getRegistrationAddress().getAddress2());
            companyDTO.setRegAddressCity(request.getRegistrationAddress().getCity());
            companyDTO.setRegAddressPostalCode(request.getRegistrationAddress().getPostalCode());
        }

        if (request.getHeadquarterAddress() != null) {
            companyDTO.setHqAddressCountryCode(request.getHeadquarterAddress().getCountryCode());
            companyDTO.setHqAddress1(request.getHeadquarterAddress().getAddress1());
            companyDTO.setHqAddress2(request.getHeadquarterAddress().getAddress2());
            companyDTO.setHqAddressCity(request.getHeadquarterAddress().getCity());
            companyDTO.setHqAddressPostalCode(request.getHeadquarterAddress().getPostalCode());
        }

        if (request.getRiskProfile() != null) {
            companyDTO.setRiskProfile(request.getRiskProfile());
        }

        companyDTO.setMobile(request.getMobile());
        companyDTO.setEmail(request.getEmail());
        companyDTO.setIndustryType(request.getIndustryType());

        if (request.getDateOfIncorporation() != null) {
            companyDTO.setDateOfIncorporation(request.getDateOfIncorporation().atStartOfDay(ZoneOffset.UTC).toInstant());
        }

        companyDTO.setBusinessRelationshipPurpose(request.getBusinessRelationshipPurpose());
        companyDTO.setIsSanctionsRelated(request.getIsSanctionsRelated());
        companyDTO.setIsAdverseMediaInvolved(request.getIsAdverseMediaInvolved());
        companyDTO.setEmployeesQuantity(request.getEmployeesQuantity());
        companyDTO.setCardSpendingAmount(request.getCardSpendingAmount());

        if (request.getLimits() != null) {
            companyDTO.setLimitDailyPurchase(request.getLimits().getDailyPurchase());
            companyDTO.setLimitDailyWithdrawal(request.getLimits().getDailyWithdrawal());
            companyDTO.setLimitMonthlyPurchase(request.getLimits().getMonthlyPurchase());
            companyDTO.setLimitMonthlyWithdrawal(request.getLimits().getMonthlyWithdrawal());
        }

        companyDTO.setKybStatus(request.getKybStatus());
        companyDTO.setStatus(request.getStatus());

        companyDTO.setPushNotificationsEnabled(request.getPushNotificationsEnabled());
        companyDTO.setPreferredLanguageCode(request.getPreferredLanguageCode());
        companyDTO.setVatNumber(request.getVatNumber());

        CompanyDTO savedCompany = companyService.save(companyDTO);
        return ResponseEntity.ok(new WallesterCompanyResponse(savedCompany));
    }

    @Operation(tags = { "Account" }, summary = "Search accounts", description = "Returns a list of accounts matching the search criteria")
    @GetMapping("/v1/accounts")
    public ResponseEntity<WallesterAccountSearchResponse> searchAccounts(
        @RequestParam(value = "from_record", required = true) int fromRecord,
        @RequestParam(value = "records_count", required = true) int recordsCount,
        @RequestParam(value = "order_field", required = false) String orderField,
        @RequestParam(value = "order_direction", required = false) String orderDirection
    ) {
        log.info(
            String.format(
                "GET /v1/accounts with params: from_record=%d, records_count=%d, order_field=%s, order_direction=%s",
                fromRecord,
                recordsCount,
                orderField,
                orderDirection
            )
        );

        // Validate and set default values
        if (recordsCount <= 0 || recordsCount > 500) {
            recordsCount = 500;
        }
        if (StringUtils.isBlank(orderDirection)) {
            orderDirection = "asc";
        }
        if (!orderDirection.equals("asc") && !orderDirection.equals("desc")) {
            throw new WallesterApiException(422, "Invalid order direction");
        }

        if (StringUtils.isBlank(orderField)) {
            orderField = "created_at";
        }

        // Validate order field
        if (!isValidAccountOrderField(orderField)) {
            throw new WallesterApiException(422, "Invalid order field");
        }

        // Create sort object
        Sort sort = Sort.by(orderDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, convertToCamelCase(orderField));

        // Create page request with sorting
        PageRequest pageRequest = PageRequest.of(fromRecord / recordsCount, recordsCount, sort);

        // Get accounts
        Page<CardAccountDTO> accounts = accountService.findAll(pageRequest);
        Page<WallesterAccount> accountsPage = accounts.map(accountDTO -> new WallesterAccount(accountDTO));

        // Create response
        WallesterAccountSearchResponse response = new WallesterAccountSearchResponse();
        response.setAccounts(accountsPage.getContent());
        response.setTotalRecordsNumber((int) accountsPage.getTotalElements());

        return ResponseEntity.ok(response);
    }

    private boolean isValidAccountOrderField(String orderField) {
        return switch (orderField) {
            case "created_at", "updated_at", "balance", "blocked_amount", "available_amount", "name", "status" -> true;
            default -> false;
        };
    }

    private String convertToCamelCase(String snakeCase) {
        if (snakeCase == null || snakeCase.isEmpty()) {
            return snakeCase;
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;

        for (char c : snakeCase.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(c);
                }
            }
        }

        return result.toString();
    }
}
