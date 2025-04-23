package io.github.vsinkievic.mockwallesterapi;

import io.github.vsinkievic.mockwallesterapi.config.AsyncSyncConfiguration;
import io.github.vsinkievic.mockwallesterapi.config.EmbeddedSQL;
import io.github.vsinkievic.mockwallesterapi.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { MockWallesterApiApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {
}
