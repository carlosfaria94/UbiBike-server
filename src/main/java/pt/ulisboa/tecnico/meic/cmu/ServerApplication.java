// tag::runner[]
package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
// end::runner[]

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException extends RuntimeException {

	public NotFoundException(String id) {
		super("could not find '" + id + "'.");
	}
}