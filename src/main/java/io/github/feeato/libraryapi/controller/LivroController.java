package io.github.feeato.libraryapi.controller;

import io.github.feeato.libraryapi.model.dto.LivroDTO;
import io.github.feeato.libraryapi.model.entity.GeneroLivro;
import io.github.feeato.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor //cria um construtor com todos os argumentos OBRIGATÒRIOS (todos que são 'final')
@PreAuthorize("hasAnyRole('OPERADOR','GERENTE')") //isso faz com que somente os usuários com essa role possam utilizam esse endpoint
public class LivroController implements GenericController {

    private final LivroService livroService;

    @PostMapping
//    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')") //dá pra colocar esse cara aqui nos métodos pra só permitir os usuários com essas roles
    public ResponseEntity<Void> salvarLivro(@RequestBody @Valid LivroDTO livroDTO) {
        LivroDTO livroDTOSalvo = livroService.salvarLivro(livroDTO);

        return ResponseEntity.created(gerarHeaderLocation(livroDTOSalvo.id())).build();
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> buscarLivro(@PathVariable String id) {
        Optional<LivroDTO> livroDTO = livroService.buscarLivroDTOPorIdComAutor(id);
        return livroDTO.<ResponseEntity<Object>>map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerLivro(@PathVariable String id) {
        livroService.removerLivro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<LivroDTO>> pesquisar(@RequestParam(required = false) String isbn,
                                                       @RequestParam(required = false) String titulo,
                                                       @RequestParam(name = "nome-autor", required = false) String nomeAutor,
                                                       @RequestParam(name = "genero", required = false) GeneroLivro generoLivro,
                                                       @RequestParam(name = "ano-publicacao", required = false) Integer anoPublicacao,
                                                       @RequestParam(defaultValue = "0") Integer pagina,
                                                       @RequestParam(name = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina) {
        return ResponseEntity.ok(livroService.pesquisar(isbn, titulo, nomeAutor, generoLivro, anoPublicacao, pagina, tamanhoPagina));
    }


}
