package pe.edu.cibertec.matricula_api.bdd.preguntas;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

public final class ErrorApi {

    private ErrorApi() {
    }

    public static Question<String> codigo() {
        return Question.about("el código del error")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getString("codigo"));
    }
}
