package br.pucpr.musicserverspring.rest.albums;

import br.pucpr.musicserverspring.rest.artists.Artist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Objects;
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
    @ToString.Exclude
    private Set<Artist> artists;

    public Album(String name, Integer year) {
        this.name = name;
        this.year = year;
    }

    // NÃO leva em consideração se os artistas são diferentes
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;

        var objCast = (Album) obj;

        return this.name.equals(objCast.name) && this.year.equals(objCast.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.year);
    }
}
