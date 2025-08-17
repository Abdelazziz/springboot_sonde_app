package dz.itbridge.hospital.exceptions;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dz.itbridge.hospital.utils.MessageUtils;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlers {

        private ErrorResponse buildError(String message, HttpStatus status, String path) {
                return new ErrorResponse(message, status.value(), status.getReasonPhrase(), path);
        }

        // Authentication failure
        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex,
                        HttpServletRequest request) {
                return new ResponseEntity<>(
                                buildError(MessageUtils.ERROR_INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED,
                                                request.getRequestURI()),
                                HttpStatus.UNAUTHORIZED);
        }

        // Validation errors
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                String message = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                .collect(Collectors.joining(", "));

                return new ResponseEntity<>(
                                buildError(message, HttpStatus.BAD_REQUEST, request.getRequestURI()),
                                HttpStatus.BAD_REQUEST);
        }

        // Handle Optional.empty().get()
        @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<ErrorResponse> handleNoSuchElement(NoSuchElementException ex,
                        HttpServletRequest request) {
                return new ResponseEntity<>(
                                buildError(MessageUtils.ERROR_RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND,
                                                request.getRequestURI()),
                                HttpStatus.NOT_FOUND);
        }

        // Handle JPA delete where entity not found
        @ExceptionHandler(EmptyResultDataAccessException.class)
        public ResponseEntity<ErrorResponse> handleEmptyResult(EmptyResultDataAccessException ex,
                        HttpServletRequest request) {
                return new ResponseEntity<>(
                                buildError(MessageUtils.NO_DATA_FOR_ID, HttpStatus.NOT_FOUND,
                                                request.getRequestURI()),
                                HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex,
                        HttpServletRequest request) {
                return new ResponseEntity<>(
                                buildError(MessageUtils.HTTP_METHOD_NOT_ALLOWED + ex.getMethod(),
                                                HttpStatus.METHOD_NOT_ALLOWED,
                                                request.getRequestURI()),
                                HttpStatus.METHOD_NOT_ALLOWED);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex,
                        HttpServletRequest request) {
                return new ResponseEntity<>(
                                buildError(MessageUtils.INVALID_REQUEST_BODY, HttpStatus.BAD_REQUEST,
                                                request.getRequestURI()),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(TokenExpiredException.class)
        public ResponseEntity<ErrorResponse> handleRefreshTokenExpired(
                        TokenExpiredException ex,
                        HttpServletRequest request) {
                return new ResponseEntity<>(
                                buildError(MessageUtils.INVALID_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED,
                                                request.getRequestURI()),
                                HttpStatus.UNAUTHORIZED);
        }

        // Data base conflit operation
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                        HttpServletRequest request) {
                String rootCauseMessage = Optional.of(ex.getMessage()).orElse(MessageUtils.DATA_BASE_ERROR);
                String errorMassage;

                if (rootCauseMessage != null && rootCauseMessage.contains("duplicate key")) {
                        errorMassage = "A unique value already exists.";
                } else {
                        errorMassage = "Data integrity violation.";
                }

                return new ResponseEntity<>(
                                buildError(errorMassage, HttpStatus.CONFLICT,
                                                request.getRequestURI()),
                                HttpStatus.CONFLICT);
        }

        // Catch-all
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {

                return new ResponseEntity<>(
                                new ErrorResponse(
                                                MessageUtils.INTERNAL_ERROR, 500,
                                                ex.getMessage(),
                                                request.getRequestURI()),
                                HttpStatus.INTERNAL_SERVER_ERROR);

        }
}
