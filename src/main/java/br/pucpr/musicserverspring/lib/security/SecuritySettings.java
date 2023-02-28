package br.pucpr.musicserverspring.lib.security;

import br.pucpr.musicserverspring.rest.users.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/security.properties")
@ConfigurationProperties("security")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecuritySettings {
    @NotBlank
    private String issuer;
    @NotBlank
    private String secret;
    private boolean testUserAllowed;
    private String token;
    private User testUser;
}
