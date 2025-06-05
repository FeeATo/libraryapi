package io.github.feeato.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //liga a auditoria das classes pra criar campos que tem annotations que tem ações assim
public class LibraryapiApplication {


	public static void main(String[] args) {
		SpringApplication.run(LibraryapiApplication.class, args);
	}
}
