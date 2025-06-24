package io.github.feeato.libraryapi.service.authenticationServer;

import io.github.feeato.libraryapi.model.entity.Client;
import io.github.feeato.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Client client) {
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        clientRepository.save(client);
    }

    public Client obterPorId(String clientId) {
        return clientRepository.findById(UUID.fromString(clientId)).orElse(null);
    }
}
