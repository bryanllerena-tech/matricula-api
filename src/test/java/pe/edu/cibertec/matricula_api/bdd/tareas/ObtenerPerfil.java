package pe.edu.cibertec.matricula_api.bdd.tareas;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ObtenerPerfil implements Task {

    private final String claveCredencial;

    public ObtenerPerfil(String claveCredencial) {
        this.claveCredencial = claveCredencial;
    }

    public static ObtenerPerfil conCredencialActual() {
        return new ObtenerPerfil("credencial");
    }

    public static ObtenerPerfil conCredencialNueva() {
        return new ObtenerPerfil("credencialNueva");
    }

    @Override
    @Step("{0} muestra su perfil")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall(claveCredencial);
        actor.attemptsTo(Get.resource("/api/auth/perfil")
                .with(peticion -> peticion.header("Authorization", "Bearer " + credencial)));
    }
}
