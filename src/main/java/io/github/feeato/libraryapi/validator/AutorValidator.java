package io.github.feeato.libraryapi.validator;

import io.github.feeato.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.feeato.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor //cria um construtor com todos os argumentos OBRIGATÒRIOS (todos que são 'final')
public class AutorValidator {

    private final AutorRepository autorRepository;
    private final LivroValidator livroValidator;

    public void validarPersistir(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    public void validarRemove(Autor autor) {
        livroValidator.validarAutorRemover(autor);
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findAutorByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if (autor.getId() == null) { //cai aqui se for um cadastro de um autor novo
            return autorEncontrado.isPresent();
        }
        return autorEncontrado.isPresent() && autor.getId().equals(autorEncontrado.get().getId()); //cai aqui se o @param autor que está sendo atualizado vai ficar igual a algum já existente no banco
    }
}
