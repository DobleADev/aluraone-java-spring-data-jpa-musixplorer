package com.dobleadev.musixplorer.model;

import java.util.Arrays;

public enum Genre {
    BACHATA("Bachata"),
    SALSA("Salsa"),
    MERENGUE("Merengue"),
    POP("Pop"),
    ROCK("Rock"),
    EDM("Edm");

    private String genre;

    Genre (String genre) {
        this.genre = genre;
    }
    public static void showAll() {
        Arrays.stream(Genre.values())
                .forEach(System.out::println);
    }

    public static Genre fromString(String text) {
        for (Genre categoria : Genre.values()) {
            if (categoria.genre.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
