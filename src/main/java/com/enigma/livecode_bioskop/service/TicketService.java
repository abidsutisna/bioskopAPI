package com.enigma.livecode_bioskop.service;

import java.util.List;

import com.enigma.livecode_bioskop.models.entity.Ticket;

public interface TicketService {
    public Ticket createTicket(Ticket ticket);    
    Ticket findTickerById(Integer id);
    List<Ticket> getAllTicket();
    void updateTicket(Ticket ticket);
    void deleteTicketByid(Integer id);
} 
