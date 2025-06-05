package io.github.feeato.libraryapi.model.entity;

import io.github.feeato.libraryapi.model.dto.AutorDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public") //o schema public no postgres é o padrão
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) //diz ao spring que ele tem que colocar um listener nessa classe para tomar alguma ação toda vez que tiver alguma modificação, assim as annotations @CreatedDate e @LastModifiedDate vão funcionar
public class Autor {

    @Id
    @Column(name = "id") //não precisa colocar isso, o JPA já reconhece que todas as propriedades são colunas
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String nacionalidade;

    @CreatedDate //faz com que o spring coloque uma data aqui toda vez que for criada uma entidade
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @LastModifiedDate //toda vez que eu fizer um update, o spring vai atualizar essa data aqui
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID usuario;

    //faz com que o JPA saiba q isso n é uma coluna mas eles tem relação
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL) //o mapperBy referencia o nome da propriedade na classe Livro que referencia o Autor
    private List<Livro> livros; //só vai salvar os filhos se tiver com CascadeType.PERSIST ou  ALL. Ele não busca os elementos quando chama uma Select

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

    public String toStringCompleto(){
        return "Autor{" +
                "nacionalidade='" + nacionalidade + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", nome='" + nome + '\'' +
                ", id=" + id +  '\'' +
                ", livros=" + livros +
                '}';
    }

    public Autor setId(UUID id) {
        this.id = id;
        return this;
    }

    public AutorDTO gerarAutorDTO() {
        return new AutorDTO(this.id, this.nome, this.dataNascimento, this.nacionalidade);
    }
}
