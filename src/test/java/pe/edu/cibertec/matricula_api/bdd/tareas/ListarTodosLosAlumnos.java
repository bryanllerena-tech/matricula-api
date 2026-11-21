package pe.edu.cibertec.matricula_api.bdd.tareas;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ListarTodosLosAlumnos implements Task {

    @Override
    @Step("{0} intenta listar todos los alumnos")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Get.resource("/api/alumnos")
                .with(peticion -> peticion.header("Authorization", "Bearer " + credencial)));
    }
}
