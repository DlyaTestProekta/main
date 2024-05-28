package ru.pachan.main.exception.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

//public record RequestException(
//        String message,
//        HttpStatus httpStatus,
//
//        @JsonIgnore
//        List<Exception>stackTrace,
//
//        @JsonIgnore
//        List<Exception> suppressed,
//
//        @JsonIgnore
//        Exception localizedMessage,
//
//        @JsonIgnore
//        Exception cause
//)  {
//}

@Setter
@Getter
@AllArgsConstructor
public class RequestException extends Exception {
    private final String message;
    private final HttpStatus httpStatus;

//    @JsonIgnore
//    private List<Exception> stackTrace;
//
//    @JsonIgnore
//    private List<Exception> suppressed;
//
//    @JsonIgnore
//    private Exception localizedMessage;
//
//    @JsonIgnore
//    private Exception cause;
}
