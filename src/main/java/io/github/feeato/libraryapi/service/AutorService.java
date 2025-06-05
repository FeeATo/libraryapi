package io.github.feeato.libraryapi.service;

import io.github.feeato.libraryapi.model.dto.AutorDTO;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.repository.AutorRepository;
import io.github.feeato.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
    public AutorDTO salvarAutor(AutorDTO autorDTO) {
        Autor autor = autorDTO.gerarAutor();
        autorValidator.validarPersistir(autor);
        return autorRepository.save(autor).gerarDTO();
    }

    public Optional<AutorDTO> buscarAutorDTO(String id) {
        return buscarAutorPorId(id).map(Autor::gerarDTO);
    }

    public Optional<Autor> buscarAutorPorId(String id) {
        return buscarAutorPorId(UUID.fromString(id));
    }

    public Optional<Autor> buscarAutorPorId(UUID id) {
        return autorRepository.findById(id);
    }

    @Transactional
    public Optional<AutorDTO> removerAutor(String id) {
        Optional<Autor> autor = buscarAutorPorId(id);
        if (autor.isPresent()) {
            autorValidator.validarRemove(autor.get());
            autorRepository.delete(autor.get());
        }
        return autor.map(Autor::gerarDTO);
    }

    public AutorDTO[] buscarAutorComParametros(String nome, String nacionalidade) {
        //campos nulos são ignorados no Example

        ExampleMatcher mather = ExampleMatcher.matching()
                .withIgnoreNullValues()
//                .withIgnorePaths("id", "dataNascimento") //faz ignorar alguns campos do objeto que foi passado no example
                .withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //valida se a string tem que ser conter algo ou começar e tals.
                ;

        return autorRepository.findAll(Example.of(new Autor(nome, nacionalidade, null), mather))
                .stream()
                .map(Autor::gerarDTO)
                .toArray(AutorDTO[]::new);
    }

    @Transactional
    public Optional<AutorDTO> atualizarAutor(String id, AutorDTO autorDTO) {
        Optional<Autor> autorOp = autorRepository.findById(UUID.fromString(id));

        if (autorOp.isPresent()) {
            Autor autor = autorOp.get().atualizarCampos(autorDTO);
            autorValidator.validarPersistir(autor);
            autorRepository.save(autor);
            return Optional.of(autor.gerarDTO());
        }
        return Optional.empty();
    }
}
