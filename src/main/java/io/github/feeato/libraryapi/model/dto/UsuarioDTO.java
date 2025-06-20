package io.github.feeato.libraryapi.model.dto;

import java.util.List;

public record UsuarioDTO (String login, String senha, List<String> roles) {
}
