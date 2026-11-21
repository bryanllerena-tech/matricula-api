package pe.edu.cibertec.matricula_api.bdd.pasos;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pe.edu.cibertec.matricula_api.bdd.preguntas.ComprobantePago;
import pe.edu.cibertec.matricula_api.bdd.preguntas.RespuestaApi;
import pe.edu.cibertec.matricula_api.bdd.tareas.CancelarMatricula;
import pe.edu.cibertec.matricula_api.bdd.tareas.CrearMatricula;
import pe.edu.cibertec.matricula_api.bdd.tareas.LiquidarMatricula;
import pe.edu.cibertec.matricula_api.bdd.tareas.ObtenerConstancia;
import pe.edu.cibertec.matricula_api.bdd.tareas.ObtenerPagos;
import pe.edu.cibertec.matricula_api.bdd.tareas.PedirCotizacion;
import pe.edu.cibertec.matricula_api.bdd.tareas.RecordarMatricula;
import pe.edu.cibertec.matricula_api.bdd.util.Numeros;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PasosMatricula {

    @When("{word} pide el presupuesto de {string} para {string}")
    public void pedirPresupuesto(String nombre, String curso, String alumno) {
        theActorCalled(nombre).attemptsTo(PedirCotizacion.de(alumno, curso));
    }

    @And("el presupuesto muestra {int} créditos, subtotal {word}, descuento {int} y total {word}")
    public void verificarPresupuesto(int creditos, String subtotal, int descuento, String total) {
        theActorInTheSpotlight().should(seeThat(RespuestaApi.entero("creditos"), equalTo(creditos)));
        theActorInTheSpotlight().should(seeThat(RespuestaApi.decimal("subtotal"),
                equalTo(Numeros.decimal(subtotal))));
        theActorInTheSpotlight().should(seeThat(RespuestaApi.entero("descuento"), equalTo(descuento)));
        theActorInTheSpotlight().should(seeThat(RespuestaApi.decimal("total"),
                equalTo(Numeros.decimal(total))));
    }

    @When("{word} registra a {string} en {string}")
    public void registrarMatricula(String nombre, String alumno, String curso) {
        theActorCalled(nombre).attemptsTo(CrearMatricula.de(alumno, curso));
    }

    @And("el expediente queda en {string}")
    public void verificarEstado(String estado) {
        theActorInTheSpotlight().should(seeThat(RespuestaApi.campo("estado"), equalTo(estado)));
    }

    @And("el monto del expediente es {word}")
    public void verificarMonto(String monto) {
        theActorInTheSpotlight().should(seeThat(RespuestaApi.decimal("monto"),
                equalTo(Numeros.decimal(monto))));
    }

    @When("{word} cancela el cobro con {string}")
    public void cancelarCobro(String nombre, String metodo) {
        theActorCalled(nombre).attemptsTo(LiquidarMatricula.conMetodo(metodo));
    }

    @And("el expediente consigna fecha de pago")
    public void verificarFechaPago() {
        theActorInTheSpotlight().should(seeThat(RespuestaApi.campo("fechaPago"), notNullValue()));
    }

    @When("{word} consulta los cobros de {string} por {string}")
    public void consultarCobros(String nombre, String alumno, String metodo) {
        theActorCalled(nombre).attemptsTo(ObtenerPagos.de(alumno, metodo));
    }

    @And("el comprobante del expediente recordado usa {string} y su operación es válida")
    public void verificarComprobante(String metodo) {
        Long id = theActorInTheSpotlight().recall("idMatriculaActual");
        theActorInTheSpotlight().should(seeThat(ComprobantePago.coincide(id, metodo), is(true)));
    }

    @Given("que {word} ubica el expediente pagado de {string}")
    public void ubicarPagada(String nombre, String curso) {
        theActorCalled(nombre).attemptsTo(RecordarMatricula.pagadaComoActual(curso));
    }

    @When("{word} intenta anular el expediente pagado")
    public void intentarAnulacion(String nombre) {
        theActorCalled(nombre).attemptsTo(new CancelarMatricula());
    }

    @When("{word} consulta la constancia de {string}")
    public void consultarConstancia(String nombre, String alumno) {
        theActorCalled(nombre).attemptsTo(ObtenerConstancia.de(alumno));
    }
}
