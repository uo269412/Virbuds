package es.uniovi.eii.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.profile.fragments.InfoFragment;
import es.uniovi.eii.myapplication.ui.profile.fragments.SeguidoresFragment;
import es.uniovi.eii.myapplication.ui.profile.fragments.SeguidosFragment;
import es.uniovi.eii.myapplication.util.Conexion;

public class VerPerfil extends AppCompatActivity {
    private Usuario usuario;

    CollapsingToolbarLayout toolBarLayout;
    TextView nombre;
    TextView biografia;
    TextView universidad;
    TextView carrera;
    TextView curso;
    ImageView fotoperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_perfil_extended);
        Log.d("tamosaqui", "buenas");
        //Recepción datos como activity secundaria
        Intent intentUsuario = getIntent();
        usuario = intentUsuario.getParcelableExtra("usuario");
        Log.d("VERPERFIL", usuario.toString());

        // Gestión de los controles que contienen los datos de la película
        nombre = (TextView)findViewById(R.id.nombreInfoFragment);
        biografia = (TextView)findViewById(R.id.biografiaInfoFragment);
        universidad = (TextView)findViewById(R.id.universidadInfoFragment);
        carrera = (TextView)findViewById(R.id.carreraInfoFragment);
        curso = (TextView)findViewById(R.id.cursoInfoFragment);
        fotoperfil = (ImageView)findViewById(R.id.fotoPerfilInfoFragment);

        nombre.setText(usuario.getNombre());
        biografia.setText(usuario.getBiografia());
        universidad.setText(usuario.getUniversidad());
        carrera.setText(usuario.getCarrera());
        curso.setText("Curso: " + usuario.getCurso());
        Picasso.get().load(usuario.getFotoperfil()).into(fotoperfil);
    }

    // Gestión del menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

    /*        //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }*/

        if (id==R.id.compartir) {
            Conexion conexion=new Conexion(getApplicationContext());
            if (conexion.CompruebaConexion()){
                compartirPeli();
            }
            else
            Toast.makeText(getApplicationContext(), R.string.error_conexion, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Abre el diálogo de compartir para que el usuario elija una app
     * Luego envia el texto que repreenta la pelicula
     */
    public void compartirPeli(){
        /* es necesario hacer un intent con la constate ACTION_SEND */
        /*Llama a cualquier app que haga un envío*/
        Intent itSend = new Intent(Intent.ACTION_SEND);
        /* vamos a enviar texto plano */
        itSend.setType("text/plain");
        // itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{​​para}​​);
        itSend.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.subject_compartir) + ": " + usuario.getNombre());
        itSend.putExtra(Intent.EXTRA_TEXT, getString(R.string.Nombre)
                +": "+usuario.getNombre()+"\n"+
                getString(R.string.Biografia)
                +": "+usuario.getBiografia());

        /* iniciamos la actividad */
                /* puede haber más de una aplicacion a la que hacer un ACTION_SEND,
                   nos sale un ventana que nos permite elegir una.
                   Si no lo pongo y no hay activity disponible, pueda dar un error */
        Intent shareIntent= Intent.createChooser(itSend, null);

        startActivity(shareIntent);

    }

    private void verTrailer() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")));
    }


}