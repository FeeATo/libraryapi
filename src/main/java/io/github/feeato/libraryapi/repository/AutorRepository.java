package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    @Query("SELECT a from Autor a LEFT JOIN FETCH a.livros l")
    List<Autor> buscarAutoTodosComLivro();

    List<Autor> findAutorsByNome(String nome);

    List<Autor> findAutorsByNacionalidade(String nacionalidade);

    List<Autor> findAutorsByNomeAndNacionalidade(String nome, String nacionalidade);

}
