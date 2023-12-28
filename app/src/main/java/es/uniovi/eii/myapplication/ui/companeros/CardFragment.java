package es.uniovi.eii.myapplication.ui.companeros;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import es.uniovi.eii.myapplication.MainActivity;
import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.dummy.DummyContent;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.VerPerfil;
import es.uniovi.eii.myapplication.ui.profile.fragments.ListaUsuariosAdapter;

/**
 * A fragment representing a list of Items.
 */
public class CardFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private DashboardViewModel dashboardViewModel;
    public List<Usuario> listaUsuarios;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CardFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CardFragment newInstance(int columnCount) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listaUsuarios = MainActivity.getListaUsuarios();

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);
        final RecyclerView recycleUsuarios = (RecyclerView) view.findViewById(R.id.recyclerViewUsuarios);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recycleUsuarios.setLayoutManager(layoutManager);

        recycleUsuarios.setHasFixedSize(true);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyCardRecyclerViewAdapter(listaUsuarios,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String userId = ((TextView) v.findViewById(R.id.id_usuario)).getText().toString();
                            Usuario usuario = null;
                            for (Usuario u : listaUsuarios)
                                if (u.getUsuario().equals(userId))
                                    usuario = u;

                            clickonItem(usuario);
                        }
                    }));
        }
        return view;
    }

    private void clickonItem(Usuario usuario) {
        Intent intent = new Intent(getActivity(), VerPerfil.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("usuario", usuario);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}