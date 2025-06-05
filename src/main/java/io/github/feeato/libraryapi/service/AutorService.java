package io.github.feeato.libraryapi.service;

import io.github.feeato.libraryapi.model.dto.AutorDTO;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.repository.AutorRepository;
import io.github.feeato.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor //cria um construtor com todos os argumentos OBRIGATÒRIOS (todos que são 'final')
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;

    @Transactional
    public Autor salvarAutor(AutorDTO autorDTO) {
        Autor autor = autorDTO.gerarAutor();
        autorValidator.validarPersistir(autor);
        return autorRepository.save(autor);
    }

    public Optional<AutorDTO> buscarAutorDTO(String id) {
        return buscarAutor(id).map(Autor::gerarAutorDTO);
    }

    public Optional<Autor> buscarAutor(String id) {
        return autorRepository.findById(UUID.fromString(id));
    }

    @Transactional
    public Optional<AutorDTO> removerAutor(String id) {
        Optional<Autor> autor = buscarAutor(id);
        if (autor.isPresent()) {
            autorValidator.validarRemove(autor.get());
            autorRepository.delete(autor.get());
        }
        return autor.map(Autor::gerarAutorDTO);
    }

    public AutorDTO[] buscarAutorComParametros(String nome, String nacionalidade) {
        //campos nulos são ignorados no Example
        return autorRepository.findAll(Example.of(new Autor(nome, nacionalidade, null)))
                .stream()
                .map(Autor::gerarAutorDTO)
                .toArray(AutorDTO[]::new);
    }

    @Transactional
    public Optional<AutorDTO> atualizarAutor(String id, AutorDTO autorDTO) {
        Optional<Autor> autorOp = autorRepository.findById(UUID.fromString(id));

        if (autorOp.isPresent()) {
            Autor autor = autorOp.get().atualizarCampos(autorDTO);
            autorValidator.validarPersistir(autor);
            autorRepository.save(autor);
            return Optional.of(autor.gerarAutorDTO());
        }
        return Optional.empty();
    }
}
