package io.github.feeato.libraryapi.service;

import io.github.feeato.libraryapi.exceptions.RegistroNaoEncontradoException;
import io.github.feeato.libraryapi.model.dto.LivroDTO;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.model.entity.Livro;
import io.github.feeato.libraryapi.repository.LivroRepository;
import io.github.feeato.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorService autorService;
    private final LivroValidator livroValidator;


    @Transactional
    public LivroDTO salvarLivro(LivroDTO livroDTO) {
        Livro livro = livroDTO.gerarLivro();

        if (livro.getAutor() != null) {
            Optional<Autor> autor = autorService.buscarAutorPorId(livro.getAutor().getId());
            if (autor.isPresent()) {
                livro.setAutor(autor.get());
            } else {
                throw new RegistroNaoEncontradoException("Entidade não encontrada", "Autor", livroDTO.autor().id().toString());
            }
        }
        livroValidator.validarPersistir(livro);
        return livroRepository.save(livro).gerarDTO();
    }


    public Optional<LivroDTO> buscarLivroDTOPorIdComAutor(String id) {
        return buscarLivroPorIdComAutor(id).map(Livro::gerarDTO);
    }

    public Optional<Livro> buscarLivroPorIdComAutor(String id) {
        return buscarLivroPorIdComAutor(UUID.fromString(id));
    }

    public Optional<Livro> buscarLivroPorIdComAutor(UUID id) {
        return livroRepository.findByIdWithAutor(id);
    }
    
    public Optional<Livro> buscarLivroPorId(String id) {
        return buscarLivroPorId(UUID.fromString(id));
    }
    
    public Optional<Livro> buscarLivroPorId(UUID id) {
        return livroRepository.findById(id);
    }

    @Transactional
    public Optional<LivroDTO> removerLivro(String id) {
        Optional<Livro> livro = buscarLivroPorId(id);
        if (livro.isPresent()) {
            livroRepository.delete(livro.get());
            return livro.map(Livro::gerarDTO);
        } else {
            throw new RegistroNaoEncontradoException("Livro não encontrado", "Livro", id);
        }
    }
}
