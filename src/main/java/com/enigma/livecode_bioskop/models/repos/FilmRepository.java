package com.enigma.livecode_bioskop.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.livecode_bioskop.models.entity.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    
} 
