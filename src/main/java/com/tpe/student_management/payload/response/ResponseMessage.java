package com.tpe.student_management.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//JsonInclude, response'ta neler olmasini istedigimizi belirtmek icin kullanilir.
//NON_NULL -> Response body, null olmayan fieldlari icerir.

public class ResponseMessage<E> {

    private E object;
    private String message;
    private HttpStatus httpStatus;




}
