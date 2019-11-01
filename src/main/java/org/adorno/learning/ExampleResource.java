package org.adorno.learning;

import org.adorno.learning.config.GreetingConfig;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ExampleResource {

    @Inject
    private GreetingConfig greetingConfig;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        return String.format(
                "hello %s %s %s",
                greetingConfig.title,
                greetingConfig.name.orElse("XXX"),
                greetingConfig.suffix);

    }
}