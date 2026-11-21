package pe.edu.cibertec.matricula_api.bdd.pasos;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import pe.edu.cibertec.matricula_api.bdd.preguntas.ErrorApi;
import pe.edu.cibertec.matricula_api.bdd.preguntas.RespuestaApi;
import pe.edu.cibertec.matricula_api.bdd.tareas.EntrarAlSistema;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class PasosGenerales {

    private final String baseUrl = EnvironmentSpecificConfiguration
            .from(SystemEnvironmentVariables.currentEnvironmentVariables())
            .getProperty("restapi.baseurl");

    @Given("que dejo a {word} frente a la API de matrícula")
    public void prepararActor(String nombre) {
        theActorCalled(nombre).whoCan(CallAnApi.at(baseUrl));
    }

    @When("{word} obtiene su credencial con {string} y {string}")
    public void obtenerCredencial(String nombre, String usuario, String contrasena) {
        theActorCalled(nombre).attemptsTo(EntrarAlSistema.como(usuario, contrasena));
    }

    @Then("la API responde {int}")
    public void verificarRespuesta(int esperado) {
        theActorInTheSpotlight().should(seeThat(RespuestaApi.codigo(), equalTo(esperado)));
    }

    @And("el error indica {string}")
    public void verificarCodigoError(String codigo) {
        theActorInTheSpotlight().should(seeThat(ErrorApi.codigo(), equalTo(codigo)));
    }

    @And("el cuerpo valida el esquema {string}")
    public void verificarEsquema(String esquema) {
        theActorInTheSpotlight().should(seeThatResponse("valida el esquema " + esquema,
                respuesta -> respuesta.body(matchesJsonSchemaInClasspath("schemas/" + esquema))));
    }
}
