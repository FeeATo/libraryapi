package io.github.feeato.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "genero", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @JoinColumn(name = "id_autor")
    //CascadeType.MERGE -> atualiza a entidade Pai também. Se não tiver ID, vai dar pau
    //CascadeType.PERSIST -> só salva a entidade Pai novamente, nada d+
    //CascadeType.ALL -> faz tudo
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Autor autor;

    public Livro(String isbn, String titulo, LocalDate dataPublicacao, GeneroLivro genero, BigDecimal preco, Autor autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.dataPublicacao = dataPublicacao;
        this.genero = genero;
        this.preco = preco;
        this.autor = autor;
    }

    @Override
    public String   toString() {
        return "Livro{" +
                "preco=" + preco +
                ", genero=" + genero +
                ", dataPublicacao=" + dataPublicacao +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", id=" + id +
                '}';
    }
}
