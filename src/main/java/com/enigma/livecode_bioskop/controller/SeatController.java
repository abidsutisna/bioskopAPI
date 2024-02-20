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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.livecode_bioskop.dto.request.SeatDTO;
import com.enigma.livecode_bioskop.dto.request.TheaterDTO;
import com.enigma.livecode_bioskop.dto.response.ResponseDTO;
import com.enigma.livecode_bioskop.models.entity.Seat;
import com.enigma.livecode_bioskop.models.entity.Theater;
import com.enigma.livecode_bioskop.models.entity.Seat;
import com.enigma.livecode_bioskop.service.SeatService;
import com.enigma.livecode_bioskop.util.ApiUrlConstant;
import com.enigma.livecode_bioskop.util.MessageResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrlConstant.BASE_URL_SEAT)
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Seat>> updateSeat(@RequestBody @Valid Seat seat, Errors errors) {

        ResponseDTO<Seat> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        seatService.updateSeat(seat);
        
        responseDTO.getMessage().add(MessageResult.CREATE_SEAT_SUCCESS);
        responseDTO.setPayload(seat);
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public List<Seat> getAllSeat(){
        return seatService.getAllSeat();
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> findSeatById(@PathVariable Integer id) {

        ResponseDTO<Seat> responseDTO = new ResponseDTO<>();
        
        try {
            return ResponseEntity.ok().body(seatService.findSeatById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.SEAT_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> deleteSeatById (@PathVariable Integer id) {

        ResponseDTO<Seat> responseDTO = new ResponseDTO<>();

        try {
            if(seatService.findSeatById(id) == null){
                responseDTO.getMessage().add(MessageResult.SEAT_NOT_FOUND);      
                responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
                responseDTO.setPayload(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
            seatService.deleteSeatByid(id);
            return ResponseEntity.ok().body(MessageResult.DELETE_SEAT_SUCCESS);
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.SEAT_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }


}
