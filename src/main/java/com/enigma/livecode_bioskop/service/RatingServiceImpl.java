package com.enigma.livecode_bioskop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.livecode_bioskop.models.entity.Rating;
import com.enigma.livecode_bioskop.models.repos.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
       return this.ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
       return this.ratingRepository.findAll();
    }

    @Override
    public void updaterating(Rating rating) {
       this.ratingRepository.findById(rating.getId()).get();
       this.ratingRepository.save(rating);
    }

    @Override
    public void deleteRatingid(Integer id) {
       this.ratingRepository.deleteById(id);
    }

   @Override
   public Rating findRatingById(Integer id) {
      return this.ratingRepository.findById(id).get();
   }
    
}
