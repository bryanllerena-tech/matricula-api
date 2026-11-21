package pe.edu.cibertec.matricula_api.bdd.tareas;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import java.util.List;
import java.util.Map;

public class RecordarMatricula implements Task {

    private final String curso;
    private final String estado;
    private final String claveMemoria;

    public RecordarMatricula(String curso, String estado, String claveMemoria) {
        this.curso = curso;
        this.estado = estado;
        this.claveMemoria = claveMemoria;
    }

    public static RecordarMatricula pagada(String curso) {
        return new RecordarMatricula(curso, "PAGADA", "idMatriculaPagada");
    }

    public static RecordarMatricula registrada(String curso) {
        return new RecordarMatricula(curso, "REGISTRADA", "idMatriculaPendiente");
    }

    public static RecordarMatricula pagadaComoActual(String curso) {
        return new RecordarMatricula(curso, "PAGADA", "idMatriculaActual");
    }

    @Override
    @Step("{0} ubica su matrícula #estado de #curso")
    public <T extends Actor> void performAs(T actor) {
        String credencial = actor.recall("credencial");
        actor.attemptsTo(Get.resource("/api/matriculas")
                .with(peticion -> peticion.header("Authorization", "Bearer " + credencial)
                        .queryParam("curso", curso)
                        .queryParam("estado", estado)));
        Long id = SerenityRest.lastResponse().jsonPath().getList("$", Map.class).stream()
                .map(fila -> (Map<?, ?>) fila)
                .filter(fila -> curso.equals(fila.get("codigoCurso"))
                        && estado.equals(fila.get("estado")))
                .map(fila -> ((Number) fila.get("id")).longValue())
                .findFirst()
                .orElseThrow();
        actor.remember(claveMemoria, id);
    }
}
