package com.enigma.livecode_bioskop.controller;

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

import com.enigma.livecode_bioskop.dto.request.RatingDTO;
import com.enigma.livecode_bioskop.dto.response.ResponseDTO;
import com.enigma.livecode_bioskop.models.entity.Rating;
import com.enigma.livecode_bioskop.service.RatingService;
import com.enigma.livecode_bioskop.util.ApiUrlConstant;
import com.enigma.livecode_bioskop.util.MessageResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrlConstant.BASE_URL_RATING)
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Rating>> createRating(@RequestBody @Valid RatingDTO ratingDTO, Errors errors) {

        ResponseDTO<Rating> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        Rating rating = new Rating();
        rating.setCode(ratingDTO.getCode());
        rating.setDescription(ratingDTO.getDescription());

        
        responseDTO.getMessage().add(MessageResult.CREATE_RATING_SUCCESS);
        responseDTO.setPayload(ratingService.createRating(rating));
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<Rating>> updateRating(@RequestBody @Valid Rating rating, Errors errors) {

        ResponseDTO<Rating> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        ratingService.updaterating(rating);
        
        responseDTO.getMessage().add(MessageResult.UPDATE_RATING_SUCCESS);
        responseDTO.setPayload(rating);
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public List<Rating> getAllRating(){
        return ratingService.getAllRating();
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> findRatingById(@PathVariable Integer id) {

        ResponseDTO<Rating> responseDTO = new ResponseDTO<>();
        
        try {
            return ResponseEntity.ok().body(ratingService.findRatingById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.RATING_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> deleteRatingById (@PathVariable Integer id) {

        ResponseDTO<Rating> responseDTO = new ResponseDTO<>();

        try {
            if(ratingService.findRatingById(id) == null){
                responseDTO.getMessage().add(MessageResult.RATING_NOT_FOUND);      
                responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
                responseDTO.setPayload(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
            ratingService.deleteRatingid(id);
            return ResponseEntity.ok().body(MessageResult.DELETE_RATING_SUCCESS);
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.RATING_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }


}
