package io.github.feeato.libraryapi.repository;

import io.github.feeato.libraryapi.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
