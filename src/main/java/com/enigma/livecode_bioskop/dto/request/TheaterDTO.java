package com.enigma.livecode_bioskop.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.enigma.livecode_bioskop.models.entity.Seat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterDTO {

    private String theaterNumber;

    private Integer stock;

    private List<Seat> seat = new ArrayList<>();
}
