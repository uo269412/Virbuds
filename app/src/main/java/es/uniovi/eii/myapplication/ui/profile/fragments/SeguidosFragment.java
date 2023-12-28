package es.uniovi.eii.myapplication.ui.profile.fragments;

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
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.VerPerfil;

public class SeguidosFragment extends Fragment {

    public static final String USUARIO="usuario";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_seguidos, container, false);

        final RecyclerView recycleSeguidos = (RecyclerView) root.findViewById(R.id.reciclerViewUsuarios);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());

        recycleSeguidos.setLayoutManager(layoutManager);
        recycleSeguidos.setHasFixedSize(true);

        Bundle args = getArguments();
        Usuario seleccionado = args.getParcelable("usuario");
        List<Usuario> listaUsuarios = seleccionado.getSeguidos();

        Log.d("usuario seguidos", seleccionado.toString());
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
