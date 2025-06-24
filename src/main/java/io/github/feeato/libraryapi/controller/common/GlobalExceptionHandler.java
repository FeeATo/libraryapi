package io.github.feeato.libraryapi.controller.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.github.feeato.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.feeato.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.feeato.libraryapi.exceptions.RegistroNaoEncontradoException;
import io.github.feeato.libraryapi.model.dto.ErroCampo;
import io.github.feeato.libraryapi.model.dto.ErroResposta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice //captura exceptions e dá uma resposta REST
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) //toda vez que a controller lançar esse erro (MethodArgumentValidException), vai cair aqui
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Erro de validação: {}", e.getMessage());
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampos = fieldErrors.stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()/*esse cara é a msg do erro da annotation*/))
                .toList();

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                erroCampos);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ErroResposta> handleRegistroDuplicadoException(RegistroDuplicadoException ex) {
        return montaResponseEntity(ErroResposta.conflito(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleException(Exception ex) {
        log.error("Erro inesperado: {}", ex.toString());
        return montaResponseEntity(ErroResposta.erroGenerico(ex.getMessage()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErroResposta> handleException(AuthorizationDeniedException ex) {
        return montaResponseEntity(new ErroResposta(HttpStatus.FORBIDDEN.value(), "Usuário não tem permissão para executar esta ação", null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException ex) {
        return montaResponseEntity(ErroResposta.conflito(ex.getMessage()));
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> handleRegistroNaoEncontradoException(RegistroNaoEncontradoException ex) {
        return montaResponseEntity(ErroResposta.naoEncontrado(ex.getMessage()));
    }

    private static ResponseEntity<ErroResposta> montaResponseEntity(ErroResposta erroResposta) {
        return ResponseEntity.status(erroResposta.status()).body(erroResposta);
    }

}
