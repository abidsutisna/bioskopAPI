package com.enigma.livecode_bioskop.service;

import java.util.List;

import com.enigma.livecode_bioskop.models.entity.Theater;

public interface TheaterService {
    public Theater createTheater(Theater theater);    
    Theater findTheaterById(Integer id);
    List<Theater> getAllTheater();
    void updateTheater(Theater theater);
    void deleteTheaterByid(Integer id);
    Theater createTheaterAndSeat(Theater theater);
}
