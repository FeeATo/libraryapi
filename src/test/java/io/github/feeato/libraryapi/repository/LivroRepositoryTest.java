package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.model.entity.GeneroLivro;
import io.github.feeato.libraryapi.model.entity.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Test
    void salvarTeste() {
        Livro livro = new Livro("566161561",
                "Titulo livro 1",
                LocalDate.of(2024, 11, 14),
                GeneroLivro.FICCAO,
                BigDecimal.valueOf(1000.50),
                new Autor("autor que não está no banco", "brasileiro", LocalDate.of(1999, 01, 31)));
        livroRepository.save(livro);
    }

    @Test
    void salvarTesteComIdAutorValido() {
        Livro livro = new Livro("566161561",
                "Livro com chave extrangeira",
                LocalDate.of(2024, 11, 14),
                GeneroLivro.FICCAO,
                BigDecimal.valueOf(1000.50),
                new Autor().setId(UUID.fromString("dc7e9f66-36dd-4b17-b7b9-187fd257c8a2"))); //ISSO FUNCIONA!!!
        livroRepository.save(livro);
    }

    @Test
    @Transactional //ele busca todos as propriedades lazys dos objetos (jeito meio bosta)
    void buscarTodos() {
        livroRepository.findAll().forEach(l->{
            System.out.println("Livro: " + l.getTitulo());
            System.out.println("Autor: " + l.getAutor().getNome());
        });
    }

    @Test
    void buscarTodosComLivros() {
        livroRepository.findAll().forEach(System.out::println);
    }

    @Test
    void buscarPorGenero() {
        livroRepository.findByGenero(GeneroLivro.FICCAO).forEach(System.out::println);
    }

    @Test
    void deleteByGenero() {livroRepository.deleteByGenero(GeneroLivro.FICCAO);}

}