package es.uniovi.eii.myapplication.ui.companeros;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.datos.UsuariosDataSource;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.profile.fragments.ListaUsuariosAdapter;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public List<Usuario> listaUsuarios;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_card_list, container, false);
        final RecyclerView recycleUsuarios = (RecyclerView) root.findViewById(R.id.recyclerViewUsuarios);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recycleUsuarios.setLayoutManager(layoutManager);

        recycleUsuarios.setHasFixedSize(true);

        Bundle args = getArguments();
        // String usuario = args.getString("usuario");


        //Creamos unos usuarios data source para llamar al método que por SQL nos localizará los usuarios
        UsuariosDataSource usuariosDataSource = new UsuariosDataSource(getContext());
        usuariosDataSource.open();
        listaUsuarios = usuariosDataSource.getUsuarios();
        /*
         * Realmente aquí usamos esto para poder extraer unos usuarios de la base de datos. Podríamos hacer
         * que el usuario que metemos de parámetro sea para recoger usuarios que no siga.
         */
        usuariosDataSource.close();
/*
        ListaUsuariosAdapter luAdapter = new ListaUsuariosAdapter(listaUsuarios,
                new ListaUsuariosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Usuario usuario1) {
                        //Si pulsamos sobre un usuario nos llevará a su perfil ¿?
                    }
                });

        MaterialButton btnSeguir = (MaterialButton) root.findViewById(R.id.boton_seguir);
        btnSeguir.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                // Aquí metodo del dataSoutce para empezar a seguir un usuario
            }
        });

        MaterialButton btnMD = (MaterialButton) root.findViewById(R.id.boton_MD);
        btnMD.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                // Aquí cambiar de fragmento a chat privado y crear una conversación con el usuario
                // en el momento que escriba algo
            }
        });



        recycleUsuarios.setAdapter(luAdapter); // aquí el adapter para meternos en el perfil
 */
        return root;
    }
}