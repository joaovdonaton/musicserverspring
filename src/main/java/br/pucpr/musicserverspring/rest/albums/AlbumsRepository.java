package br.pucpr.musicserverspring.rest.albums;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumsRepository extends JpaRepository<Album, Long> {
}
