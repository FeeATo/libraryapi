package io.github.feeato.libraryapi.repository.specs;

import io.github.feeato.libraryapi.model.entity.GeneroLivro;
import io.github.feeato.libraryapi.model.entity.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> titulolike(String titulo) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro generoLivro) {
        return (root, query, cb) -> cb.equal(root.get("genero"), generoLivro);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        //esse cara faz um where tipo assim:
        // and to_char(data_publicacao, 'YYYY') = :anoPublicacao ----> esse cara "to_char" converte um localDate pra algum formato em texto
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
//            return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%"); //esse jeito aqui dá inner join sempre
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);

            return cb.like(cb.upper(joinAutor.get("nome")/*'nome' é uma coluna da tabela autor */), nome.toUpperCase());
        };
    }

}