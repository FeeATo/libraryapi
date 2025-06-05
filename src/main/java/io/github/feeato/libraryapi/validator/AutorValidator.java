package io.github.feeato.libraryapi.validator;

import io.github.feeato.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findAutorByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if (autor.getId() == null) { //cai aqui se for um cadastro de um autor novo
            return autorEncontrado.isPresent();
        }
        return autorEncontrado.isPresent() && autor.getId().equals(autorEncontrado.get().getId()); //cai aqui se o @param autor que está sendo atualizado vai ficar igual a algum já existente no banco
    }
}
