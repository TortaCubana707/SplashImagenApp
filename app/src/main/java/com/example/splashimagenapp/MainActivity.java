package com.example.splashimagenapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private final AtomicBoolean cargaTerminada =
            new AtomicBoolean(false);

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    private TextView txtCatalogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen =
                SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        splashScreen.setKeepOnScreenCondition(
                () -> !cargaTerminada.get());

        setContentView(R.layout.activity_main);

        txtCatalogo = findViewById(R.id.txtCatalogo);

        prepararAplicacion();
    }

    private void prepararAplicacion() {
        executor.execute(() -> {
            try {
                // Espera didactica: solo sirve para observar el SplashScreen
                // durante la practica. En una app terminada se sustituye por
                // la carga real (ver cargarCatalogoReal()).
                Thread.sleep(1200);

                List<String> catalogo = CatalogoJuegos.cargarCatalogo();

                runOnUiThread(() -> {
                    mostrarCatalogo(catalogo);
                    cargaTerminada.set(true);
                });

            } catch (InterruptedException error) {
                Thread.currentThread().interrupt();
                runOnUiThread(() -> {
                    mostrarError();
                    cargaTerminada.set(true);
                });
            }
        });
    }

    private void mostrarCatalogo(List<String> catalogo) {
        StringBuilder texto = new StringBuilder(
                getString(R.string.etiqueta_catalogo));
        texto.append(" (").append(catalogo.size()).append(")");
        for (String juego : catalogo) {
            texto.append("\n▸ ").append(juego);
        }
        txtCatalogo.setText(texto.toString());
    }

    private void mostrarError() {
        txtCatalogo.setText("No se pudo cargar el catálogo");
    }

    /**
     * Operación real que sustituye a Thread.sleep() en una app terminada:
     * leer el catálogo de juegos guardado (base de datos o servicio web),
     * comprobar la sesión del jugador y dejar en memoria las partidas
     * guardadas. Se ejecuta igual en el executor, nunca en el hilo principal,
     * y la bandera cargaTerminada debe liberarse tanto si la operación
     * funciona como si falla; de lo contrario el SplashScreen no desaparece.
     */
    private void cargarCatalogoReal() {
        executor.execute(() -> {
            try {
                List<String> catalogo = CatalogoJuegos.cargarCatalogo();
                runOnUiThread(() -> {
                    mostrarCatalogo(catalogo);
                    cargaTerminada.set(true);
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    mostrarError();
                    cargaTerminada.set(true);
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        executor.shutdownNow();
        super.onDestroy();
    }
}
