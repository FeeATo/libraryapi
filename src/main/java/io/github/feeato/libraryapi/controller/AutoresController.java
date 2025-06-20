package io.github.feeato.libraryapi.controller;

import io.github.feeato.libraryapi.model.dto.AutorDTO;
import io.github.feeato.libraryapi.service.AutorService;
import io.github.feeato.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor //cria um construtor com todos os argumentos OBRIGATÒRIOS (todos que são 'final')
public class AutoresController implements GenericController {

    private final AutorService autorService;

    @PostMapping
    //o @Valid obriga que o objeto que chegue na controller seja validado com o spring validator
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> cadastrarAutor(@RequestBody @Valid AutorDTO autorDTO/*, Authentication authentication*/) { //ResponseEntity é um objeto que representa todos os dados que dá pra retornar em uma resposta HTTP
        AutorDTO autorDTOSalvo = autorService.salvarAutor(autorDTO/*, authentication*/);
        return ResponseEntity.created(gerarHeaderLocation(autorDTOSalvo.id())).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> buscarAutor(@PathVariable String id) {
        return autorService
                .buscarAutorDTO(id)
                .map(a -> ResponseEntity.ok().body(a))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> removerAutor(@PathVariable String id) {
        return autorService.removerAutor(id)
                .map(a -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<AutorDTO[]> buscarAutores(@RequestParam(required = false) String nome, @RequestParam(required = false) String nacionalidade) {
        return ResponseEntity.ok(autorService.buscarAutorComParametros(nome, nacionalidade));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> atualizarAutor(@PathVariable String id, @RequestBody @Valid AutorDTO autorDTO) {
//        try {
        return autorService.atualizarAutor(id, autorDTO)
                .map(autor -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
//        } catch (RegistroDuplicadoException ex) {
//            ErroResposta erroResposta = ErroResposta.conflito(ex.getMessage());
//            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
//        } catch (Exception ex) {
//            ErroResposta erroResposta = ErroResposta.erroGenerico(ex.getMessage());
//            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
//        }

        //NAO PRECISA DESSAS EXCEPTIONS PORQUE TEM O EXCEPTION HANDLER (CLASSE GlobalExceptionHandler)
    }

}
