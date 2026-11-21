package pe.edu.cibertec.matricula_api.bdd.preguntas;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

public final class RespuestaApi {

    private RespuestaApi() {
    }

    public static Question<Integer> codigo() {
        return Question.about("el código HTTP")
                .answeredBy(actor -> SerenityRest.lastResponse().statusCode());
    }

    public static Question<Object> campo(String ruta) {
        return Question.about("el campo " + ruta)
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().get(ruta));
    }

    public static Question<Integer> entero(String ruta) {
        return Question.about("el campo entero " + ruta)
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getInt(ruta));
    }

    public static Question<Double> decimal(String ruta) {
        return Question.about("el campo decimal " + ruta)
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getDouble(ruta));
    }
}
