package pe.edu.cibertec.matricula_api.bdd.tareas;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ObtenerHistorial implements Task {

    private final String alumno;

    public ObtenerHistorial(String alumno) {
        this.alumno = alumno;
    }

    public static ObtenerHistorial de(String alumno) {
        return new ObtenerHistorial(alumno);
    }

    @Override
    @Step("{0} consulta el historial de #alumno")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Get.resource("/api/alumnos/{codigo}/historial")
                .with(peticion -> peticion.pathParam("codigo", alumno)
                        .header("Authorization", "Bearer " + credencial)));
    }
}
