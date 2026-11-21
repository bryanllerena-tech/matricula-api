package pe.edu.cibertec.matricula_api.bdd.tareas;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.List;
import java.util.Map;

public class PedirCotizacion implements Task {

    private final String alumno;
    private final String curso;

    public PedirCotizacion(String alumno, String curso) {
        this.alumno = alumno;
        this.curso = curso;
    }

    public static PedirCotizacion de(String alumno, String curso) {
        return new PedirCotizacion(alumno, curso);
    }

    @Override
    @Step("{0} pide cotización de #curso para #alumno")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Post.to("/api/matriculas/cotizacion")
                .with(peticion -> peticion.contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + credencial)
                        .body(Map.of("codigoAlumno", alumno, "cursos", List.of(curso)))));
    }
}
