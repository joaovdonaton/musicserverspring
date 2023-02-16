package br.pucpr.musicserverspring.rest.artists;

import br.pucpr.musicserverspring.rest.albums.Album;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@NamedQuery(name="Artist.findByGenre", query = "SELECT a from Artist a join a.genres g where g = :genre ORDER BY a.name")
public class Artist {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "GENRES", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "genre")
    private Set<String> genres;

    @ManyToMany
    @JoinTable(name = "artists_albums",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    @ToString.Exclude
    private Set<Album> albums;

    public Artist(String name, Set<String> genres) {
        this.name = name;
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Artist artist = (Artist) o;
        return id != null && Objects.equals(id, artist.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
