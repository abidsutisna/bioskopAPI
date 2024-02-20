package com.enigma.livecode_bioskop.dto.request;

import java.sql.Date;
import java.util.List;

import com.enigma.livecode_bioskop.models.entity.Theater;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmDTO {

    private String tittle;

    private Long duration;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date showDate;

    private Integer price;

    private Integer rating;

    private List<Theater> theater;
}
