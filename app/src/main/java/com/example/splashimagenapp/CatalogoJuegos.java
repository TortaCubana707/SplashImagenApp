package com.example.splashimagenapp;

import java.util.Arrays;
import java.util.List;

/**
 * Origen de datos de la aplicacion. En la practica devuelve una lista fija;
 * es el punto donde una app real consultaria la base de datos o el servidor.
 */
final class CatalogoJuegos {

    private CatalogoJuegos() {
    }

    static List<String> cargarCatalogo() {
        return Arrays.asList(
                "Neon Racer",
                "Pixel Dungeon",
                "Star Blaster",
                "Retro Kart");
    }
}
