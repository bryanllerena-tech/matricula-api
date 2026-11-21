package pe.edu.cibertec.matricula_api.bdd.tareas;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

public class CancelarMatricula implements Task {

    @Override
    @Step("{0} intenta anular el expediente pagado")
    public <T extends Actor> void performAs(T actor) {
        Long id = actor.recall("idMatriculaActual");
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Patch.to("/api/matriculas/{id}/anular")
                .with(peticion -> peticion.pathParam("id", id)
                        .header("Authorization", "Bearer " + credencial)));
    }
}
