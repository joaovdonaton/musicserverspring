package br.pucpr.musicserverspring.rest.albums;

import br.pucpr.musicserverspring.rest.artists.Artist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Album {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer year;

    @JsonIgnore
    @ManyToMany(mappedBy = "albums")
    private Set<Artist> artists;

    public Album(String name, Integer year) {
        this.name = name;
        this.year = year;
    }
}
