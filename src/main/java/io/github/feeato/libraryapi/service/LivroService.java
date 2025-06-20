package io.github.feeato.libraryapi.service;

import io.github.feeato.libraryapi.exceptions.RegistroNaoEncontradoException;
import io.github.feeato.libraryapi.model.dto.LivroDTO;
import io.github.feeato.libraryapi.model.dto.mapper.LivroMapper;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.model.entity.GeneroLivro;
import io.github.feeato.libraryapi.model.entity.Livro;
import io.github.feeato.libraryapi.repository.LivroRepository;
import io.github.feeato.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.github.feeato.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorService autorService;
    private final LivroValidator livroValidator;
    private final LivroMapper livroMapper;


    @Transactional
    public LivroDTO salvarLivro(LivroDTO livroDTO) {
        Livro livro = livroMapper.toEntity(livroDTO);

        if (livro.getAutor() != null) {
            Optional<Autor> autor = autorService.buscarAutorPorId(livro.getAutor().getId());
            if (autor.isPresent()) {
                livro.setAutor(autor.get());
            } else {
                throw new RegistroNaoEncontradoException("Entidade não encontrada", "Autor", livroDTO.autor().id().toString());
            }
        }
        livroValidator.validarPersistir(livro);
        return livroMapper.toDTO(livroRepository.save(livro));
    }


    public Optional<LivroDTO> buscarLivroDTOPorIdComAutor(String id) {
        return buscarLivroPorIdComAutor(id).map(livroMapper::toDTO);
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

    public Page<LivroDTO> pesquisar(String isbn,
                                    String titulo,
                                    String nomeAutor,
                                    GeneroLivro genero,
                                    Integer anoPublicacao,
                                    Integer pagina,
                                    Integer tamanhoPagina) {
        Specification<Livro> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null) {
            specs = specs.and(titulolike(titulo));
        }
        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }
        if (anoPublicacao!=null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }
        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs, PageRequest.of(pagina, tamanhoPagina)).map(livroMapper::toDTO);
    }

    @Transactional
    public Optional<LivroDTO> removerLivro(String id) {
        Optional<Livro> livro = buscarLivroPorId(id);
        if (livro.isPresent()) {
            livroRepository.delete(livro.get());
            return livro.map(livroMapper::toDTO);
        } else {
            throw new RegistroNaoEncontradoException("Livro não encontrado", "Livro", id);
        }
    }
}
