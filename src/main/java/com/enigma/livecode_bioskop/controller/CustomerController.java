package com.enigma.livecode_bioskop.controller;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
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

import com.enigma.livecode_bioskop.dto.request.CustomerDTO;
import com.enigma.livecode_bioskop.dto.request.TicketDTO;
import com.enigma.livecode_bioskop.dto.response.ResponseDTO;
import com.enigma.livecode_bioskop.models.entity.Customer;
import com.enigma.livecode_bioskop.models.entity.Theater;
import com.enigma.livecode_bioskop.models.entity.Ticket;
import com.enigma.livecode_bioskop.service.CustomerService;
import com.enigma.livecode_bioskop.service.FilmService;
import com.enigma.livecode_bioskop.service.SeatService;
import com.enigma.livecode_bioskop.service.TicketService;
import com.enigma.livecode_bioskop.util.ApiUrlConstant;
import com.enigma.livecode_bioskop.util.MessageResult;
import com.enigma.livecode_bioskop.util.RatingCodeEnum;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrlConstant.BASE_URL_CUSTOMER)
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private FilmService filmService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Customer>> createCustomer(@RequestBody @Valid CustomerDTO customerDTO, Errors errors) {

        ResponseDTO<Customer> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setBirthDate(customerDTO.getBirthDate());
        
        responseDTO.getMessage().add(MessageResult.CREATE_CUSTOMER_SUCCESS);
        responseDTO.setPayload(customerService.createCustomer(customer));
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @PutMapping
    public ResponseEntity<ResponseDTO<Customer>> updateCustomer(@RequestBody @Valid Customer customer, Errors errors) {

        ResponseDTO<Customer> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        customerService.updateCustomer(customer);
        
        responseDTO.getMessage().add(MessageResult.UPDATE_CUSTOMER_SUCCESS);
        responseDTO.setPayload(customer);
        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @GetMapping
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> findCustomerById(@PathVariable Integer id) {

        ResponseDTO<Customer> responseDTO = new ResponseDTO<>();
        
        try {
            return ResponseEntity.ok().body(customerService.findCustomerById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.CUSTOMER_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> deleteCustomerById (@PathVariable Integer id) {

        ResponseDTO<Customer> responseDTO = new ResponseDTO<>();

        try {
            if(customerService.findCustomerById(id) == null){
                responseDTO.getMessage().add(MessageResult.CUSTOMER_NOT_FOUND);      
                responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
                responseDTO.setPayload(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
            customerService.deleteCustomerByid(id);
            return ResponseEntity.ok().body(MessageResult.DELETE_CUSTOMER_SUCCESS);
            
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResult.CUSTOMER_NOT_FOUND);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    @PostMapping(ApiUrlConstant.BUY_TICKET)
    public ResponseEntity<ResponseDTO<Ticket>> buyTicket(@RequestBody @Valid TicketDTO ticketDTO, Errors errors) {

        ResponseDTO<Ticket> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        Customer customer = customerService.findCustomerById(ticketDTO.getCustomer());
        Period agePeriod = Period.between(customer.getBirthDate(), LocalDate.now());
        Integer age = agePeriod.getYears();

        RatingCodeEnum ratingCode = filmService.findFilmById(ticketDTO.getFilm()).getRating().getCode();
        Integer stock = seatService.findSeatById(ticketDTO.getSeat()).getTheater().getStock();

        if(ratingCode == RatingCodeEnum.BO){ 
            if(age < 13){
                responseDTO.getMessage().add(MessageResult.UNDER_AGE);
                responseDTO.setPayload(null);
                responseDTO.setStatus(HttpStatus.FORBIDDEN.getReasonPhrase());

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
            } 
        } else if(ratingCode == RatingCodeEnum.R){
            if(age < 18){
                responseDTO.getMessage().add(MessageResult.UNDER_AGE);
                responseDTO.setPayload(null);
                responseDTO.setStatus(HttpStatus.FORBIDDEN.getReasonPhrase());

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
            } 
        } else if(ratingCode == RatingCodeEnum.D){
            if(age < 21){
                responseDTO.getMessage().add(MessageResult.UNDER_AGE);
                responseDTO.setPayload(null);
                responseDTO.setStatus(HttpStatus.FORBIDDEN.getReasonPhrase());

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
            } 
        } else if(stock == 0){
            responseDTO.getMessage().add(MessageResult.SOLD_OUT);
            responseDTO.setPayload(null);
            responseDTO.setStatus(HttpStatus.FORBIDDEN.getReasonPhrase());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }

            Theater theater = seatService.findSeatById(ticketDTO.getSeat()).getTheater();
            theater.setStock(stock -1);

            Ticket ticket = new Ticket();
            ticket.setCustomer(customerService.findCustomerById(ticketDTO.getCustomer()));
            ticket.setSeat(seatService.findSeatById(ticketDTO.getSeat()));
            ticket.setFilm(filmService.findFilmById(ticketDTO.getFilm()));
            
            responseDTO.getMessage().add(MessageResult.CREATE_TICKET_SUCCESS);
            responseDTO.setPayload(ticketService.createTicket(ticket));
            responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    
    }

}
