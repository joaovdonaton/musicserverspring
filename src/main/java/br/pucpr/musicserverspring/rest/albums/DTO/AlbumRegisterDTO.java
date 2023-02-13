package br.pucpr.musicserverspring.rest.albums.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AlbumRegisterDTO {
    @NotBlank
    private String name;
    @Positive
    private Integer year;
    private Set<Long> artistIds;
}
