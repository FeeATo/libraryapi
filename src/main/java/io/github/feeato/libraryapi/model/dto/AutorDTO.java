package io.github.feeato.libraryapi.model.dto;

import io.github.feeato.libraryapi.model.entity.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

//record é uma classe simples sem nada de regras nem muitos detalhes
//essa classe é imutável, só tem getters
public record AutorDTO(UUID id,
                       @NotBlank(message = "campo obrigatório") @Size(min = 1, max = 100, message = "Campo fora do tamanho máximo ou mínimo permitido") String nome,
                       @NotNull(message = "campo obrigatório") @Past(message = "Data de nascimento não pode ser futura") LocalDate dataNascimento,
                       @NotBlank(message = "campo obrigatório") @Size(min = 4, max = 50, message = "Campo fora do tamanho máximo ou mínimo permitido") String nacionalidade) {

}
