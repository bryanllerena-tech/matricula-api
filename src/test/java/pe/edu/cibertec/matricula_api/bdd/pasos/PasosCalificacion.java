package pe.edu.cibertec.matricula_api.bdd.pasos;

import io.cucumber.java.en.And;
import pe.edu.cibertec.matricula_api.bdd.preguntas.LibroCalificaciones;
import pe.edu.cibertec.matricula_api.bdd.tareas.ColocarNota;
import pe.edu.cibertec.matricula_api.bdd.tareas.ObtenerHistorial;
import pe.edu.cibertec.matricula_api.bdd.tareas.RecordarMatricula;
import pe.edu.cibertec.matricula_api.bdd.util.Numeros;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

public class PasosCalificacion {

    @And("{word} ubica su expediente pagado de {string}")
    public void ubicarPagada(String nombre, String curso) {
        theActorCalled(nombre).attemptsTo(RecordarMatricula.pagada(curso));
    }

    @And("{word} ubica su expediente registrado de {string}")
    public void ubicarRegistrada(String nombre, String curso) {
        theActorCalled(nombre).attemptsTo(RecordarMatricula.registrada(curso));
    }

    @And("{word} anota {word} en el expediente pagado de {word}")
    public void anotarEnPagada(String docente, String nota, String alumna) {
        Long id = theActorCalled(alumna).recall("idMatriculaPagada");
        theActorCalled(docente).attemptsTo(ColocarNota.en(id, Numeros.decimal(nota)));
    }

    @And("{word} anota {word} en el expediente registrado de {word}")
    public void anotarEnRegistrada(String docente, String nota, String alumna) {
        Long id = theActorCalled(alumna).recall("idMatriculaPendiente");
        theActorCalled(docente).attemptsTo(ColocarNota.en(id, Numeros.decimal(nota)));
    }

    @And("{word} consulta el historial de {string}")
    public void consultarHistorial(String nombre, String alumno) {
        theActorCalled(nombre).attemptsTo(ObtenerHistorial.de(alumno));
    }

    @And("el historial marca {string} en {string}, {int} créditos aprobados y promedio {word}")
    public void verificarHistorial(String condicion, String curso, int creditos, String promedio) {
        theActorInTheSpotlight().should(seeThat(LibroCalificaciones.condicion(curso), equalTo(condicion)));
        theActorInTheSpotlight().should(seeThat(LibroCalificaciones.creditosAprobados(), equalTo(creditos)));
        theActorInTheSpotlight().should(seeThat(LibroCalificaciones.promedio(),
                closeTo(Numeros.decimal(promedio), 0.01)));
    }
}
