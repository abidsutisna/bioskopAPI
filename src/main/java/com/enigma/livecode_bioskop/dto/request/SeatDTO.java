package com.enigma.livecode_bioskop.dto.request;

import com.enigma.livecode_bioskop.models.entity.Theater;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDTO {

    private String seatNumber;

    private Theater theater;
}
