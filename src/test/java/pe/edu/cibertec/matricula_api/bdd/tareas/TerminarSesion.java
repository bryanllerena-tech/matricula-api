package pe.edu.cibertec.matricula_api.bdd.tareas;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class TerminarSesion implements Task {

    @Override
    @Step("{0} cierra su sesión")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Post.to("/api/auth/logout")
                .with(peticion -> peticion.header("Authorization", "Bearer " + credencial)));
    }
}
