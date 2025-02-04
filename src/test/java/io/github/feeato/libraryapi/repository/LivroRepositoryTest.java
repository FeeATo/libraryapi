package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.Autor;
import io.github.feeato.libraryapi.model.GeneroLivro;
import io.github.feeato.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void buscarTodos() {
        livroRepository.findAll().forEach(System.out::println);
    }

}