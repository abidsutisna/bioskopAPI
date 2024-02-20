package com.enigma.livecode_bioskop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.livecode_bioskop.models.entity.Film;
import com.enigma.livecode_bioskop.models.repos.FilmRepository;

@Service
public class FilmServiceImpl implements FilmService {
    
    @Autowired
    private FilmRepository filmRepository;

    @Override
    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film findFilmById(Integer id) {
        return filmRepository.findById(id).get();
    }

    @Override
    public List<Film> getAllFilm() {
        return filmRepository.findAll();
    }

    @Override
    public void updateFilm(Film film) {
        this.filmRepository.findById(film.getId()).get();
        this.filmRepository.save(film);
    }

    @Override
    public void deleteFilmByid(Integer id) {
        this.filmRepository.deleteById(id);
    }
    
}
