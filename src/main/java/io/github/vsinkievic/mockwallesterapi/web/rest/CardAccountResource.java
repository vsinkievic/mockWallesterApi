package io.github.vsinkievic.mockwallesterapi.web.rest;

import io.github.vsinkievic.mockwallesterapi.repository.CardAccountRepository;
import io.github.vsinkievic.mockwallesterapi.service.CardAccountService;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link io.github.vsinkievic.mockwallesterapi.domain.CardAccount}.
 */
@RestController
@RequestMapping("/api/card-accounts")
public class CardAccountResource {

    private static final Logger LOG = LoggerFactory.getLogger(CardAccountResource.class);

    private static final String ENTITY_NAME = "cardAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CardAccountService cardAccountService;

    private final CardAccountRepository cardAccountRepository;

    public CardAccountResource(CardAccountService cardAccountService, CardAccountRepository cardAccountRepository) {
        this.cardAccountService = cardAccountService;
        this.cardAccountRepository = cardAccountRepository;
    }

    /**
     * {@code POST  /card-accounts} : Create a new cardAccount.
     *
     * @param cardAccountDTO the cardAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cardAccountDTO, or with status {@code 400 (Bad Request)} if the cardAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CardAccountDTO> createCardAccount(@Valid @RequestBody CardAccountDTO cardAccountDTO) throws URISyntaxException {
        LOG.debug("REST request to save CardAccount : {}", cardAccountDTO);
        if (cardAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new cardAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cardAccountDTO = cardAccountService.save(cardAccountDTO);
        return ResponseEntity.created(new URI("/api/card-accounts/" + cardAccountDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cardAccountDTO.getId().toString()))
            .body(cardAccountDTO);
    }

    /**
     * {@code PUT  /card-accounts/:id} : Updates an existing cardAccount.
     *
     * @param id the id of the cardAccountDTO to save.
     * @param cardAccountDTO the cardAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardAccountDTO,
     * or with status {@code 400 (Bad Request)} if the cardAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cardAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CardAccountDTO> updateCardAccount(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody CardAccountDTO cardAccountDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CardAccount : {}, {}", id, cardAccountDTO);
        if (cardAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cardAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cardAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cardAccountDTO = cardAccountService.update(cardAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cardAccountDTO.getId().toString()))
            .body(cardAccountDTO);
    }

    /**
     * {@code PATCH  /card-accounts/:id} : Partial updates given fields of an existing cardAccount, field will ignore if it is null
     *
     * @param id the id of the cardAccountDTO to save.
     * @param cardAccountDTO the cardAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardAccountDTO,
     * or with status {@code 400 (Bad Request)} if the cardAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cardAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cardAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CardAccountDTO> partialUpdateCardAccount(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody CardAccountDTO cardAccountDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CardAccount partially : {}, {}", id, cardAccountDTO);
        if (cardAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cardAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cardAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CardAccountDTO> result = cardAccountService.partialUpdate(cardAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cardAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /card-accounts} : get all the cardAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cardAccounts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CardAccountDTO>> getAllCardAccounts(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of CardAccounts");
        Page<CardAccountDTO> page = cardAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /card-accounts/:id} : get the "id" cardAccount.
     *
     * @param id the id of the cardAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cardAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CardAccountDTO> getCardAccount(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get CardAccount : {}", id);
        Optional<CardAccountDTO> cardAccountDTO = cardAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cardAccountDTO);
    }

    /**
     * {@code DELETE  /card-accounts/:id} : delete the "id" cardAccount.
     *
     * @param id the id of the cardAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardAccount(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete CardAccount : {}", id);
        cardAccountService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
