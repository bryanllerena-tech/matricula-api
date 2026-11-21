package pe.edu.cibertec.matricula_api.bdd.gancho;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PreparacionDelEscenario {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
        System.out.println("Estudiante: Brayan Miguel Llerena Arpita");
        System.out.println("Codigo: i202508058");
        System.out.println("Fecha y hora: "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}
