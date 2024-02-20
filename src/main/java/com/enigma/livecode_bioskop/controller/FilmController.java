package com.enigma.livecode_bioskop.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.livecode_bioskop.dto.request.FilmDTO;
import com.enigma.livecode_bioskop.dto.response.ResponseDTO;
import com.enigma.livecode_bioskop.models.entity.Film;
import com.enigma.livecode_bioskop.models.entity.Theater;
import com.enigma.livecode_bioskop.service.FilmService;
import com.enigma.livecode_bioskop.service.RatingService;
import com.enigma.livecode_bioskop.service.TheaterService;
import com.enigma.livecode_bioskop.util.ApiUrlConstant;
import com.enigma.livecode_bioskop.util.MessageResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrlConstant.BASE_URL_FILM)
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private TheaterService theaterService;

    @Autowired
    private RatingService ratingService;
    
    @PostMapping
    public ResponseEntity<ResponseDTO<Film>> createFilm(@RequestBody @Valid FilmDTO filmDTO, Errors errors) {

        ResponseDTO<Film> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        Film film = new Film();
        film.setTittle(filmDTO.getTittle());
        film.setDuration(Duration.ofMinutes(filmDTO.getDuration()));
        film.setShowDate(filmDTO.getShowDate());
        film.setPrice(filmDTO.getPrice());
        film.setRating(ratingService.findRatingById(filmDTO.getRating()));
        film.setTheater(new ArrayList<>());
        
        for(Theater theaterId : filmDTO.getTheater()){
            film.getTheater().add(theaterService.findTheaterById(theaterId.getId()));
        }
        
        responseDTO.getMessage().add(MessageResult.CREATE_FILM_SUCCESS);
        responseDTO.setPayload(filmService.createFilm(film));
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<Film>> updateFilm(@RequestBody @Valid Film film, Errors errors) {

        ResponseDTO<Film> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        filmService.updateFilm(film);
        
        responseDTO.getMessage().add(MessageResult.UPDATE_FILM_SUCCESS);
        responseDTO.setPayload(film);
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public List<Film> getAllFilm(){
        return filmService.getAllFilm();
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> findFilmById(@PathVariable Integer id) {

        ResponseDTO<Film> responseDTO = new ResponseDTO<>();
        
        try {
            return ResponseEntity.ok().body(filmService.findFilmById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.FILM_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> deleteFilmById (@PathVariable Integer id) {

        ResponseDTO<Film> responseDTO = new ResponseDTO<>();

        try {
            if(filmService.findFilmById(id) == null){
                responseDTO.getMessage().add(MessageResult.FILM_NOT_FOUND);      
                responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
                responseDTO.setPayload(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
            filmService.deleteFilmByid(id);
            return ResponseEntity.ok().body(MessageResult.DELETE_FILM_SUCCESS);
            
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.FILM_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

}
