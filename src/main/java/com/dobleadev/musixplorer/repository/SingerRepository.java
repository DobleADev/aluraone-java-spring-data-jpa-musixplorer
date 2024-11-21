package com.dobleadev.musixplorer.repository;

import com.dobleadev.musixplorer.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SingerRepository extends JpaRepository<Singer, Long> {
    Optional<Singer> findByNameContainsIgnoreCase(String singerName);
    @Query("SELECT s FROM Song s JOIN s.singer si WHERE si.name LIKE %:singerName%")
    List<Song> findBySingerNameContainingIgnoreCase(String singerName);

    @Query("SELECT s FROM Song s WHERE s.genre = :genre")
    List<Song> findByGenre(Genre genre);

    @Query(value = "SELECT s FROM Song s ORDER BY s.rating DESC LIMIT 5")
    List<Song> findTop5SongsByRating();

    @Query(value = "SELECT s FROM Song s WHERE s.title ILIKE %:title%")
    List<Song> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT new com.dobleadev.musixplorer.model.SingerRating(s.singer, AVG(s.rating)) FROM Song s GROUP BY s.singer ORDER BY AVG(s.rating) DESC")
    List<SingerRating> findTop5SingersByAverageRating();

    @Query("SELECT new com.dobleadev.musixplorer.model.GenreRating(s.genre, AVG(s.rating)) FROM Song s GROUP BY s.genre ORDER BY AVG(s.rating) DESC")
    List<GenreRating> findTop5GenresByAverageRating();
}
