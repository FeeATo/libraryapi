package io.github.feeato.libraryapi.model.entity;

public enum GeneroLivro {
    FICCAO("FICÇÃO"),
    FANTASIA("FANTASIA"),
    MISTERIO("MISTÉRIO"),
    ROMANCE("ROMÂNCE"),
    BIOGRAFIA("BIOGRAFIA");

    private final String nome;

    GeneroLivro(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
