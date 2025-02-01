package io.github.feeato.libraryapi.model;

public enum GeneroLivro {
    FICCAO("FICÇÃO"),
    FANTASIA("FANTASIA"),
    MISTERIO("MISTÉRIO"),
    ROMANCE("ROMÂNCE"),
    BIOGRAFIA("BIOGRAFIA");

    private String nome;

    GeneroLivro(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
