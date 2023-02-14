package br.pucpr.musicserverspring.rest.albums;

import br.pucpr.musicserverspring.lib.exception.NotFoundException;
import br.pucpr.musicserverspring.rest.artists.Artist;
import br.pucpr.musicserverspring.rest.artists.ArtistsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumsService {
    private AlbumsRepository repository;
    private ArtistsService artistsService;

    public AlbumsService(AlbumsRepository repository, ArtistsService artistsService) {
        this.repository = repository;
        this.artistsService = artistsService;
    }

    public Album addAlbum(Album album, Set<Long> artistIds){
        // adicionar artistas ao album
        // https://www.devmedia.com.br/manytomany-hibernate-variacoes-unidirecional-e-bidirecional/29535
        // https://stackoverflow.com/questions/7434115/jpa-confusion-managed-vs-non-managed-entities
        Set<Artist> artists = new HashSet<>();
        album.setArtists(artistIds.stream().map(id -> {
            var artist = artistsService.getById(id);
            artists.add(artist);
            return artist;
        }).collect(Collectors.toSet()));

        var savedAlbum = repository.save(album);

        // adicionar album aos artistas do outro lado da relação
        artists.forEach(artist -> {
            var albums = artist.getAlbums();
            albums.add(savedAlbum);
            artist.setAlbums(albums);
        });

        return savedAlbum;
    }

    public void deleteAlbum(Long id) {
        var artists = repository.findById(id).orElseThrow(NotFoundException::new).getArtists();

        //remover albums dos artists
        artists.forEach(a -> {
            var albums = a.getAlbums();
            albums.removeIf((album) -> album.getId().equals(id));
            a.setAlbums(albums);
        });

        //remover album
        repository.deleteById(id);
    }

    public Set<Album> search(Long artistId, Integer from, Integer to, String genre) {
        Set<Album> albums = new HashSet<>(repository.findAll());
        if(artistId != null){
            albums = artistsService.getById(artistId).getAlbums();
        }

        if(from != null){
            albums = albums.stream().filter(album -> album.getYear() >= from).collect(Collectors.toSet());
        }

        if(to != null){
            albums = albums.stream().filter(album -> album.getYear() <= to).collect(Collectors.toSet());
        }

        if(genre != null){
            albums = albums.stream().filter(album -> {
                var artists = album.getArtists();

                return artists.stream().anyMatch(artist -> artist.getGenres().contains(genre));
            }).collect(Collectors.toSet());
        }

        return albums;
    }
}
