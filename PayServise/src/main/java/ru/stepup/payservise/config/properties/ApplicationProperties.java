package ru.stepup.payservise.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "service")
public class ApplicationProperties {

    private final String name;
    private final String family;
    private final String url;

    public ApplicationProperties(String name, String family, String url) {
        this.name = name;
        this.family = family;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ApplicationConfigProperties{" +
                "name = '" + name + '\'' +
                ", family = '" + family + '\'' +
                ", url = '" + url + '\'' +
                '}';
    }
}
