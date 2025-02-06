package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    @Query("SELECT a from Autor a LEFT JOIN FETCH a.livros l")
    List<Autor> buscarAutoTodosComLivro();

}
