package br.pucpr.musicserverspring.rest.artists.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class ArtistRegisterDTO {
    @NotBlank
    private String name;
    @NotEmpty
    private Set<String> genres;
}
