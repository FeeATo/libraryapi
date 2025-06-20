package io.github.feeato.libraryapi.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {

    default URI gerarHeaderLocation(UUID id) {
        return ServletUriComponentsBuilder //isso aqui existe pra colocar um header no response com a localização do recurso criado
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
