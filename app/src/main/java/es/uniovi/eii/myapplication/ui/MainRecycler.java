package es.uniovi.eii.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.uniovi.eii.myapplication.MainActivity;
import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.datos.UsuariosDataSource;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.profile.fragments.ListaUsuariosAdapter;

import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainRecycler extends AppCompatActivity {

    /**
     * Clase asíncrona interna: AsyncTask <Parámetro de entrada, Tipo de valor del progreso, Tipo del return>
     */
    private class DownloadFilesTask extends AsyncTask<Void, Integer, String> {

        // Barra de progreso
        private ProgressDialog progressDialog;

        /**
         * Variables necesarias para llevar la cuenta del progreso de la carga.
         * ¡Ojo! La fórmula que vamos a utilizar para calcular el porcentaje leído es:
         * (numeroLineasLeidas / lineasAleer) * 100
         * Esa división requiere que el tipo de las variables sea flotante para poder obtener
         * decimales que al multiplicar por 100 nos den el porcentaje.
         *
         * En caso de usar enteros pasaría de 0 a 100 sin los intermedios, llevando una cuenta no válida
         */
        private float lineasAleer = 0.0f;
        float numeroLineasLeidas = 0.0f;


        /**
         * Método previo a la ejecución asíncrona principal.
         * Antes de empezar, muestra el Progress Bar
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progressDialog = new ProgressDialog(MainRecycler.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();

            // Inicializamos el lineasAleer, con un repaso a la cantidad de líneas que tienen los ficheros.
            lineasAleer = (float) (lineasFichero("usuarios.csv"));
            lineasAleer = (float) (lineasAleer + lineasFichero("seguidos.csv"));
        }

        /**
         * Método principal que se ejecutará en segundo plano.
         * El Void se corresponde al parámetro indicado en el encabezado de la clase
         */
        @Override
        protected String doInBackground(Void... voids) {
            String mensaje;

            try {
                cargarUsuario();
                mensaje = "DB actualizada";
            } catch (Exception e) {
                mensaje = "Error en la carga de la base de datos";
            }

            // mNotificationManager.notify(001, mBuilder.build());
            return mensaje;
        }

        /**
         * Este método actualiza la barra de progreso...
         * El Integer se corresponde al parámetro indicado en el encabezado de la clase.
         */
        protected void onProgressUpdate(Integer... progress) {
            progressDialog.setProgress(progress[0]);
        }

        /**
         * Método que se ejecuta tras doInBackground.
         * El mensaje que recibe es el que devolvemos en la ejecución principal.
         */
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            // Carga del RecycleView por primera vez
            cargarView();
        }

        /**
         * Devuelve un entero con las líneas que contiene un fichero,
         * cuyo nombre recibe por parámetro.
         */
        protected int lineasFichero(String nombreFichero) {
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;

            int contador = 0;

            try {
                file = getAssets().open("peliculas.csv");
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);

                while (bufferedReader.readLine() != null)
                    contador++;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return contador;
        }

        /*


        /**
         * Lee lista de películas desde el fichero csv en assets
         * Crea listaPeli como un ArrayList<Pelicula> 
        protected void cargarUsuarios() {
            Usuario usuario = null;
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;

            try {
                file = getAssets().open("usuarios.csv");
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);
                String line = null;

                //Leemos la primera línea que es encabezado y por tanto no nos aporta información útil.
                bufferedReader.readLine();

                //A partir de aquí leemos a partir de la segunda línea.
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(";");
                    usuario = new Usuario(data[0], data[1], data[3],  data[4], data[5], Integer.parseInt(data[6]), data[7]);
                    Log.d("cargarUsuarios", usuario.toString());
                    UsuariosDataSource peliculasDataSource = new UsuariosDataSource(getApplicationContext());
                    peliculasDataSource.open();
                    peliculasDataSource.createUsuario(usuario);
                    peliculasDataSource.close();

                    numeroLineasLeidas++;
                    publishProgress((int) ((numeroLineasLeidas / lineasAleer) * 100));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        */
    }

    private void cargarUsuario() {
        String username = MainActivity.getUsername();
        UsuariosDataSource usuariosDataSource = new UsuariosDataSource(getApplicationContext());
        usuario = usuariosDataSource.getUsuario(username);
        Picasso.get().load(usuario.getFotoperfil()).into((ImageView) findViewById(R.id.imagenFondo));
    }

    public static final String USUARIO_SELECCIONADO = "usuario seleccionado";

    public static final String USUARIO_CREADO = "usuario creado";

    public static String filtroUsuarios = null;

    private static final int GESTION_ACTIVITY = 1;

    // Modelo datos
    List<Usuario> listaUsuarios;
    Usuario usuario;
    RecyclerView listaUsuariosView;

    private boolean primeraEjecucion = true;

    // Objetos para las notificaciones
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

    protected void cargarView() {
        // Cargamos en listaPeli las películas con o sin filtro
        UsuariosDataSource usuariosDataSource = new UsuariosDataSource(getApplicationContext());
        // Abrir
        usuariosDataSource.open();

        if (filtroUsuarios == null || filtroUsuarios.equals("Todas las usuarios")) {
            listaUsuarios = usuariosDataSource.getUsuarios();
        } else if (filtroUsuarios.equals("Seguidos")) {
            listaUsuarios = usuariosDataSource.getFilteredSeguidos(usuario.getUsuario());
        } else if (filtroUsuarios.equals("Seguidores")) {
            listaUsuarios = usuariosDataSource.getFilteredSeguidores(usuario.getUsuario());
        }

        // Cerrar
        usuariosDataSource.close();

        listaUsuariosView = (RecyclerView) findViewById(R.id.recyclerView);
        listaUsuariosView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaUsuariosView.setLayoutManager(layoutManager);
        ListaUsuariosAdapter lpAdapter = new ListaUsuariosAdapter(listaUsuarios,
                new ListaUsuariosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Usuario usuario) {
                        clickOnItem(usuario);
                    }
                });

        listaUsuariosView.setAdapter(lpAdapter);

        primeraEjecucion = false;
        cargarUsuario();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!primeraEjecucion)
            cargarView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);
        DownloadFilesTask task = new DownloadFilesTask();
        task.execute();
    }

    public void clickOnItem(Usuario usuario) {
        Log.i("Click adapter", "Item Clicked: " + usuario.getUsuario());

        Intent intent = new Intent(MainRecycler.this, VerPerfil.class);
        intent.putExtra(USUARIO_SELECCIONADO, usuario);
        // Transición de barrido
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    // Gestión del menú

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intentSettingsActivity = new Intent(MainRecycler.this, SettingsActivity.class);
            startActivity(intentSettingsActivity);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Comprobamos a qué petición se está respondiendo
        if (requestCode == GESTION_ACTIVITY) {
            // Nos aseguramos de que el resultado fue OK
            if (resultCode == RESULT_OK) {
                peli = data.getParcelableExtra(PELICULA_CREADA);
            }

            // Refrescar el RecycleView
            listaPeli.add(peli);

            // Creamos un nuevo Adapter que le pasamos al recyclerView
            ListaPeliculasAdapter listaPeliculasAdapter = new ListaPeliculasAdapter(listaPeli,
                    new ListaPeliculasAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Pelicula item) {
                            clickOnItem(peli);
                        }
                    });

            listaPeliView.setAdapter(listaPeliculasAdapter);

            // Película > Lista > Adapter > RecyclerView
        }
    }

    public void crearPeliNuevaFab(View v) {
        Log.d("CrearPeli", "crearPeli");
        Intent intent = new Intent(MainRecycler.this, NewMovie.class);
        startActivityForResult(intent, GESTION_ACTIVITY);
    }
     */
}
