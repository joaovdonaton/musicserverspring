package br.pucpr.musicserverspring.rest.albums;

import br.pucpr.musicserverspring.lib.exception.NotFoundException;
import br.pucpr.musicserverspring.rest.artists.ArtistsRepository;
import br.pucpr.musicserverspring.rest.artists.ArtistsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class AlbumsServiceTest {
    private AlbumsRepository repository;
    private ArtistsService artistsService;
    private AlbumsService service;

    @BeforeEach
    public void setup(){
        repository = mock(AlbumsRepository.class);
        artistsService = mock(ArtistsService.class);
        service = new AlbumsService(repository, artistsService);
    }

    @Test
    public void shouldThrowNotFoundIfAlbumArtistIdDoesntExist(){
        Album album = new Album("O Album", 2007);

        Long notFoundId = 999L;

        Set<Long> artistIds = Set.of(notFoundId);

        when(artistsService.getById(notFoundId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> {
           service.addAlbum(album, artistIds);
        });
    }
}
