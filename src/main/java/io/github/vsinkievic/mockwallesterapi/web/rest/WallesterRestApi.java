package io.github.vsinkievic.mockwallesterapi.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Ping endpoint", description = "Returns the input string with '-pong' suffix")
    @GetMapping("/ping")
    public ResponseEntity<String> ping(@RequestParam String request) {
        log.info("Ping request: {}", request);
        return ResponseEntity.ok(request + "-pong");
    }
    // Add your API endpoints here

}
