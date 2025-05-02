package io.github.vsinkievic.mockwallesterapi.web.rest;

import io.github.vsinkievic.mockwallesterapi.service.CompanyService;
import io.github.vsinkievic.mockwallesterapi.service.dto.CompanyDTO;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Wallester API endpoints.
 */
@Tag(name = "wallester-api", description = "Wallester API endpoints")
@RestController
@RequestMapping("/wallester/api")
@Slf4j
public class WallesterRestApi {

    private final CompanyService companyService;

    public WallesterRestApi(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Ping endpoint", description = "Returns the input string with '-pong' suffix")
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
    // Add your API endpoints here
}
