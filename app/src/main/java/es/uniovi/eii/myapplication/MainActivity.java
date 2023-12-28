package es.uniovi.eii.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import es.uniovi.eii.myapplication.modelo.CargadorUsuarios;
import es.uniovi.eii.myapplication.modelo.RelacionSeguido;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static Usuario usuario;
    private static List<Usuario> listaUsuarios;
    private static List<RelacionSeguido> listaSeguidos;

    public static List<RelacionSeguido> getRelaciones() {
        return listaSeguidos;
    }

    @Override
    protected void onCreate(Bundle savedInstansceState) {
        super.onCreate(savedInstansceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        this.listaUsuarios = bundle.getParcelableArrayList("listaUsuarios");
        this.listaSeguidos = bundle.getParcelableArrayList("listaRelaciones");
        String aux = bundle.getString("username");

        for (Usuario usuario : listaUsuarios) {
            for (RelacionSeguido relacion : listaSeguidos) {
                if (usuario.getUsuario().equals(relacion.getSeguidor())) {
                    for (Usuario usuario2 : listaUsuarios) {
                        if (usuario2.getUsuario().equals(relacion.getSeguido())) {
                            usuario.addSeguido(usuario2);
                            usuario2.addSeguidor(usuario);
                        }
                    }
                }
            }
        }


        for (Usuario user : listaUsuarios) {
            if (user.getUsuario().equals(aux)) {
                usuario = user;
            }
        }
        Log.d("Lista relaciones", listaSeguidos.toString());
        Log.d("Lista main", listaUsuarios.toString());
        Log.d("User Main", usuario.toString());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile, R.id.navigation_find_profiles, R.id.navigation_group_chat, R.id.navigation_private_chat) // profile, home, find profiles, message
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public static Usuario getUser() {
        return usuario;
    }

    public static List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
}