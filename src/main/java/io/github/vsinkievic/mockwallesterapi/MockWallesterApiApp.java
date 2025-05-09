package io.github.vsinkievic.mockwallesterapi;

import io.github.vsinkievic.mockwallesterapi.config.ApplicationProperties;
import io.github.vsinkievic.mockwallesterapi.config.CRLFLogConverter;
import io.github.vsinkievic.mockwallesterapi.web.rest.WallesterRestApi;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.jhipster.config.DefaultProfileUtil;
import tech.jhipster.config.JHipsterConstants;

@SpringBootApplication(exclude = { H2ConsoleAutoConfiguration.class })
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class MockWallesterApiApp {

    private static final Logger LOG = LoggerFactory.getLogger(MockWallesterApiApp.class);

    private final Environment env;

    public MockWallesterApiApp(Environment env) {
        this.env = env;
    }

    private static class EndpointInfo {

        private final String method;
        private final String path;
        private final String description;
        private final String[] tags;

        public EndpointInfo(String method, String path, String description, String[] tags) {
            this.method = method;
            this.path = path;
            this.description = description;
            this.tags = tags;
        }

        @Override
        public String toString() {
            return String.format("  %-6s %-40s - %s", method, path, description);
        }
    }

    private List<EndpointInfo> enumerateEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        Class<?> apiClass = WallesterRestApi.class;

        // Get base path from class-level RequestMapping
        String basePath = "";
        if (apiClass.isAnnotationPresent(RequestMapping.class)) {
            basePath = apiClass.getAnnotation(RequestMapping.class).value()[0];
        }

        for (Method method : apiClass.getDeclaredMethods()) {
            String httpMethod = "";
            String path = "";
            String description = "";
            String[] tags = new String[0];

            // Get HTTP method
            if (method.isAnnotationPresent(GetMapping.class)) {
                httpMethod = "GET";
                path = method.getAnnotation(GetMapping.class).value()[0];
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                httpMethod = "POST";
                path = method.getAnnotation(PostMapping.class).value()[0];
            } else if (method.isAnnotationPresent(PutMapping.class)) {
                httpMethod = "PUT";
                path = method.getAnnotation(PutMapping.class).value()[0];
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                httpMethod = "DELETE";
                path = method.getAnnotation(DeleteMapping.class).value()[0];
            } else if (method.isAnnotationPresent(PatchMapping.class)) {
                httpMethod = "PATCH";
                path = method.getAnnotation(PatchMapping.class).value()[0];
            }

            // Get description and tags from Operation annotation
            if (method.isAnnotationPresent(Operation.class)) {
                Operation operation = method.getAnnotation(Operation.class);
                description = operation.summary();
                tags = operation.tags();
            }

            if (!httpMethod.isEmpty() && !path.isEmpty()) {
                // Remove the base path from the endpoint path since it's already in the URL
                String endpointPath = path;
                if (endpointPath.startsWith(basePath)) {
                    endpointPath = endpointPath.substring(basePath.length());
                }
                endpoints.add(new EndpointInfo(httpMethod, endpointPath, description, tags));
            }
        }

        return endpoints;
    }

    private String formatEndpoints(List<EndpointInfo> endpoints) {
        StringBuilder sb = new StringBuilder();
        String currentCategory = "";

        // Group endpoints by their first tag
        endpoints.sort((e1, e2) -> {
            String tag1 = e1.tags.length > 0 ? e1.tags[0] : "";
            String tag2 = e2.tags.length > 0 ? e2.tags[0] : "";
            return tag1.compareTo(tag2);
        });

        for (EndpointInfo endpoint : endpoints) {
            String category = endpoint.tags.length > 0 ? endpoint.tags[0] : "Other";
            if (!category.equals(currentCategory)) {
                if (!currentCategory.isEmpty()) {
                    sb.append("\n");
                }
                currentCategory = category;
                sb.append("\t").append(StringUtils.capitalize(category)).append(" Endpoints:\n");
            }
            sb.append("\t").append(endpoint.toString()).append("\n");
        }

        return sb.toString();
    }

    /**
     * Initializes MockWallesterApi.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)
        ) {
            LOG.error(
                "You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time."
            );
        }
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)
        ) {
            LOG.error(
                "You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time."
            );
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MockWallesterApiApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String applicationName = env.getProperty("spring.application.name");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
            .filter(StringUtils::isNotBlank)
            .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.warn("The host name could not be determined, using `localhost` as fallback");
        }

        MockWallesterApiApp app = new MockWallesterApiApp(env);
        String endpointsList = app.formatEndpoints(app.enumerateEndpoints());

        LOG.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            """

            ----------------------------------------------------------------------------------------
            \tApplication '{}' is running!
            \tProfile(s):                        {}
            \t
            \tAccess URLs:
            \tWeb UI (Portal) Local:             {}://localhost:{}{}
            \tWeb UI (Portal) External:          {}://{}:{}{}
            \tWallester API Local (BASE_URL):    {}://localhost:{}{}wallester/api
            \tWallester API External (BASE_URL): {}://{}:{}{}wallester/api
            \t
            \tNote: Authentication is disabled for all Wallester API endpoints
            \t
            \tAvailable Wallester API Endpoints:
            {}
            ----------------------------------------------------------------------------------------""",
            applicationName,
            env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles(),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            endpointsList
        );
    }
}
