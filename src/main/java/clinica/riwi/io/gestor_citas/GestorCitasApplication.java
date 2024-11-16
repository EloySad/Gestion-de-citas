package clinica.riwi.io.gestor_citas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "clinica.riwi.io.gestor_citas")
public class GestorCitasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorCitasApplication.class, args);
	}

}
