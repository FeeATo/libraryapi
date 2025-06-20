package io.github.feeato.libraryapi.model.dto.mapper;

import io.github.feeato.libraryapi.model.dto.AutorDTO;
import io.github.feeato.libraryapi.model.entity.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper { //esse cara aqui vai jogar os valores do this no objeto parametro nos campos que tem o mesmo nome

    @Mapping(source = "nome", target = "nome") //se tivessem nomes diferentes, era só mudar o source ou o target
    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
