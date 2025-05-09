package io.github.vsinkievic.mockwallesterapi.web.rest;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.service.AccountStatementService;
import io.github.vsinkievic.mockwallesterapi.service.CardAccountService;
import io.github.vsinkievic.mockwallesterapi.service.CardService;
import io.github.vsinkievic.mockwallesterapi.service.CompanyService;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardDTO;
import io.github.vsinkievic.mockwallesterapi.service.dto.CompanyDTO;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAccount;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAccountRequest;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAccountResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAccountSearchResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAdjustmentRequest;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCard;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCardRequest;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCardResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCompany;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCompanyRequest;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCompanyResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCompanySearchResponse;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterRestError;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterStatementRecord;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterStatementResponse;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import io.github.vsinkievic.mockwallesterapi.web.rest.model.WallesterCardSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
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
    private final CardService cardService;
    private final AccountStatementService accountStatementService;

    public WallesterRestApi(
        CompanyService companyService,
        CardAccountService accountService,
        CardService cardService,
        AccountStatementService accountStatementService
    ) {
        this.companyService = companyService;
        this.accountService = accountService;
        this.cardService = cardService;
        this.accountStatementService = accountStatementService;
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
    public ResponseEntity<String> ping(@RequestParam(required = false, defaultValue = "ping") String request) {
        log.info("Ping request: {}", request);
        return ResponseEntity.ok(request + "-pong");
    }

    @Operation(tags = { "Company" }, summary = "Get company by ID", description = "Returns company information by its ID")
    @GetMapping("/v1/companies/{companyId}")
    public ResponseEntity<WallesterCompany> getCompanyById(@PathVariable("companyId") String companyId) {
        log.info("GET /v1/companies/{}", companyId);

        return companyService
            .findOne(UUID.fromString(companyId))
            .map(companyDTO -> ResponseEntity.ok(new WallesterCompany(companyDTO)))
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
            throw new WallesterApiException(422, "Only search by registration_number is supported");
        }

        Page<WallesterCompany> companiesPage = companies.map(companyDTO -> new WallesterCompany(companyDTO));

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
        return ResponseEntity.ok(new WallesterCompanyResponse(new WallesterCompany(savedCompany)));
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

    @Operation(tags = { "Account" }, summary = "Create account", description = "Creates a new account")
    @PostMapping("/v1/accounts")
    public ResponseEntity<WallesterAccountResponse> createAccount(@RequestBody WallesterAccountRequest request) {
        log.info("POST /v1/accounts with request: {}", request);

        CardAccountDTO accountDTO = new CardAccountDTO();
        accountDTO.setCompanyId(request.getCompanyId());
        accountDTO.setPersonId(request.getPersonId());
        accountDTO.setName(request.getName());
        accountDTO.setCurrencyCode(request.getCurrencyCode());
        accountDTO.setExternalId(request.getExternalId());
        accountDTO.setCreditLimit(request.getCreditLimit());
        accountDTO.setUsedCredit(request.getUsedCredit());

        if (request.getLimits() != null) {
            accountDTO.setDailyContactlessPurchase(request.getLimits().getDailyContactlessPurchase());
            accountDTO.setDailyInternetPurchase(request.getLimits().getDailyInternetPurchase());
            accountDTO.setDailyPurchase(request.getLimits().getDailyPurchase());
            accountDTO.setDailyWithdrawal(request.getLimits().getDailyWithdrawal());
            accountDTO.setMonthlyContactlessPurchase(request.getLimits().getMonthlyContactlessPurchase());
            accountDTO.setMonthlyInternetPurchase(request.getLimits().getMonthlyInternetPurchase());
            accountDTO.setMonthlyPurchase(request.getLimits().getMonthlyPurchase());
            accountDTO.setMonthlyWithdrawal(request.getLimits().getMonthlyWithdrawal());
            accountDTO.setWeeklyContactlessPurchase(request.getLimits().getWeeklyContactlessPurchase());
            accountDTO.setWeeklyInternetPurchase(request.getLimits().getWeeklyInternetPurchase());
            accountDTO.setWeeklyPurchase(request.getLimits().getWeeklyPurchase());
            accountDTO.setWeeklyWithdrawal(request.getLimits().getWeeklyWithdrawal());
        }

        if (request.getTopUpDetails() != null) {
            accountDTO.setBankAddress(request.getTopUpDetails().getBankAddress());
            accountDTO.setBankName(request.getTopUpDetails().getBankName());
            accountDTO.setIban(request.getTopUpDetails().getIban());
            accountDTO.setPaymentDetails(request.getTopUpDetails().getPaymentDetails());
            accountDTO.setReceiverName(request.getTopUpDetails().getReceiverName());
            accountDTO.setRegistrationNumber(request.getTopUpDetails().getRegistrationNumber());
            accountDTO.setSwiftCode(request.getTopUpDetails().getSwiftCode());
        }

        CardAccountDTO savedAccount = accountService.save(accountDTO);
        return ResponseEntity.ok(new WallesterAccountResponse(new WallesterAccount(savedAccount)));
    }

    @Operation(tags = { "Card" }, summary = "Get card by ID", description = "Returns card information by its ID")
    @GetMapping("/v1/cards/{card_id}")
    public ResponseEntity<WallesterCardResponse> getCardById(@PathVariable("card_id") String cardId) {
        log.info("GET /v1/cards/{}", cardId);

        return cardService
            .findOne(UUID.fromString(cardId))
            .map(cardDTO -> ResponseEntity.ok(new WallesterCardResponse(new WallesterCard(cardDTO))))
            .orElseThrow(() -> new WallesterApiException(404, "Card not found"));
    }

    @Operation(tags = { "Card" }, summary = "Get card by external ID", description = "Returns card information by its external ID")
    @GetMapping("/v1/cards-by-external-id/{external_id}")
    public ResponseEntity<WallesterCardResponse> getCardByExternalId(@PathVariable("external_id") String externalId) {
        log.info("GET /v1/cards-by-external-id/{}", externalId);

        return cardService
            .findOneByExternalId(externalId)
            .map(cardDTO -> ResponseEntity.ok(new WallesterCardResponse(new WallesterCard(cardDTO))))
            .orElseThrow(() -> new WallesterApiException(404, "Card not found"));
    }

    @Operation(tags = { "Card" }, summary = "Search cards", description = "Returns a list of cards matching the search criteria")
    @GetMapping("/v1/cards")
    public ResponseEntity<WallesterCardSearchResponse> searchCards(
        @RequestParam(value = "from_record", required = true) int fromRecord,
        @RequestParam(value = "records_count", required = true) int recordsCount,
        @RequestParam(value = "order_field", required = false) String orderField,
        @RequestParam(value = "order_direction", required = false) String orderDirection
    ) {
        log.info(
            String.format(
                "GET /v1/cards with params: from_record=%d, records_count=%d, order_field=%s, order_direction=%s",
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
            orderField = "embossing_name";
        }

        // Validate order field
        if (!isValidCardOrderField(orderField)) {
            throw new WallesterApiException(422, "Invalid order field");
        }

        // Create sort object
        Sort sort = Sort.by(orderDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, convertToCamelCase(orderField));

        // Create page request with sorting
        PageRequest pageRequest = PageRequest.of(fromRecord / recordsCount, recordsCount, sort);

        // Get cards
        Page<CardDTO> cards = cardService.findAll(pageRequest);
        Page<WallesterCard> cardsPage = cards.map(cardDTO -> new WallesterCard(cardDTO));

        // Create response
        WallesterCardSearchResponse response = new WallesterCardSearchResponse();
        response.setCards(cardsPage.getContent());
        response.setTotalRecordsNumber((int) cardsPage.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Operation(tags = { "Card" }, summary = "Create card", description = "Creates a new card")
    @PostMapping("/v1/cards")
    public ResponseEntity<WallesterCardResponse> createCard(@RequestBody WallesterCardRequest request) {
        log.info("POST /v1/cards with request: {}", request);

        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(request.getAccountId());
        cardDTO.setName(request.getName());
        cardDTO.setExternalId(request.getExternalId());
        cardDTO.setType(request.getType());
        cardDTO.setCarrierType(request.getCarrierType());
        cardDTO.setEmbossingName(request.getEmbossingName());
        cardDTO.setEmbossingCompanyName(request.getEmbossingCompanyName());
        cardDTO.setIsDisposable(request.getIsDisposable());
        cardDTO.setEncryptedPin(request.getEncryptedPin());
        cardDTO.setExpiryDays(request.getExpiryDays());
        cardDTO.setExpiryDaysRound(request.getExpiryDaysRound());
        cardDTO.setPersonalizationProductCode(request.getPersonalizationProductCode());
        cardDTO.setPredecessorCardId(request.getPredecessorCardId());
        cardDTO.setDisableAutomaticRenewal(request.getDisableAutomaticRenewal());

        if (request.getSecure3DSettings() != null) {
            cardDTO.setSecure3DMobile(request.getSecure3DSettings().getMobile());
            cardDTO.setSecure3DEmail(request.getSecure3DSettings().getEmail());
            cardDTO.setSecure3DOutOfBandEnabled(request.getSecure3DSettings().getOutOfBandEnabled());
            cardDTO.setSecure3DOutOfBandId(request.getSecure3DSettings().getOutOfBandId());
        }

        if (request.getCardNotificationSettings() != null) {
            cardDTO.setNotificationReceiptsReminderEnabled(request.getCardNotificationSettings().getReceiptsReminderEnabled());
            cardDTO.setNotificationInstantSpendUpdateEnabled(request.getCardNotificationSettings().getInstantSpendUpdateEnabled());
        }

        if (request.getDeliveryAddress() != null) {
            cardDTO.setDeliveryFirstName(request.getDeliveryAddress().getFirstName());
            cardDTO.setDeliveryLastName(request.getDeliveryAddress().getLastName());
            cardDTO.setDeliveryCompanyName(request.getDeliveryAddress().getCompanyName());
            cardDTO.setDeliveryAddress1(request.getDeliveryAddress().getAddress1());
            cardDTO.setDeliveryAddress2(request.getDeliveryAddress().getAddress2());
            cardDTO.setDeliveryPostalCode(request.getDeliveryAddress().getPostalCode());
            cardDTO.setDeliveryCity(request.getDeliveryAddress().getCity());
            cardDTO.setDeliveryCountryCode(request.getDeliveryAddress().getCountryCode());
            cardDTO.setDeliveryDispatchMethod(request.getDeliveryAddress().getDispatchMethod());
            cardDTO.setDeliveryPhone(request.getDeliveryAddress().getPhone());
            cardDTO.setDeliveryTrackingNumber(request.getDeliveryAddress().getTrackingNumber());
        }

        if (request.getLimits() != null) {
            cardDTO.setLimitDailyPurchase(request.getLimits().getDailyPurchase());
            cardDTO.setLimitDailyWithdrawal(request.getLimits().getDailyWithdrawal());
            cardDTO.setLimitMonthlyPurchase(request.getLimits().getMonthlyPurchase());
            cardDTO.setLimitMonthlyWithdrawal(request.getLimits().getMonthlyWithdrawal());
            cardDTO.setLimitTransactionPurchase(request.getLimits().getTransactionPurchase());
            cardDTO.setLimitDailyContactlessPurchase(request.getLimits().getDailyContactlessPurchase());
            cardDTO.setLimitDailyInternetPurchase(request.getLimits().getDailyInternetPurchase());
            cardDTO.setLimitWeeklyContactlessPurchase(request.getLimits().getWeeklyContactlessPurchase());
            cardDTO.setLimitWeeklyInternetPurchase(request.getLimits().getWeeklyInternetPurchase());
            cardDTO.setLimitWeeklyPurchase(request.getLimits().getWeeklyPurchase());
            cardDTO.setLimitWeeklyWithdrawal(request.getLimits().getWeeklyWithdrawal());
            cardDTO.setLimitMonthlyContactlessPurchase(request.getLimits().getMonthlyContactlessPurchase());
            cardDTO.setLimitMonthlyInternetPurchase(request.getLimits().getMonthlyInternetPurchase());
            cardDTO.setLimitAllTimePurchase(request.getLimits().getAllTimePurchase());
            cardDTO.setLimitAllTimeWithdrawal(request.getLimits().getAllTimeWithdrawal());
            cardDTO.setLimitAllTimeContactlessPurchase(request.getLimits().getAllTimeContactlessPurchase());
            cardDTO.setLimitAllTimeInternetPurchase(request.getLimits().getAllTimeInternetPurchase());
            //            cardDTO.setLimitOverallPurchase(request.getLimits().getOverallPurchase());
        }

        if (request.getSecurity() != null) {
            cardDTO.setSecurityContactlessEnabled(request.getSecurity().getContactlessEnabled());
            cardDTO.setSecurityWithdrawalEnabled(request.getSecurity().getWithdrawalEnabled());
            cardDTO.setSecurityInternetPurchaseEnabled(request.getSecurity().getInternetPurchaseEnabled());
            cardDTO.setSecurityOverallLimitsEnabled(request.getSecurity().getOverallLimitsEnabled());
            cardDTO.setSecurityAllTimeLimitsEnabled(request.getSecurity().getAllTimeLimitsEnabled());
        }

        if (request.getSeparatedEmbossingName() != null) {
            cardDTO.setEmbossingFirstName(request.getSeparatedEmbossingName().getFirstName());
            cardDTO.setEmbossingLastName(request.getSeparatedEmbossingName().getLastName());
        }

        CardDTO savedCard = cardService.save(cardDTO);
        return ResponseEntity.ok(new WallesterCardResponse(new WallesterCard(savedCard)));
    }

    @Operation(tags = { "Account" }, summary = "Get account statement", description = "Returns account statement records")
    @GetMapping("/v1/accounts/{account_id}/statement")
    public ResponseEntity<WallesterStatementResponse> getAccountStatement(
        @PathVariable("account_id") String accountId,
        @RequestParam(value = "from_date", required = false) String fromDate,
        @RequestParam(value = "to_date", required = false) String toDate,
        @RequestParam(value = "from_record", required = true) int fromRecord,
        @RequestParam(value = "records_count", required = true) int recordsCount,
        @RequestParam(value = "order_field", required = false) String orderField,
        @RequestParam(value = "order_direction", required = false) String orderDirection
    ) {
        log.info(
            String.format(
                "GET /v1/accounts/%s/statement with params: from_date=%s, to_date=%s, from_record=%d, records_count=%d, order_field=%s, order_direction=%s",
                accountId,
                fromDate,
                toDate,
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
            orderField = "date";
        }

        // Validate order field
        if (!isValidStatementOrderField(orderField)) {
            throw new WallesterApiException(422, "Invalid order field");
        }

        // Create sort object
        Sort sort = Sort.by(orderDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, convertToCamelCase(orderField));

        // Create page request with sorting
        PageRequest pageRequest = PageRequest.of(fromRecord / recordsCount, recordsCount, sort);

        // Parse dates
        Instant fromDateInstant = fromDate != null ? Instant.parse(fromDate) : null;
        Instant toDateInstant = toDate != null ? Instant.parse(toDate) : null;

        // Get statement records
        Page<WallesterStatementRecord> recordsPage = accountStatementService.getAccountStatement(
            UUID.fromString(accountId),
            fromDateInstant,
            toDateInstant,
            pageRequest
        );
        // Create response
        WallesterStatementResponse response = new WallesterStatementResponse(recordsPage.getContent());

        return ResponseEntity.ok(response);
    }

    @Operation(tags = { "Account" }, summary = "Adjust account balance", description = "Adjusts the balance of an account")
    @PatchMapping("/v1/accounts/{account_id}/balance")
    public ResponseEntity<WallesterAccountResponse> adjustAccountBalance(
        @PathVariable("account_id") String accountId,
        @RequestBody WallesterAdjustmentRequest request
    ) {
        log.info(String.format("PATCH /v1/accounts/%s/balance with request: %s", accountId, request));

        // Validate request
        if (request.getAmount() == null) {
            throw new WallesterApiException(422, "Amount is required");
        }
        if (StringUtils.isBlank(request.getDescription())) {
            throw new WallesterApiException(422, "Description is required");
        }

        // Call service to adjust balance
        // TODO: Implement the actual balance adjustment logic in AccountStatementService
        // The service should:
        // 1. Validate if negative balance is allowed
        // 2. Create a new statement record for the adjustment
        // 3. Update the account balance
        // 4. Return the created statement record
        WallesterAccount adjustedAccount = accountStatementService.adjustAccountBalance(UUID.fromString(accountId), request);

        return ResponseEntity.ok(new WallesterAccountResponse(adjustedAccount));
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

    private boolean isValidCardOrderField(String orderField) {
        return Arrays.asList(
            "embossing_name",
            "masked_card_number",
            "status",
            "type",
            "personalization_product_code",
            "name",
            "expiry_date",
            "updated_at",
            "block_type"
        ).contains(orderField);
    }

    private boolean isValidStatementOrderField(String orderField) {
        return switch (orderField) {
            case "date",
                "purchase_date",
                "transaction_amount",
                "account_amount",
                "total_amount",
                "merchant_name",
                "merchant_city",
                "merchant_country_code" -> true;
            default -> false;
        };
    }
}
