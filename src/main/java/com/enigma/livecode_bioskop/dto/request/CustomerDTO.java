package com.enigma.livecode_bioskop.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
