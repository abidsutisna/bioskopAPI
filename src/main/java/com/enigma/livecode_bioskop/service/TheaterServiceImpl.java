package com.enigma.livecode_bioskop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.livecode_bioskop.models.entity.Seat;
import com.enigma.livecode_bioskop.models.entity.Theater;
import com.enigma.livecode_bioskop.models.repos.TheaterRepository;

import jakarta.transaction.Transactional;

@Service
public class TheaterServiceImpl implements TheaterService{

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private SeatService seatService;

    @Override
    public Theater createTheater(Theater theater) {
        return this.theaterRepository.save(theater);
    }

    @Override
    public Theater findTheaterById(Integer id) {
       return this.theaterRepository.findById(id).get();
    }

    @Override
    public List<Theater> getAllTheater() {
        return this.theaterRepository.findAll();
    }

    @Override
    public void updateTheater(Theater theater) {
        this.theaterRepository.findById(theater.getId()).get();
    }

    @Override
    public void deleteTheaterByid(Integer id) {
        this.theaterRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Theater createTheaterAndSeat(Theater theater) {

        Theater theater1 = this.theaterRepository.save(theater);
        for(Seat seat : theater.getSeat()) {
            seat.setTheater(theater1);
            this.seatService.createSeat(seat);
        }

        return theater1;
    }
    
}
