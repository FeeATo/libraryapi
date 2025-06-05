package io.github.feeato.libraryapi.model.dto;

import io.github.feeato.libraryapi.model.entity.Autor;

import java.time.LocalDate;
import java.util.UUID;

//record é uma classe simples sem nada de regras nem muitos detalhes
//essa classe é imutável, só tem getters
public record AutorDTO(UUID id,
                       String nome,
                       LocalDate dataNascimento,
                       String nacionalidade) {

    public Autor gerarAutor() {
        return new Autor(nome, nacionalidade, dataNascimento);
    }

}
