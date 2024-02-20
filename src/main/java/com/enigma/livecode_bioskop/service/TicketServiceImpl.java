package com.enigma.livecode_bioskop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.livecode_bioskop.models.entity.Ticket;
import com.enigma.livecode_bioskop.models.repos.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket createTicket(Ticket ticket) {
       return this.ticketRepository.save(ticket);
    }

    @Override
    public Ticket findTickerById(Integer id) {
        return this.ticketRepository.findById(id).get();
    }

    @Override
    public List<Ticket> getAllTicket() {
        return this.ticketRepository.findAll();
    }

    @Override
    public void updateTicket(Ticket ticket) {
        this.ticketRepository.findById(ticket.getId()).get();
        this.ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketByid(Integer id) {
       this.ticketRepository.deleteById(id);
    }
    
}
