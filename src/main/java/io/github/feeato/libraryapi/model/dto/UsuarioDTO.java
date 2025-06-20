package io.github.feeato.libraryapi.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO (@NotBlank(message = "campo obrigatório") String login,
                          @NotBlank(message = "campo obrigatório") String senha,
                          @Email(message = "email inválido") String email,
                          List<String> roles) {
}
