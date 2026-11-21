package pe.edu.cibertec.matricula_api.bdd.tareas;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ObtenerPagos implements Task {

    private final String alumno;
    private final String metodo;

    public ObtenerPagos(String alumno, String metodo) {
        this.alumno = alumno;
        this.metodo = metodo;
    }

    public static ObtenerPagos de(String alumno, String metodo) {
        return new ObtenerPagos(alumno, metodo);
    }

    @Override
    @Step("{0} consulta los pagos de #alumno por método #metodo")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Get.resource("/api/pagos")
                .with(peticion -> peticion.header("Authorization", "Bearer " + credencial)
                        .queryParam("alumno", alumno)
                        .queryParam("metodo", metodo)));
    }
}
