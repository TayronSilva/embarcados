package com.embarcados.api.features.trip.exceptions;

public class TripNotFoundExcpetion extends RuntimeException {
    public TripNotFoundExcpetion(String message) {
        super(message);
    }
}
