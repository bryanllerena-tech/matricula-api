package pe.edu.cibertec.matricula_api.bdd.tareas;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class CrearMatricula implements Task {

    private final String alumno;
    private final String curso;

    public CrearMatricula(String alumno, String curso) {
        this.alumno = alumno;
        this.curso = curso;
    }

    public static CrearMatricula de(String alumno, String curso) {
        return new CrearMatricula(alumno, curso);
    }

    @Override
    @Step("{0} registra a #alumno en #curso")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Post.to("/api/matriculas")
                .with(peticion -> peticion.contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + credencial)
                        .body(Map.of("codigoAlumno", alumno, "codigoCurso", curso))));
        actor.remember("idMatriculaActual",
                SerenityRest.lastResponse().jsonPath().getLong("id"));
    }
}
