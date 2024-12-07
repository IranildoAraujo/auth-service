package com.authservice.config;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.authservice.config.erro.Erro;
import com.authservice.config.erro.ErroResponse;
import com.authservice.exceptions.InvalidJwtAuthenticationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ERROR_HANDER_MESSAGE = "[ExceptionHandler] Sistema com erro";

	private static final String MSG_ERROR_SERVER = "O servidor apresentou erro. Entre em contato com adminstrador do sistema.";

	private static final String MSG_ERROR_UNAUTHORIZED = "Não Autorizado.";

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGeneralError(Exception ex, WebRequest request) {
		log.error(ERROR_HANDER_MESSAGE, ex);
		var erros = Arrays.asList(new Erro(HttpStatus.INTERNAL_SERVER_ERROR.value(), MSG_ERROR_SERVER));
		var bodyOfResponse = new ErroResponse(erros);
		return ResponseEntity.internalServerError().body(bodyOfResponse);
	}

	@ExceptionHandler(SignatureVerificationException.class)
	protected ResponseEntity<Object> handleSignatureVerificationException(SignatureVerificationException ex,
			HttpServletRequest request) {
		log.error(ERROR_HANDER_MESSAGE, ex);
		var erros = Arrays.asList(new Erro(HttpStatus.UNAUTHORIZED.value(), MSG_ERROR_UNAUTHORIZED));
		var bodyOfResponse = new ErroResponse(erros);
		return ResponseEntity.internalServerError().body(bodyOfResponse);
	}

	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	protected ResponseEntity<Object> handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException ex,
			WebRequest request) {
		log.error(ERROR_HANDER_MESSAGE, ex);
		var status = HttpStatus.UNAUTHORIZED;
		var erros = Arrays.asList(new Erro(status.value(), "Token inválido ou expirado."));
		var bodyOfResponse = new ErroResponse(erros);
		return ResponseEntity.status(status).body(bodyOfResponse);
	}

}
