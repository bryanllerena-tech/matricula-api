package pe.edu.cibertec.matricula_api.bdd.preguntas;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

import java.util.List;
import java.util.Map;

public final class LibroCalificaciones {

    private LibroCalificaciones() {
    }

    public static Question<String> condicion(String curso) {
        return Question.about("la condición de " + curso)
                .answeredBy(actor -> cursos().stream()
                        .map(fila -> (Map<?, ?>) fila)
                        .filter(fila -> curso.equals(fila.get("codigoCurso")))
                        .map(fila -> String.valueOf(fila.get("condicion")))
                        .findFirst()
                        .orElse(""));
    }

    public static Question<Integer> creditosAprobados() {
        return Question.about("los créditos aprobados")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getInt("creditosAprobados"));
    }

    public static Question<Double> promedio() {
        return Question.about("el promedio ponderado")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getDouble("promedioPonderado"));
    }

    private static List<?> cursos() {
        return SerenityRest.lastResponse().jsonPath().getList("cursos", Map.class);
    }
}
