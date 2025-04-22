package dev.mfikri.weather.error;

import dev.mfikri.weather.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.*;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice(annotations = RestController.class)
public class ErrorController  {

    @ExceptionHandler(BadRequest.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> badRequest(BadRequest e) {
        ErrorResponse response = new ErrorResponse(e.getResponseBodyAsString());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Unauthorized.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> unauthorize() {

        ErrorResponse response = new ErrorResponse("API key is wrong");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFound.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> notFound() {

        ErrorResponse response = new ErrorResponse("URL API NOT FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TooManyRequests.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> tooManyRequest() {

        ErrorResponse response = new ErrorResponse("Too Many request, Try again later");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> internalServerError() {

        ErrorResponse response = new ErrorResponse("Internal Server Error, Try again later");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> errorParameter(MissingServletRequestParameterException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }



}
