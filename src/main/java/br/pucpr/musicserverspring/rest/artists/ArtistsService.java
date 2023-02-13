package br.pucpr.musicserverspring.rest.artists;

import br.pucpr.musicserverspring.lib.exception.BadRequestException;
import br.pucpr.musicserverspring.lib.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistsService {
    private final ArtistsRepository repository;

    public ArtistsService(ArtistsRepository repository) {
        this.repository = repository;
    }

    public List<Artist> search(String genre){
        return genre == null ? repository.findAll(Sort.by(Sort.Order.asc("name"))) : repository.findByGenre(genre);
    }

    public Artist add(Artist artist){
        if(artist == null) throw new BadRequestException("Artist cannot be null!");
        if(artist.getId() != null) throw new BadRequestException("Cannot save artist with an id!");
        return repository.save(artist);
    }

    public void delete(Long id){
        if(!repository.existsById(id)) throw new NotFoundException(id);
        repository.deleteById(id);
    }

    public Artist getById(Long id){
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }
}
