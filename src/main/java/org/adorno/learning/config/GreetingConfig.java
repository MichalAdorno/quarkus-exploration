package org.adorno.learning.config;

import io.quarkus.arc.config.ConfigProperties;

import java.util.Optional;

@ConfigProperties(prefix = "greeting")
public class GreetingConfig {

    public String title;
    public Optional<String> name;
    public String suffix = "!";

}
