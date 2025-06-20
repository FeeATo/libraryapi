package io.github.feeato.libraryapi.model.dto.mapper;

import io.github.feeato.libraryapi.model.dto.LivroDTO;
import io.github.feeato.libraryapi.model.entity.Livro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    Livro toEntity(LivroDTO livroDTO);

    LivroDTO toDTO(Livro autor);
}
