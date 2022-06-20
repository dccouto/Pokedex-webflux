package com.couto.pokedex.exceptions;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-2) // será executado antes do DefaultAbstractExceptionHandler do spring que tem a
			// prioridade -1
public class GlobalExceptionHandlers extends AbstractErrorWebExceptionHandler {

	public GlobalExceptionHandlers(ErrorAttributes errorAttributes, Resources resources,
			ApplicationContext applicationContext, ServerCodecConfigurer codecConfigurer) {
		super(errorAttributes, resources, applicationContext);
		this.setMessageWriters(codecConfigurer.getWriters());
	}


	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::sendResponseExceptionRequest);
	}

	/**
	 * Monta o objeto com as informações da Exception
	 * 
	 * @return {@link HttpStatus}
	 */
	private Mono<ServerResponse> sendResponseExceptionRequest(ServerRequest request) {

		Map<String, Object> errorAttributesMap = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());

		int status = (int) Optional.ofNullable(errorAttributesMap.get("status")).orElse(500);

		return ServerResponse.status(status).body(BodyInserters.fromValue(errorAttributesMap));

	}
}
