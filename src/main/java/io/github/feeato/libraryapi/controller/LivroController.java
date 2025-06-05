package io.github.feeato.libraryapi.controller;

import io.github.feeato.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.feeato.libraryapi.exceptions.RegistroNaoEncontradoException;
import io.github.feeato.libraryapi.model.dto.ErroResposta;
import io.github.feeato.libraryapi.model.dto.LivroDTO;
import io.github.feeato.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor //cria um construtor com todos os argumentos OBRIGATÒRIOS (todos que são 'final')
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvarLivro(@RequestBody @Valid LivroDTO livroDTO) {
        try {
            LivroDTO livroDTOSalvo = livroService.salvarLivro(livroDTO);

            URI location = ServletUriComponentsBuilder //isso aqui existe pra colocar um header no response com a localização do recurso criado
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(livroDTOSalvo.id()).toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroNaoEncontradoException ex) {
            return montaResponseRegistroNaoEncontrado(ErroResposta.naoEncontrado(ex.getMessage()));
        } catch (RegistroDuplicadoException ex) {
            return montaResponseRegistroNaoEncontrado(ErroResposta.conflito(ex.getMessage()));
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> buscarLivro(@PathVariable String id) {
        Optional<LivroDTO> livroDTO = livroService.buscarLivroDTOPorIdComAutor(id);
        return livroDTO.<ResponseEntity<Object>>map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removerLivro(@PathVariable String id) {
        try {
            livroService.removerLivro(id);
            return ResponseEntity.noContent().build();
        } catch (RegistroNaoEncontradoException ex) {
            return montaResponseRegistroNaoEncontrado(ErroResposta.naoEncontrado(ex.getMessage()));
        }
    }

    private static ResponseEntity<Object> montaResponseRegistroNaoEncontrado(ErroResposta ex) {
        ErroResposta erroResposta = ex;
        return ResponseEntity.status(erroResposta.status()).body(erroResposta);
    }

}
