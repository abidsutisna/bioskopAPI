package com.enigma.livecode_bioskop.dto.request;

import com.enigma.livecode_bioskop.util.RatingCodeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
    
    private RatingCodeEnum code;

    private String description;
}
