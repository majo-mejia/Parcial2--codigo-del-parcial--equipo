package co.edu.uniquindio.poo.parcial_2.controllers;

import javafx.scene.control.Alert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/** Utilidades pequeñas para mostrar mensajes y dinero en las ventanas. */
public final class Mensajes {
    private static final DecimalFormat FORMATO_DINERO = new DecimalFormat(
            "#,##0", DecimalFormatSymbols.getInstance(new Locale("es", "CO")));

    private Mensajes() {
    }

    public static String dinero(double valor) {
        return "$" + FORMATO_DINERO.format(valor);
    }

    public static void informar(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void advertir(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void error(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
