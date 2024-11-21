package com.dobleadev.musixplorer.main;

import com.dobleadev.musixplorer.model.*;
import com.dobleadev.musixplorer.repository.SingerRepository;

import java.util.*;

public class Main {
    Scanner input = new Scanner(System.in);
    private SingerRepository repository;
    private List<Singer> singers;
    private Optional<Singer> foundSinger;

    public Main(SingerRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Registrar datos de cantantes 
                    2 - Registrar datos de canciones
                    3 - Buscar canciones por cantante
                    4 - Buscar canciones por titulo
                    5 - Buscar canciones por genero
                    6 - Mostrar Top 5 canciones
                    7 - Mostrar Top 5 cantantes
                    8 - Mostrar Top 5 generos de musica
                    9 - Buscar cantante
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    registrarCantante();
                    break;

                case 2:
                    registrarCancion();
                    break;

                case 3:
                    buscarCancionesPorCantante();
                    break;

                case 4:
                    buscarCancionesPorNombre();
                    break;

                case 5:
                    buscarCancionesPorGenero();
                    break;

                case 6:
                    mostrarTopCanciones();
                    break;

                case 7:
                    mostrarTopCantantes();
                    break;

                case 8:
                    mostrarTopGenerosMusicales();
                    break;

                case 9:
                    buscarCantantePorNombre();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void registrarCantante() {
        System.out.print("Ingrese el nombre del cantante: ");
        String singerName = input.nextLine();
        System.out.print("Ingrese la fecha de nacimiento del cantante: ");
        String singerBirthDate = input.nextLine();
        repository.save(new Singer(singerName, singerBirthDate));
        System.out.println("Cantante agregado correctamente.\n");
    }

    private void registrarCancion() {
        buscarCantantePorNombre();
        if (foundSinger.isPresent()) {
            System.out.print("Ingrese el nombre de la cancion: ");
            String songName = input.nextLine();
            System.out.print("Ingrese la duracion de la cancion (en segundos): ");
            int songDuration = input.nextInt();
            input.nextLine();
            System.out.print("Ingrese el genero de la cancion: ");
            Genre.showAll();
            String genreSearch = input.nextLine();
            Genre songGenre = Genre.fromString(genreSearch);
            System.out.print("Ingrese la puntuacion de la cancion: ");
            Double songRating = input.nextDouble();
            Song newSong = new Song(songName, songDuration, songGenre, songRating);
            newSong.setSinger(foundSinger.get());
            foundSinger.get().getSongs().add(newSong);
            repository.save(foundSinger.get());
        }
    }

    private void buscarCancionesPorCantante() {
        buscarCantantePorNombre();
        if (foundSinger.isPresent()) {
            List<Song> foundSongs = repository.findBySingerNameContainingIgnoreCase(foundSinger.get().getName());
            foundSongs.forEach(System.out::println);
        }
    }

    private void buscarCancionesPorNombre() {
        System.out.print("Ingrese el nombre de la cancion: ");
        String songName = input.nextLine();
        List<Song> foundSongs = repository.findByTitleContainingIgnoreCase(songName);
        foundSongs.forEach(System.out::println);
    }

    private void buscarCancionesPorGenero() {
        System.out.print("Ingrese el nombre del genero: ");
        String genreName = input.nextLine();
        List<Song> foundSongs = repository.findByGenre(Genre.fromString(genreName));
        foundSongs.forEach(System.out::println);
    }

    private void mostrarTopCanciones() {
        System.out.println("Mostrando el Top 5 mejores canciones: ");
        List<Song> topSongs = repository.findTop5SongsByRating();
        topSongs.forEach(song -> System.out.println("Cancion: " + song.getName() + " - Rating: " + song.getRating()));
    }

    private void mostrarTopCantantes() {
        System.out.println("Mostrando el Top 5 mejores cantantes: ");
        List<SingerRating> topSingers = repository.findTop5SingersByAverageRating();
        topSingers.forEach(singer -> System.out.println("Cantante: " + singer.singer() + " - Rating: " + singer.rating()));
    }

    private void mostrarTopGenerosMusicales() {
        System.out.println("Mostrando el Top 5 mejores generos: ");
        List<GenreRating> topGenres = repository.findTop5GenresByAverageRating();
        topGenres.forEach(genre -> System.out.println("Genero: " + genre.genre() + " - Rating: " + genre.rating()));
    }

    private void buscarCantantePorNombre() {
        List<Singer> singers = repository.findAll();
        singers.forEach(System.out::println);

        System.out.print("Ingrese el nombre del cantante: ");
        String singerName = input.nextLine();
        foundSinger = repository.findByNameContainsIgnoreCase(singerName);

        if (foundSinger.isPresent()) {
            System.out.println(foundSinger.get());
        } else {
            System.out.println("ERROR: cantante no encontrado");
        }
    }

}
