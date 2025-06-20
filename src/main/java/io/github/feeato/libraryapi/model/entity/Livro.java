package io.github.feeato.libraryapi.model.entity;

import io.github.feeato.libraryapi.model.dto.AutorDTO;
import io.github.feeato.libraryapi.model.dto.LivroDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) //diz ao spring que ele tem que colocar um listener nessa classe para tomar alguma ação toda vez que tiver alguma modificação, assim as annotations @CreatedDate e @LastModifiedDate vão funcionar
public class Livro {


    public static final int ISBN_LENGTH = 17;
    public static final int TITULO_LENGTH = 150;
    public static final int GENERO_LENGTH = 30;
//    public static final int PRECO_LENGTH = 30;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Autor autor;

    @CreatedDate //faz com que o spring coloque uma data aqui toda vez que for criada uma entidade
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @LastModifiedDate //toda vez que eu fizer um update, o spring vai atualizar essa data aqui
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID usuario;

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
