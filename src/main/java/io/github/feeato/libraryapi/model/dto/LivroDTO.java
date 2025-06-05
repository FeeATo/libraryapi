package io.github.feeato.libraryapi.model.dto;

import io.github.feeato.libraryapi.annotation.EnumValidator;
import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.model.entity.GeneroLivro;
import io.github.feeato.libraryapi.model.entity.Livro;
import io.github.feeato.libraryapi.utils.Utils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroDTO(UUID id,
                       @ISBN @NotBlank(message = Utils.MENSAGEM_CAMPO_OBRIGATORIO) @Size(min = Livro.ISBN_LENGTH, max = Livro.ISBN_LENGTH, message = "O tamanho do ISBN deve ser 17") String isbn,
                       @NotBlank(message = Utils.MENSAGEM_CAMPO_OBRIGATORIO) @Size(max = Livro.TITULO_LENGTH) String titulo,
                       @Past(message = "Data de publicação não pode ser uma data futura") LocalDate dataPublicacao,
                       @EnumValidator(enumClazz = GeneroLivro.class, message = "Valor não permitido") @NotBlank String genero,
                       BigDecimal preco,
                        AutorDTO autor) {
    public Livro gerarLivro() {
        return new Livro(isbn, titulo, dataPublicacao, GeneroLivro.valueOf(genero), preco, new Autor(autor.id()));
    }
}
