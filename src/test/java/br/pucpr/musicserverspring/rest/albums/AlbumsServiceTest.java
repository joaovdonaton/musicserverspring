package br.pucpr.musicserverspring.rest.albums;

import br.pucpr.musicserverspring.lib.exception.BadRequestException;
import br.pucpr.musicserverspring.lib.exception.NotFoundException;
import br.pucpr.musicserverspring.rest.artists.Artist;
import br.pucpr.musicserverspring.rest.artists.ArtistsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
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

    @Test
    public void shouldThrowNotFoundIfAlbumIdDoesntExist(){
        when(repository.findById(1L)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> {
            service.deleteAlbum(1L);
        });
    }

    @Test
    void shouldThrowBadRequestIfSearchToIsLessThanFrom(){
        assertThrows(BadRequestException.class, () -> {
            service.search(null, 2015, 2004, null);
        });
    }

    @Test
    void shouldOnlyReturnAlbumsFrom2004to2009(){
        stubAlbumsFindAll();

        var expectedAlbums = new HashSet<>(Set.of(
                new Album("Torn Beyond Reason", 2008),
                new Album("Final Phase", 2005),
                new Album("My Damnation", 2004))
        );

        assertEquals(service.search(null, 2004, 2009, null), expectedAlbums);
    }

    @Test
    void shouldReturnEmptySetIfNoResults(){
        stubAlbumsFindAll();

        assertTrue(service.search(null, 2022, null, null).isEmpty());
    }

    @Test
    void shouldOnlyReturnAlbumsBelongingToArtistId5(){
        stubAlbumsFindAll();


        var expectedResult = Set.of(
                new Album("Flesh Coffin", 2017),
                new Album("Tortured by Solitude", 1999)
        );

        var artist = mock(Artist.class);

        when(artistsService.getById(5L)).thenReturn(artist);
        when(artist.getAlbums()).thenReturn(expectedResult);

        assertEquals(service.search(5L, null, null, null), expectedResult);
    }

    private void stubAlbumsFindAll() {
        when(repository.findAll()).thenReturn(List.of(
                new Album("Torn Beyond Reason", 2008),
                new Album("Flesh Coffin", 2017),
                new Album("Desolation of Eden", 2010),
                new Album("Final Phase", 2005),
                new Album("My Damnation", 2004),
                new Album("Tortured by Solitude", 1999),
                new Album("Impenetrable Cerebral Fortress", 2018)
        ));
    }

}
