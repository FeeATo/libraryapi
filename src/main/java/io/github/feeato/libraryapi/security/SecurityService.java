package io.github.feeato.libraryapi.security;

import io.github.feeato.libraryapi.model.entity.Usuario;
import io.github.feeato.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomAuthentication) authentication.getPrincipal()).getUsuario(); //esse cara é um CustomAuthentication por causa da classe CustomAuthenticationProvider
    }

}
