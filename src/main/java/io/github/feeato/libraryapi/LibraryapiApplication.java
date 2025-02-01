package io.github.feeato.libraryapi;

import io.github.feeato.libraryapi.model.Autor;
import io.github.feeato.libraryapi.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryapiApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LibraryapiApplication.class, args);

		AutorRepository autorRepository = context.getBean(AutorRepository.class);
		salvarRegistro(autorRepository);
	}

	public static void salvarRegistro(AutorRepository autorRepository) {
		Autor autor = new Autor("José", "Brasileira", LocalDate.of(1950, 1, 31));
		autor = autorRepository.save(autor);
		System.out.println("Autor salvo: " + autor);
	}
}
