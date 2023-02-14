package br.pucpr.musicserverspring.rest.artists;

import br.pucpr.musicserverspring.rest.albums.Album;
import br.pucpr.musicserverspring.rest.artists.DTO.ArtistRegisterDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/artists")
public class ArtistsController {
    private ArtistsService service;

    public ArtistsController(ArtistsService service) {
        this.service = service;
    }

    @GetMapping
    @Transactional
    public List<Artist> search(@RequestParam(value = "genre", required = false) String genre){
        return service.search(genre);
    }

    @PostMapping
    @Transactional
    @RolesAllowed("ADMIN")
    @SecurityRequirement(name = "authServer")
    public Artist add(@RequestBody @Valid ArtistRegisterDTO artist){
        return service.add(new Artist(artist.getName(), artist.getGenres()));
    }

    @DeleteMapping("{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    @SecurityRequirement(name = "authServer")
    public void delete(@PathVariable("id") Long id){
         service.delete(id);
    }

    @GetMapping("{id}/albums")
    public Set<Album> artistAlbums(@PathVariable("id") Long id){
        return service.getAlbumsByArtistId(id);
    }
}
