package io.github.feeato.libraryapi.service;

import io.github.feeato.libraryapi.model.dto.AutorDTO;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Transactional
    public Autor salvarAutor(AutorDTO autorDTO) {
        Autor autor = autorDTO.gerarAutor();
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
            autorRepository.delete(autor.get());
        }
        return autor.map(Autor::gerarAutorDTO);
    }
}
