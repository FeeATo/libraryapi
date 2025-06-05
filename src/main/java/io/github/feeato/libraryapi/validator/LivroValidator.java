package io.github.feeato.libraryapi.validator;

import io.github.feeato.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.feeato.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.model.entity.Livro;
import io.github.feeato.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor //cria um construtor com todos os argumentos OBRIGATÒRIOS (todos que são 'final')
public class LivroValidator {

    private final LivroRepository livroRepository;

    public void validarAutorRemover(Autor autor) {
        if (existeLivroDoAutor(autor)) {
            throw new OperacaoNaoPermitidaException("Autor possui livros, por isso não pode ser excluído");
        }
    }

    private boolean existeLivroDoAutor(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }

    public void validarPersistir(Livro livro) {
        if (existeLivroCadastrado(livro)) {
            throw new RegistroDuplicadoException("IBNS duplicado");
        }
    }

    private boolean existeLivroCadastrado(Livro livro) {
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null) { //cai aqui se for um cadastro de um autor novo
            return livroEncontrado.isPresent();
        }
        return livroEncontrado.isPresent() && livro.getId().equals(livroEncontrado.get().getId()); //cai aqui se o @param autor que está sendo atualizado vai ficar igual a algum já existente no banco
    }
}
