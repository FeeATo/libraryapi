package io.github.feeato.libraryapi.controller;

import io.github.feeato.libraryapi.model.dto.UsuarioDTO;
import io.github.feeato.libraryapi.model.dto.mapper.UsuarioMapper;
import io.github.feeato.libraryapi.model.entity.Usuario;
import io.github.feeato.libraryapi.repository.UsuarioRepository;
import io.github.feeato.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        Usuario entity = usuarioMapper.toEntity(dto);
        usuarioService.salvar(entity);
    }

}
