package io.github.vsinkievic.mockwallesterapi.web.rest;

import io.github.vsinkievic.mockwallesterapi.repository.AccountStatementRecordRepository;
import io.github.vsinkievic.mockwallesterapi.service.AccountStatementRecordService;
import io.github.vsinkievic.mockwallesterapi.service.dto.AccountStatementRecordDTO;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord}.
 */
@RestController
@RequestMapping("/api/account-statement-records")
public class AccountStatementRecordResource {

    private static final Logger LOG = LoggerFactory.getLogger(AccountStatementRecordResource.class);

    private static final String ENTITY_NAME = "accountStatementRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountStatementRecordService accountStatementRecordService;

    private final AccountStatementRecordRepository accountStatementRecordRepository;

    public AccountStatementRecordResource(
        AccountStatementRecordService accountStatementRecordService,
        AccountStatementRecordRepository accountStatementRecordRepository
    ) {
        this.accountStatementRecordService = accountStatementRecordService;
        this.accountStatementRecordRepository = accountStatementRecordRepository;
    }

    /**
     * {@code POST  /account-statement-records} : Create a new accountStatementRecord.
     *
     * @param accountStatementRecordDTO the accountStatementRecordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountStatementRecordDTO, or with status {@code 400 (Bad Request)} if the accountStatementRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AccountStatementRecordDTO> createAccountStatementRecord(
        @RequestBody AccountStatementRecordDTO accountStatementRecordDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save AccountStatementRecord : {}", accountStatementRecordDTO);
        if (accountStatementRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountStatementRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        accountStatementRecordDTO = accountStatementRecordService.save(accountStatementRecordDTO);
        return ResponseEntity.created(new URI("/api/account-statement-records/" + accountStatementRecordDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, accountStatementRecordDTO.getId().toString())
            )
            .body(accountStatementRecordDTO);
    }

    /**
     * {@code PUT  /account-statement-records/:id} : Updates an existing accountStatementRecord.
     *
     * @param id the id of the accountStatementRecordDTO to save.
     * @param accountStatementRecordDTO the accountStatementRecordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountStatementRecordDTO,
     * or with status {@code 400 (Bad Request)} if the accountStatementRecordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountStatementRecordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountStatementRecordDTO> updateAccountStatementRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody AccountStatementRecordDTO accountStatementRecordDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AccountStatementRecord : {}, {}", id, accountStatementRecordDTO);
        if (accountStatementRecordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountStatementRecordDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountStatementRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        accountStatementRecordDTO = accountStatementRecordService.update(accountStatementRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, accountStatementRecordDTO.getId().toString()))
            .body(accountStatementRecordDTO);
    }

    /**
     * {@code PATCH  /account-statement-records/:id} : Partial updates given fields of an existing accountStatementRecord, field will ignore if it is null
     *
     * @param id the id of the accountStatementRecordDTO to save.
     * @param accountStatementRecordDTO the accountStatementRecordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountStatementRecordDTO,
     * or with status {@code 400 (Bad Request)} if the accountStatementRecordDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accountStatementRecordDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountStatementRecordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountStatementRecordDTO> partialUpdateAccountStatementRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody AccountStatementRecordDTO accountStatementRecordDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AccountStatementRecord partially : {}, {}", id, accountStatementRecordDTO);
        if (accountStatementRecordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountStatementRecordDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountStatementRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountStatementRecordDTO> result = accountStatementRecordService.partialUpdate(accountStatementRecordDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, accountStatementRecordDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /account-statement-records} : get all the accountStatementRecords.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountStatementRecords in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AccountStatementRecordDTO>> getAllAccountStatementRecords(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of AccountStatementRecords");
        Page<AccountStatementRecordDTO> page = accountStatementRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-statement-records/:id} : get the "id" accountStatementRecord.
     *
     * @param id the id of the accountStatementRecordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountStatementRecordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountStatementRecordDTO> getAccountStatementRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get AccountStatementRecord : {}", id);
        Optional<AccountStatementRecordDTO> accountStatementRecordDTO = accountStatementRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountStatementRecordDTO);
    }

    /**
     * {@code DELETE  /account-statement-records/:id} : delete the "id" accountStatementRecord.
     *
     * @param id the id of the accountStatementRecordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountStatementRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete AccountStatementRecord : {}", id);
        accountStatementRecordService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
