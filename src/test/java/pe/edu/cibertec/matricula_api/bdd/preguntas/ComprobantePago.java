package pe.edu.cibertec.matricula_api.bdd.preguntas;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

import java.util.Map;

public final class ComprobantePago {

    private ComprobantePago() {
    }

    public static Question<Boolean> coincide(Long idMatricula, String metodo) {
        return Question.about("el comprobante de la matrícula " + idMatricula)
                .answeredBy(actor -> SerenityRest.lastResponse()
                        .jsonPath()
                        .getList("$", Map.class)
                        .stream()
                        .map(fila -> (Map<?, ?>) fila)
                        .anyMatch(fila -> {
                            Object id = fila.get("idMatricula");
                            Object metodoRegistrado = fila.get("metodo");
                            Object operacion = fila.get("numeroOperacion");
                            return id instanceof Number
                                    && ((Number) id).longValue() == idMatricula
                                    && metodo.equals(metodoRegistrado)
                                    && operacion instanceof String
                                    && ((String) operacion).matches("OP-\\d{6}");
                        }));
    }
}
