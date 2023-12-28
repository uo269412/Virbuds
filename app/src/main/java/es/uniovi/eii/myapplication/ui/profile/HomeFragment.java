package es.uniovi.eii.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.transition.Transition;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.uniovi.eii.myapplication.MainActivity;
import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.modelo.RelacionSeguido;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.companeros.MyCardRecyclerViewAdapter;
import es.uniovi.eii.myapplication.ui.login.LoginActivity;
import es.uniovi.eii.myapplication.ui.profile.fragments.InfoFragment;
import es.uniovi.eii.myapplication.ui.profile.fragments.SeguidoresFragment;
import es.uniovi.eii.myapplication.ui.profile.fragments.SeguidosFragment;
import es.uniovi.eii.myapplication.ui.register.RegisterActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Usuario usuario;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        cargarUsuario();

        ImageView imagenFondo = root.findViewById((R.id.imagenFondo));
        String fotoPerfil = usuario.getFotoperfil();
        Picasso.get().load(fotoPerfil).into(imagenFondo);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editActivity = new Intent(getContext(), RegisterActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<Usuario> listaParaActivity = new ArrayList<Usuario>(MainActivity.getListaUsuarios());
                ArrayList<RelacionSeguido> aux = new ArrayList<>(MainActivity.getRelaciones());
                bundle.putParcelableArrayList("listaUsuarios", listaParaActivity);
                bundle.putParcelableArrayList("listaRelaciones", aux);
                bundle.putParcelable("usuario", usuario);
                editActivity.putExtras(bundle);
                Log.d("Lista usuarios registro", listaParaActivity.toString());

                startActivity(editActivity);
            }
        });
        //Gestion de la botonera
        BottomNavigationView navView = root.findViewById(R.id.nav_fragmentoBotonera);
        //Le añado un listener
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        InfoFragment info = new InfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("usuario", usuario);
        info.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (usuario != null) {
                switch (item.getItemId()) {
                    case R.id.navigation_profile_bio:
                        //Creamos el framento de información
                        InfoFragment info = new InfoFragment();
                        Bundle args = new Bundle();
                        args.putParcelable("usuario", usuario);
                        info.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info).commit();
                        return true;
                    case R.id.navigation_profile_seguidos:
                        //Creamos el framento de información
                        SeguidosFragment seguidos = new SeguidosFragment();
                        Bundle argsSeguidos = new Bundle();
                        argsSeguidos.putParcelable("usuario", usuario);
                        seguidos.setArguments(argsSeguidos);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, seguidos).commit();
                        return true;
                    case R.id.navigation_profile_seguidores:
                        //Creamos el framento de información
                        SeguidoresFragment seguidores = new SeguidoresFragment();
                        Bundle argsSeguidores = new Bundle();
                        argsSeguidores.putParcelable("usuario", usuario);
                        seguidores.setArguments(argsSeguidores);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, seguidores).commit();
                        return true;
                    default:
                        return false;
                }
            }
            return false;
        }
    };

    public void cargarUsuario() {
        this.usuario = MainActivity.getUser();
        Log.d("Home usuario", usuario.toString());
    }

}