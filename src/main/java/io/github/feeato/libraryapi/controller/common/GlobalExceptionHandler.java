package io.github.feeato.libraryapi.controller.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.github.feeato.libraryapi.model.dto.ErroCampo;
import io.github.feeato.libraryapi.model.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice //captura exceptions e dá uma resposta REST
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) //toda vez que a controller lançar esse erro (MethodArgumentValidException), vai cair aqui
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampos = fieldErrors.stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()/*esse cara é a msg do erro da annotation*/))
                .toList();

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                erroCampos);
    }

}
