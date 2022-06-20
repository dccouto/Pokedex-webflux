package com.couto.pokedex.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class PokedexException extends ResponseStatusException {


	public PokedexException(HttpStatus httpStatus, String message) {
		super(httpStatus, message);
	}
}
