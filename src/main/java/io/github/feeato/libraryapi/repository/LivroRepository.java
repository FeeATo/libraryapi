package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.Autor;
import io.github.feeato.libraryapi.model.GeneroLivro;
import io.github.feeato.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query method -> o JPA procura e monta automático a implementação desse método
    List<Livro> findByAutor(Autor autor);

    @Query("SELECT l from Livro l WHERE l.genero=:genero")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro);

    @Query("SELECT l from Livro l WHERE l.genero=?1") //faz pela ordem dos parâmetros
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("DELETE FROM Livro l WHERE l.genero=?1")
    void deleteByGenero(@Param("genero") GeneroLivro generoLivro);

}
