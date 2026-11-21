package pe.edu.cibertec.matricula_api.bdd.tareas;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class FallarIngreso implements Task {

    private final String usuario;
    private final String contrasena;

    public FallarIngreso(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public static FallarIngreso como(String usuario, String contrasena) {
        return new FallarIngreso(usuario, contrasena);
    }

    @Override
    @Step("{0} intenta entrar como #usuario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to("/api/auth/login")
                .with(peticion -> peticion.contentType(ContentType.JSON)
                        .body(Map.of("username", usuario, "password", contrasena))));
    }
}
