package pe.edu.cibertec.matricula_api.bdd.tareas;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

import java.util.Map;

public class LiquidarMatricula implements Task {

    private final String metodo;

    public LiquidarMatricula(String metodo) {
        this.metodo = metodo;
    }

    public static LiquidarMatricula conMetodo(String metodo) {
        return new LiquidarMatricula(metodo);
    }

    @Override
    @Step("{0} liquida la matrícula con #metodo")
    public <T extends Actor> void performAs(T actor) {
        Long id = actor.recall("idMatriculaActual");
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Patch.to("/api/matriculas/{id}/pagar")
                .with(peticion -> peticion.pathParam("id", id)
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + credencial)
                        .body(Map.of("metodo", metodo))));
    }
}
