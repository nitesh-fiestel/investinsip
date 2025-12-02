package org.investinsip;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.investinsip.health.TemplateHealthCheck;
import org.investinsip.resource.InvestmentResource;
import org.investinsip.resources.VisitResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class InvestinsipApplication extends Application<InvestinsipConfiguration> {
    public static void main(String[] args) throws Exception {
        new InvestinsipApplication().run("server", "config.yml");
    }

    @Override
    public String getName() {
        return "investinsip";
    }

    @Override
    public void initialize(Bootstrap<InvestinsipConfiguration> bootstrap) {
        // Serve static files from the classpath
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(InvestinsipConfiguration configuration, Environment environment) {
        System.out.println("Starting " + configuration.getAppConfig().getAppName());
        
        // Configure CORS
        configureCors(environment);
        
        // Register resources
        final InvestmentResource resource = new InvestmentResource();
        environment.jersey().register(resource);
        
        // Register visit tracking resource
        final String dataDir = configuration.getAppConfig().getDataDir();
        environment.jersey().register(new VisitResource(dataDir + "/visits.json"));

        // Register health checks
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck();
        environment.healthChecks().register("template", healthCheck);
    }
    
    private void configureCors(Environment environment) {
        // Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
