package io.github.feeato.libraryapi.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public") //o schema public no postgres é o padrão
@Data
@NoArgsConstructor
public class Autor {

    @Id
    @Column(name = "id") //não precisa colocar isso, o JPA já reconhece que todas as propriedades são colunas
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String nacionalidade;

    //faz com que o JPA saiba q isso n é uma coluna mas eles tem relação
    @OneToMany(mappedBy = "autor") //o mapperBy referencia o nome da propriedade na classe Livro que referencia o Autor
    private List<Livro> livros;

    public Autor(String nome, String nacionalidade, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nacionalidade='" + nacionalidade + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", nome='" + nome + '\'' +
                ", id=" + id +
                '}';
    }

    public Autor setId(UUID id) {
        this.id = id;
        return this;
    }
}
