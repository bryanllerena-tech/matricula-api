package pe.edu.cibertec.matricula_api.bdd.util;

public final class Numeros {

    private Numeros() {
    }

    public static Double decimal(String texto) {
        String valor = texto.trim();
        if (valor.contains(",") && valor.contains(".")) {
            valor = valor.replace(".", "").replace(",", ".");
        } else {
            valor = valor.replace(",", ".");
        }
        return Double.valueOf(valor);
    }
}
