package pe.edu.cibertec.matricula_api.bdd.pasos;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pe.edu.cibertec.matricula_api.bdd.tareas.FallarIngreso;
import pe.edu.cibertec.matricula_api.bdd.tareas.ListarTodosLosAlumnos;
import pe.edu.cibertec.matricula_api.bdd.tareas.ObtenerPerfil;
import pe.edu.cibertec.matricula_api.bdd.tareas.RenovarCredenciales;
import pe.edu.cibertec.matricula_api.bdd.tareas.TerminarSesion;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class PasosSeguridad {

    @When("{word} canjea el refresco guardado")
    public void canjearRefresco(String nombre) {
        theActorCalled(nombre).attemptsTo(RenovarCredenciales.conRefrescoGuardado());
    }

    @When("{word} vuelve a presentar el refresco original")
    public void reutilizarRefresco(String nombre) {
        theActorCalled(nombre).attemptsTo(RenovarCredenciales.conRefrescoInicial());
    }

    @When("{word} muestra el perfil con la credencial recién emitida")
    public void perfilConNueva(String nombre) {
        theActorCalled(nombre).attemptsTo(ObtenerPerfil.conCredencialNueva());
    }

    @When("{word} cierra la sesión")
    public void cerrarSesion(String nombre) {
        theActorCalled(nombre).attemptsTo(new TerminarSesion());
    }

    @When("{word} muestra el perfil con la credencial actual")
    public void perfilConActual(String nombre) {
        theActorCalled(nombre).attemptsTo(ObtenerPerfil.conCredencialActual());
    }

    @When("{word} prueba una clave equivocada para {string} con {string}")
    public void claveEquivocada(String nombre, String usuario, String contrasena) {
        theActorCalled(nombre).attemptsTo(FallarIngreso.como(usuario, contrasena));
    }

    @When("{word} prueba la clave correcta para {string} con {string}")
    public void claveCorrecta(String nombre, String usuario, String contrasena) {
        theActorCalled(nombre).attemptsTo(FallarIngreso.como(usuario, contrasena));
    }

    @And("{word} pide el listado global de alumnos")
    public void listarAlumnos(String nombre) {
        theActorCalled(nombre).attemptsTo(new ListarTodosLosAlumnos());
    }
}
