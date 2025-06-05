package io.github.feeato.libraryapi.service;

import io.github.feeato.libraryapi.model.entity.Autor;
import io.github.feeato.libraryapi.model.entity.GeneroLivro;
import io.github.feeato.libraryapi.model.entity.Livro;
import io.github.feeato.libraryapi.repository.AutorRepository;
import io.github.feeato.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor //cria um construtor com todos os argumentos OBRIGATÒRIOS (todos que são 'final')
public class TransacaoService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto() {
        Livro livro = new Livro("blabla", "titulo", LocalDate.now(), GeneroLivro.FICCAO, BigDecimal.valueOf(100.0), null);
        livro = livroRepository.save(livro);

        UUID id = livro.getId();
        //*salva foto do livro num bucket na núvem*
        //bucketService.salvar(livro.getFoto(), id + ".png");

        //atualizar o nome do arquivo que foi salvo
        //livro.setNomeArquivoFoto(id + ".png");

        /* NESTE PONTO NÃO PRECISA CHAMAR O livroRepository.save(livro) NOVAMENTE!!! Já que a entidade é MANAGED, ela já vai ser atualizada automaticamente
        * no final da transação */

    }

    /**
     * A nacionalidade do autor vai ser atualizada no banco no fim deste método porque a entidade autor é MANAGED, então ela é atualizada
     * mesmo sem chamar o merge
     * **/
    @Transactional
    public void atualizacaoSemMerge() {
        Autor autor = autorRepository.findAll().stream().findFirst().orElse(null);
        if (autor != null) {
            autor.setNacionalidade("OI " + new Date());
        }
    }

    @Transactional
    public void testeRollBack() {
        Autor autor = new Autor("Maria", "Brasileira", LocalDate.of(1951, 1, 31));
        autor.setLivros(List.of(new Livro("192310293", "livro criado ao salvar Autor", LocalDate.now(), GeneroLivro.FICCAO, BigDecimal.TEN, autor)));
        autor = autorRepository.save(autor);
        System.out.println("Autor salvo: " + autor);

        if (true) {
            throw new RuntimeException("Não commitou a transaction!");
        }

    }

}
