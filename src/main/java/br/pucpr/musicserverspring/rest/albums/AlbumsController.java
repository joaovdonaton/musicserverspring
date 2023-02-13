package br.pucpr.musicserverspring.rest.albums;

import br.pucpr.musicserverspring.rest.albums.DTO.AlbumRegisterDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable("id") Long id){
        service.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
