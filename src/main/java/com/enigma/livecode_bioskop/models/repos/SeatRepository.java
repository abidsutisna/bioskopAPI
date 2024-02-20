package com.enigma.livecode_bioskop.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.livecode_bioskop.models.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    
}
