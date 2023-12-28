package es.uniovi.eii.myapplication.ui.profile.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.modelo.RelacionSeguido;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.VerPerfil;

public class SeguidoresFragment extends Fragment {

    public static final String DESCRIPCION="descripicion";
    public static final String USUARIO="usuario";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile_seguidos, container, false);

        final RecyclerView recycleSeguidos = (RecyclerView) root.findViewById(R.id.reciclerViewUsuarios);
        recycleSeguidos.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recycleSeguidos.setLayoutManager(layoutManager);

        Bundle args = getArguments();
        Usuario seleccionado = args.getParcelable("usuario");
        List<Usuario> listaUsuarios = seleccionado.getSeguidores();

        Log.d("usuario seguidores", seleccionado.toString());
        Log.d("lista seguidores", listaUsuarios.toString());
        ListaUsuariosAdapter lpAdapter = new ListaUsuariosAdapter(listaUsuarios,
                new ListaUsuariosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Usuario usuario) {
                        clickonItem(usuario);
                    }
                });

        recycleSeguidos.setAdapter(lpAdapter);
        return root;
    }

    private void clickonItem(Usuario usuario) {
        Intent intent = new Intent(getActivity(), VerPerfil.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("usuario", usuario);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
