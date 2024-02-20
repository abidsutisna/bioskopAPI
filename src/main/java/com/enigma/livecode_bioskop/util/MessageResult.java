package com.enigma.livecode_bioskop.util;

public interface MessageResult {
    String CREATE_CUSTOMER_SUCCESS = "Create customer success";
    String UPDATE_CUSTOMER_SUCCESS = "Update customer success";
    String DELETE_CUSTOMER_SUCCESS = "Delete customer success";
    String CUSTOMER_NOT_FOUND = "Customer not found";

    String CREATE_FILM_SUCCESS = "Create film success";
    String UPDATE_FILM_SUCCESS = "update film success";
    String DELETE_FILM_SUCCESS = "Delete film success";
    String FILM_NOT_FOUND = "Film not found";

    String CREATE_THEATER_SUCCESS = "Create theater success";
    String UPDATE_THEATER_SUCCESS = "update theater success";
    String DELETE_THEATER_SUCCESS = "Delete theater success";
    String THEATER_NOT_FOUND = "Theater not found";

    String CREATE_SEAT_SUCCESS = "Create seat success";
    String UPDATE_SEAT_SUCCESS = "update seat success";
    String DELETE_SEAT_SUCCESS = "Delete seat success";
    String SEAT_NOT_FOUND = "Theater not found";

    String CREATE_RATING_SUCCESS = "Create rating success";
    String UPDATE_RATING_SUCCESS = "update rating success";
    String DELETE_RATING_SUCCESS = "Delete rating success";
    String RATING_NOT_FOUND = "Rating not found";

    String CREATE_TICKET_SUCCESS = "Create ticket success";
    String UNDER_AGE = "belum cukup umur";
    String SOLD_OUT = "sold out";
}
