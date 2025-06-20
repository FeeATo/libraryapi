package io.github.feeato.libraryapi.model.dto.mapper;

import io.github.feeato.libraryapi.model.dto.UsuarioDTO;
import io.github.feeato.libraryapi.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDto(Usuario entity);
}
