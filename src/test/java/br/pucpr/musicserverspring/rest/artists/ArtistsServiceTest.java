package br.pucpr.musicserverspring.rest.artists;

import br.pucpr.musicserverspring.lib.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArtistsServiceTest {
    private ArtistsRepository repository;
    private ArtistsService service;

    @BeforeEach
    public void setup(){
        repository = mock(ArtistsRepository.class);
        service = new ArtistsService(repository);
    }

    @Test
    public void addShouldCallSaveIfHasAnArtist(){
        var artist = new Artist();
        artist.setName("artists");
        artist.setGenres(Set.of("rock"));

        var savedArtist = new Artist();
        savedArtist.setId(1L);
        artist.setName("artists");
        artist.setGenres(Set.of("rock"));
        when(repository.save(artist)).thenReturn(savedArtist);

        var ret = service.add(artist);

        assertEquals(savedArtist, ret);
        assertNotNull(ret.getId());
    }

    @Test
    public void shouldThrowBadRequestWhenNull(){
        assertThrows(BadRequestException.class, () -> {
            service.add(null);
        });
    }
    @Test
    public void shouldThrowBadRequestWhenIdNotNull(){
        var artist = new Artist();
        artist.setId(1L);
        artist.setName("artists");
        artist.setGenres(Set.of("rock"));

        assertThrows(BadRequestException.class, () -> {
            service.add(artist);
        });
    }
}
