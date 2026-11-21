package pe.edu.cibertec.matricula_api.bdd.tareas;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ObtenerConstancia implements Task {

    private final String alumno;

    public ObtenerConstancia(String alumno) {
        this.alumno = alumno;
    }

    public static ObtenerConstancia de(String alumno) {
        return new ObtenerConstancia(alumno);
    }

    @Override
    @Step("{0} consulta la constancia de #alumno")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Get.resource("/api/alumnos/{codigo}/constancia")
                .with(peticion -> peticion.pathParam("codigo", alumno)
                        .header("Authorization", "Bearer " + credencial)));
    }
}
