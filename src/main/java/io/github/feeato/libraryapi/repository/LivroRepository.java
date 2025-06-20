package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.model.entity.GeneroLivro;
import io.github.feeato.libraryapi.model.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

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

    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);

    @Query("SELECT l FROM Livro l LEFT JOIN FETCH l.autor WHERE l.id=?1")
    Optional<Livro> findByIdWithAutor(UUID id);

}
