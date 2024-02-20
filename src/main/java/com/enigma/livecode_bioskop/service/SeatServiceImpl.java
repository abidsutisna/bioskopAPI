package com.enigma.livecode_bioskop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.livecode_bioskop.models.entity.Seat;
import com.enigma.livecode_bioskop.models.repos.SeatRepository;

@Service
public class SeatServiceImpl implements SeatService{

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public Seat createSeat(Seat seat) {
       return this.seatRepository.save(seat);
    }

    @Override
    public List<Seat> getAllSeat() {
        return this.seatRepository.findAll();
    }

    @Override
    public void updateSeat(Seat seat) {
        this.seatRepository.findById(seat.getId()).get();
        this.seatRepository.save(seat);
    }

    @Override
    public void deleteSeatByid(Integer id) {
        this.seatRepository.deleteById(id);
    }

    @Override
    public Seat findSeatById(Integer id) {
        return this.seatRepository.findById(id).get();
    }
    
}
