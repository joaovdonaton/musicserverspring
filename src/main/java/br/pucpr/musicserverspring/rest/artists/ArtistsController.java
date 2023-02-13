package br.pucpr.musicserverspring.rest.artists;

import br.pucpr.musicserverspring.rest.artists.DTO.ArtistRegisterDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Artist add(@RequestBody @Valid ArtistRegisterDTO artist){
        return service.add(new Artist(artist.getName(), artist.getGenres()));
    }

    @DeleteMapping("{id}")
    @Transactional
    public void delete(@PathVariable("id") Long id){
         service.delete(id);
    }
}
