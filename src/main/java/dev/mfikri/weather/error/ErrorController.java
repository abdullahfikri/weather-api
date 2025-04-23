package dev.mfikri.weather.error;

import dev.mfikri.weather.model.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Set;

@ControllerAdvice(annotations = RestController.class)
public class ErrorController  {

    @ExceptionHandler(BadRequest.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> badRequest(BadRequest e) {
        ErrorResponse response = new ErrorResponse(e.getResponseBodyAsString());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Unauthorized.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> unauthorize() {

        ErrorResponse response = new ErrorResponse("API key is wrong");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFound.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> notFound() {

        ErrorResponse response = new ErrorResponse("URL API NOT FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TooManyRequests.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<ErrorResponse> tooManyRequest() {

        ErrorResponse response = new ErrorResponse("Too Many request, Try again later");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> constraintViolation(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            if (!message.isEmpty()){
                message.append(", ");
            }
            String[] split = constraintViolation.getPropertyPath().toString().split("\\.");
            String argument = split[split.length-1];
            message.append(argument);
            message.append(": ");
            message.append(constraintViolation.getMessage());
        }
        ErrorResponse response = new ErrorResponse(message.toString());
        return ResponseEntity.badRequest().body(response);
    }



}
