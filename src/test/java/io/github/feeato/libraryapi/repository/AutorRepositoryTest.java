package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.Autor;
import io.github.feeato.libraryapi.model.GeneroLivro;
import io.github.feeato.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTeste() {
        Autor autor = new Autor("Maria", "Brasileira", LocalDate.of(1951, 1, 31));
        autor.setLivros(List.of(new Livro("192310293", "livro criado ao salvar Autor", LocalDate.now(), GeneroLivro.FICCAO, BigDecimal.TEN, autor)));
        autor = autorRepository.save(autor);
        System.out.println("Autor salvo: " + autor);
    }

    @Test
    public void atualizarTeste() {
        UUID uuid = UUID.fromString("109adeb2-7a17-431f-8c4d-9dec90b806ef");

        Optional<Autor> autor = autorRepository.findById(uuid);
        if (autor.isPresent()) {
            Autor autorEncontrado = autor.get();
            System.out.println("Dados autor: " + autorEncontrado);

            autorEncontrado.setNome("NOME ALTERADO");
            autorRepository.save(autorEncontrado);
        } else {
            throw new RuntimeException("Autor não encontado");
        }
    }

    @Test
    public void listarTodos() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void listarTodosComLivros() {
        List<Autor> autores = autorRepository.buscarAutoTodosComLivro();
        autores.forEach(a-> System.out.println(a.toStringCompleto()));
    }


    @Test
    public void deleteAlgum() {
        Optional<Autor> algumAutor = autorRepository.findAll().stream().findAny();
        if (algumAutor.isPresent()) {
            autorRepository.delete(algumAutor.get());
        } else {
            throw new RuntimeException("Nenhum autor presente");
        }
    }

}
