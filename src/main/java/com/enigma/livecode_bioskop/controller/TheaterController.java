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

import com.enigma.livecode_bioskop.dto.request.TheaterDTO;
import com.enigma.livecode_bioskop.dto.response.ResponseDTO;
import com.enigma.livecode_bioskop.models.entity.Theater;
import com.enigma.livecode_bioskop.service.TheaterService;
import com.enigma.livecode_bioskop.util.ApiUrlConstant;
import com.enigma.livecode_bioskop.util.MessageResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrlConstant.BASE_URL_THEATER)
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Theater>> createTheater(@RequestBody @Valid TheaterDTO theaterDTO, Errors errors) {

        ResponseDTO<Theater> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        Theater theater = new Theater();
        theater.setTheaterNumber(theaterDTO.getTheaterNumber());
        theater.setStock(theaterDTO.getStock());
        theater.setSeat(theaterDTO.getSeat());
        
        responseDTO.getMessage().add(MessageResult.CREATE_THEATER_SUCCESS);
        responseDTO.setPayload(theaterService.createTheaterAndSeat(theater));
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<Theater>> updateTheater(@RequestBody @Valid Theater theater, Errors errors) {

        ResponseDTO<Theater> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        theaterService.updateTheater(theater);
        
        responseDTO.getMessage().add(MessageResult.UPDATE_THEATER_SUCCESS);
        responseDTO.setPayload(theater);
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public List<Theater> getAllTheater(){
        return theaterService.getAllTheater();
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> findTheaterById(@PathVariable Integer id) {

        ResponseDTO<Theater> responseDTO = new ResponseDTO<>();
        
        try {
            return ResponseEntity.ok().body(theaterService.findTheaterById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.THEATER_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> deleteTheaterById (@PathVariable Integer id) {

        ResponseDTO<Theater> responseDTO = new ResponseDTO<>();

        try {
            if(theaterService.findTheaterById(id) == null){
                responseDTO.getMessage().add(MessageResult.THEATER_NOT_FOUND);      
                responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
                responseDTO.setPayload(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
            theaterService.deleteTheaterByid(id);
            return ResponseEntity.ok().body(MessageResult.DELETE_THEATER_SUCCESS);
            
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.THEATER_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

}
