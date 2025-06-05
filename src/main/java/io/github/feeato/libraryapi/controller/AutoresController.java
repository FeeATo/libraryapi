package io.github.feeato.libraryapi.controller;

import io.github.feeato.libraryapi.model.dto.AutorDTO;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.service.AutorService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutoresController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseEntity<Void> cadastrarAutor(@RequestBody AutorDTO autorDTO) { //ResponseEntity é um objeto que representa todos os dados que dá pra retornar em uma resposta HTTP
        Autor autor = autorService.salvarAutor(autorDTO);

        URI location = ServletUriComponentsBuilder //isso aqui existe pra colocar um header no response com a localização do recurso criado
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId()).toUri();
        return ResponseEntity.created(location).build();
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
    public ResponseEntity<Void> atualizarAutor(@PathVariable String id, @RequestBody AutorDTO autorDTO) {
        Optional<AutorDTO> autorAtualizadoDTO = autorService.atualizarAutor(id, autorDTO);
        if (autorAtualizadoDTO.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
