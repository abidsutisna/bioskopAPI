package com.enigma.livecode_bioskop.service;

import java.util.List;

import com.enigma.livecode_bioskop.models.entity.Rating;

public interface RatingService {
    public Rating createRating(Rating rating);    
    List<Rating> getAllRating();
    Rating findRatingById(Integer id);
    void updaterating(Rating rating);
    void deleteRatingid(Integer id);
}
