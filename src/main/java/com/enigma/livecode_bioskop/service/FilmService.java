package com.enigma.livecode_bioskop.service;

import java.util.List;

import com.enigma.livecode_bioskop.models.entity.Film;

public interface FilmService {
    public Film createFilm(Film film);    
    Film findFilmById(Integer id);
    List<Film> getAllFilm();
    void updateFilm(Film film);
    void deleteFilmByid(Integer id);
}
