package br.pucpr.musicserverspring.rest.albums;

import br.pucpr.musicserverspring.rest.albums.DTO.AlbumRegisterDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/albums")
public class AlbumsController {

    private AlbumsService service;

    public AlbumsController(AlbumsService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping
    public Album insertAlbum(@RequestBody @Valid AlbumRegisterDTO album) {
        return service.addAlbum(new Album(album.getName(), album.getYear()), album.getArtistIds());
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable("id") Long id){
        service.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @GetMapping("search")
    public Set<Album> searchAlbums(
            @RequestParam(required = false) Long artistId,
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer to,
            @RequestParam(required = false) String genre){
        return service.search(artistId, from, to, genre);
    }
}
