package pe.edu.cibertec.matricula_api.bdd.tareas;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class RenovarCredenciales implements Task {

    private final String claveRefresco;

    public RenovarCredenciales(String claveRefresco) {
        this.claveRefresco = claveRefresco;
    }

    public static RenovarCredenciales conRefrescoGuardado() {
        return new RenovarCredenciales("refresco");
    }

    public static RenovarCredenciales conRefrescoInicial() {
        return new RenovarCredenciales("refrescoInicial");
    }

    @Override
    @Step("{0} renueva sus credenciales")
    public <T extends Actor> void performAs(T actor) {
        String refresco = actor.recall(claveRefresco);
        actor.attemptsTo(Post.to("/api/auth/refresh")
                .with(peticion -> peticion.contentType(ContentType.JSON)
                        .body(Map.of("refreshToken", refresco))));
        if (SerenityRest.lastResponse().statusCode() == 200) {
            String nuevaCredencial = SerenityRest.lastResponse().jsonPath().getString("accessToken");
            actor.remember("credencialNueva", nuevaCredencial);
            actor.remember("credencial", nuevaCredencial);
            actor.remember("refresco", SerenityRest.lastResponse().jsonPath().getString("refreshToken"));
        }
    }
}
