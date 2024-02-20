package com.enigma.livecode_bioskop.service;

import java.util.List;

import com.enigma.livecode_bioskop.models.entity.Seat;


public interface SeatService {
    public Seat createSeat(Seat seat);    
    List<Seat> getAllSeat();
    Seat findSeatById(Integer id);
    void updateSeat(Seat seat);
    void deleteSeatByid(Integer id);
}
