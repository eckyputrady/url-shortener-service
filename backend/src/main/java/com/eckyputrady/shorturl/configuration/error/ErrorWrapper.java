package com.eckyputrady.shorturl.configuration.error;

import lombok.*;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by ecky on 08/06/16.
 */
@Data
@AllArgsConstructor
public class ErrorWrapper {
    private String message;
    private Collection<Message> errors;

    @Data
    @AllArgsConstructor
    public static class Message {
        private String location;
        private String message;
    }

    public static ErrorWrapper from(Collection<FieldError> fieldErrors) {
        return new ErrorWrapper(
                "Invalid parameter",
                fieldErrors.stream().map(x ->
                        new Message(x.getField(), x.isBindingFailure() ? "Invalid value" : x.getDefaultMessage())
                ).collect(Collectors.toList())
        );
    }
}
