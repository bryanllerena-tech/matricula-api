package pe.edu.cibertec.matricula_api.bdd.tareas;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

import java.util.Map;

public class ColocarNota implements Task {

    private final Long idMatricula;
    private final Double nota;

    public ColocarNota(Long idMatricula, Double nota) {
        this.idMatricula = idMatricula;
        this.nota = nota;
    }

    public static ColocarNota en(Long idMatricula, Double nota) {
        return new ColocarNota(idMatricula, nota);
    }

    @Override
    @Step("{0} anota #nota en la matrícula #idMatricula")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Patch.to("/api/matriculas/{id}/nota")
                .with(peticion -> peticion.pathParam("id", idMatricula)
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + credencial)
                        .body(Map.of("nota", nota))));
    }
}
