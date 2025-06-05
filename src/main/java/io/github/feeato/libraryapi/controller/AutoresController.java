package io.github.feeato.libraryapi.controller;

import io.github.feeato.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.feeato.libraryapi.model.dto.AutorDTO;
import io.github.feeato.libraryapi.model.dto.ErroResposta;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("autores")
public class AutoresController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseEntity<Object> cadastrarAutor(@RequestBody AutorDTO autorDTO) { //ResponseEntity é um objeto que representa todos os dados que dá pra retornar em uma resposta HTTP
        try {
            Autor autor = autorService.salvarAutor(autorDTO);

            URI location = ServletUriComponentsBuilder //isso aqui existe pra colocar um header no response com a localização do recurso criado
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException ex) {
            ErroResposta erroResposta = ErroResposta.conflito(ex.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        } catch (Exception ex) {
            ErroResposta erroResposta = ErroResposta.erroGenerico(ex.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> buscarAutor(@PathVariable String id) {
        Optional<AutorDTO> autor = autorService.buscarAutorDTO(id);

        return autor.map(a->ResponseEntity.ok().body(a)).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerAutor(@PathVariable String id) {
        Optional<AutorDTO> autor = autorService.removerAutor(id);

        if (autor.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<AutorDTO[]> buscarAutores(@RequestParam(required = false) String nome, @RequestParam(required = false) String nacionalidade) {
        return ResponseEntity.ok(autorService.buscarAutorComParametros(nome, nacionalidade));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarAutor(@PathVariable String id, @RequestBody AutorDTO autorDTO) {
        try {
            Optional<AutorDTO> autorAtualizadoDTO = autorService.atualizarAutor(id, autorDTO);
            if (autorAtualizadoDTO.isPresent()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RegistroDuplicadoException ex) {
            ErroResposta erroResposta = ErroResposta.conflito(ex.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        } catch (Exception ex) {
            ErroResposta erroResposta = ErroResposta.erroGenerico(ex.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

}
