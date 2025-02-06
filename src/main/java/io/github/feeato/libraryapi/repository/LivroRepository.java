package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.Autor;
import io.github.feeato.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query method -> o JPA procura e monta automático a implementação desse método
    List<Livro> findByAutor(Autor autor);

}
