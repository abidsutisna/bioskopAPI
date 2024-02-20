package com.enigma.livecode_bioskop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {

    private Integer seat;

    private Integer customer;  

    private Integer film;
}
