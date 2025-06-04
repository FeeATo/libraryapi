package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    @Test
    void testeRollBack(){
        transacaoService.testeRollBack();
    }

    @Test
    void testeAtualizacaoSemMerge() {
        transacaoService.atualizacaoSemMerge();
    }

}
