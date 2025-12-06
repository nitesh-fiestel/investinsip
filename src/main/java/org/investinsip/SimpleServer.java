package org.investinsip;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.investinsip.core.AnalyticsManager;
import org.investinsip.core.RateLimitFilter;
import org.investinsip.core.SecurityHeadersFilter;
import org.investinsip.health.TemplateHealthCheck;
import org.investinsip.resource.InvestmentResource;
import org.investinsip.resources.AnalyticsResource;
import org.investinsip.resources.SipResource;
import org.investinsip.resources.SwpResource;

import javax.servlet.DispatcherType;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.util.EnumSet;

public class SimpleServer extends Application<SimpleConfig> {
    public static void main(String[] args) throws Exception {
        // Delegate to Dropwizard with CLI args so Gradle's run task (server config.yml)
        // is honored
        new SimpleServer().run(args);
    }

    @Override
    public void initialize(Bootstrap<SimpleConfig> bootstrap) {
        // Serve static files from /assets directory under /
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public String getName() {
        return "investinsip-simple";
    }

    @Override
    public void run(SimpleConfig config, Environment env) {
        System.out.println("Running SimpleServer");

        // Register resource
        // Analytics
        final AnalyticsManager analyticsManager = new AnalyticsManager(env.getObjectMapper());
        final AnalyticsResource analyticsResource = new AnalyticsResource(analyticsManager);
        env.jersey().register(analyticsResource);

        final InvestmentResource resource = new InvestmentResource();
        final SipResource sipResource = new SipResource();
        final SwpResource swpResource = new SwpResource();
        env.jersey().register(resource);
        env.jersey().register(sipResource);
        env.jersey().register(swpResource);

        // Security Filters
        env.servlets().addFilter("RateLimitFilter", new RateLimitFilter())
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        env.servlets().addFilter("SecurityHeadersFilter", new SecurityHeadersFilter())
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // Register health check
        env.healthChecks().register("template", new TemplateHealthCheck());

        // Configure CORS
        env.servlets().addFilter("CORS", CrossOriginFilter.class)
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // Add CORS headers
        env.jersey().register(new ContainerResponseFilter() {
            @Override
            public void filter(ContainerRequestContext requestContext,
                    ContainerResponseContext responseContext) {
                responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
                responseContext.getHeaders().add("Access-Control-Allow-Headers", "*");
                responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            }
        });
    }
}
