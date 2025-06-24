package io.github.feeato.libraryapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String clientId; //esse cara já é traduzido automaticamente pra 'client_id'
    private String clientSecret;
    @Column(name = "redirect_uri")
    private String redirectURI;
    private String scope;


}
