package io.github.feeato.libraryapi.validator;

import io.github.feeato.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
