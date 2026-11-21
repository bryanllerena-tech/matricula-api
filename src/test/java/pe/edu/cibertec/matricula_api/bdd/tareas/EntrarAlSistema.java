package pe.edu.cibertec.matricula_api.bdd.tareas;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class EntrarAlSistema implements Task {

    private final String usuario;
    private final String contrasena;

    public EntrarAlSistema(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public static EntrarAlSistema como(String usuario, String contrasena) {
        return new EntrarAlSistema(usuario, contrasena);
    }

    @Override
    @Step("{0} obtiene su credencial como #usuario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to("/api/auth/login")
                .with(peticion -> peticion.contentType(ContentType.JSON)
                        .body(Map.of("username", usuario, "password", contrasena))));
        String credencial = SerenityRest.lastResponse().jsonPath().getString("accessToken");
        String refresco = SerenityRest.lastResponse().jsonPath().getString("refreshToken");
        actor.remember("credencial", credencial);
        actor.remember("refresco", refresco);
        actor.remember("credencialInicial", credencial);
        actor.remember("refrescoInicial", refresco);
    }
}
