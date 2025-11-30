package org.investinsip;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.investinsip.health.TemplateHealthCheck;
import org.investinsip.resource.InvestmentResource;

import javax.servlet.DispatcherType;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.util.EnumSet;

public class SimpleServer extends Application<SimpleConfig> {
    public static void main(String[] args) throws Exception {
        // Delegate to Dropwizard with CLI args so Gradle's run task (server config.yml) is honored
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
        env.jersey().register(new InvestmentResource());
        
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
